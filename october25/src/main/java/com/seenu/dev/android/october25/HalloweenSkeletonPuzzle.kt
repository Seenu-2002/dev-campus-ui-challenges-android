package com.seenu.dev.android.october25

import android.content.ClipData
import android.content.ClipDescription
import android.util.Log
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.draganddrop.mimeTypes
import androidx.compose.ui.draganddrop.toAndroidDragEvent
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.seenu.dev.android.october25.theme.Cinzel
import com.seenu.dev.android.october25.theme.OctoberTheme
import com.seenu.dev.android.october25.theme.outlineActive
import com.seenu.dev.android.october25.theme.outlineError
import com.seenu.dev.android.october25.theme.outlineInactive
import com.seenu.dev.android.october25.theme.slot
import com.seenu.dev.android.october25.theme.textPrimary
import com.seenu.dev.android.october25.theme.toastBg
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview
@Composable
private fun HalloweenSkeletonPuzzlePreview() {
    OctoberTheme {
        HalloweenSkeletonPuzzle()
    }
}

@Composable
fun HalloweenSkeletonPuzzle() {
    val snackBarHostState = remember { SnackbarHostState() }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) { data ->
                Box(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.toastBg,
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(8.dp)
                ) {
                    Text(
                        text = data.visuals.message,
                        fontFamily = Cinzel,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.background
                    )
                }

            }
        }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {

            val solvedParts = remember {
                mutableStateSetOf<SkeletonPart>()
            }
            val isPuzzleSolved = solvedParts.size == SkeletonPart.entries.size

            var triggerShakePart by remember {
                mutableStateOf<SkeletonPart?>(null)
            }
            val scope = rememberCoroutineScope()

            val message = stringResource(R.string.skeleton_is_back)
            LaunchedEffect(isPuzzleSolved) {
                if (isPuzzleSolved) {
                    snackBarHostState.showSnackbar(
                        message = message
                    )
                }
            }

            Image(
                painter = painterResource(R.drawable.ic_skeleteon_bg),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )

            Skeleton(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .padding(top = 80.dp),
                solvedParts = solvedParts,
                playAnimation = isPuzzleSolved,
                onSolved = { part ->
                    solvedParts.add(part)
                },
                onMisplaced = {
                    scope.launch {
                        delay(100)
                        triggerShakePart = it
                        delay(500)
                        triggerShakePart = null
                    }
                }
            )

            SkeletonDragSourceGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                solvedParts = solvedParts,
                triggerShake = triggerShakePart
            )
        }
    }
}

enum class SkeletonPart {
    LEG_LEFT,
    ARM_RIGHT,
    RIB_CAGE,
    LEG_RIGHT,
    HEAD,
    ARM_LEFT,
    PELVIS,
}

fun SkeletonPart.getDrawableRes(): Int {
    return when (this) {
        SkeletonPart.HEAD -> R.drawable.ic_skeleten_head
        SkeletonPart.ARM_LEFT -> R.drawable.ic_skeleten_arm_left
        SkeletonPart.ARM_RIGHT -> R.drawable.ic_skeleten_arm_right
        SkeletonPart.RIB_CAGE -> R.drawable.ic_skeleten_rib_cage
        SkeletonPart.PELVIS -> R.drawable.ic_skeleten_pelvis
        SkeletonPart.LEG_LEFT -> R.drawable.ic_skeleten_leg_left
        SkeletonPart.LEG_RIGHT -> R.drawable.ic_skeleten_leg_right
    }
}

private val skeletonPadding = hashMapOf(
    SkeletonPart.HEAD to PaddingValues(
        top = 12.dp,
        bottom = 4.dp,
        start = 20.dp,
        end = 20.dp
    ),
    SkeletonPart.ARM_LEFT to PaddingValues(
        start = 12.dp,
        bottom = 8.dp,
        top = 8.dp,
        end = 4.dp
    ),
    SkeletonPart.ARM_RIGHT to PaddingValues(
        end = 12.dp,
        bottom = 8.dp,
        top = 8.dp,
        start = 4.dp
    ),
    SkeletonPart.RIB_CAGE to PaddingValues(
        vertical = 12.dp,
        horizontal = 4.dp
    ),
    SkeletonPart.PELVIS to PaddingValues(
        horizontal = 4.dp,
        vertical = 8.dp
    ),
    SkeletonPart.LEG_LEFT to PaddingValues(
        top = 4.dp,
        bottom = 4.dp,
        end = 12.dp,
        start = 4.dp
    ),
    SkeletonPart.LEG_RIGHT to PaddingValues(
        top = 4.dp,
        bottom = 4.dp,
        start = 12.dp,
        end = 4.dp
    )
)

@Composable
fun Skeleton(
    modifier: Modifier = Modifier,
    solvedParts: Set<SkeletonPart>,
    playAnimation: Boolean,
    onSolved: (SkeletonPart) -> Unit,
    onMisplaced: (SkeletonPart) -> Unit
) {

    val armRotation = remember {
        Animatable(0F)
    }
    val headRotation = remember {
        Animatable(0F)
    }

    LaunchedEffect(playAnimation) {
        if (playAnimation) {
            delay(300)
            val anim = tween<Float>(durationMillis = 300, easing = Easing {
                OvershootInterpolator().getInterpolation(it)
            })
            launch {
                headRotation.animateTo(16F, animationSpec = anim)
                delay(1000)
                headRotation.animateTo(0F, animationSpec = anim)
            }
            launch {
                armRotation.animateTo(16F, animationSpec = anim)
                delay(1000)
                armRotation.animateTo(0F, animationSpec = anim)
            }
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier) {
            SkeletonDragTarget(
                skeletonPart = SkeletonPart.HEAD,
                isSolved = solvedParts.contains(SkeletonPart.HEAD),
                playAnimation = playAnimation,
                animationSide = AnimationSide.TO_BOTTOM,
                onSolved = {
                    onSolved(SkeletonPart.HEAD)
                },
                onMisplaced = onMisplaced,
                modifier = Modifier
                    .graphicsLayer {
                        rotationZ = headRotation.value
                    }
            )
        }
        Row(modifier = Modifier.height(IntrinsicSize.Max)) {
            SkeletonDragTarget(
                skeletonPart = SkeletonPart.ARM_LEFT,
                isSolved = solvedParts.contains(SkeletonPart.ARM_LEFT),
                playAnimation = playAnimation,
                animationSide = AnimationSide.TO_RIGHT,
                onSolved = {
                    onSolved(SkeletonPart.ARM_LEFT)
                },
                onMisplaced = onMisplaced,
                modifier = Modifier
                    .graphicsLayer {
                        rotationZ = armRotation.value
                        transformOrigin = TransformOrigin(
                            pivotFractionX = .5F,
                            pivotFractionY = 0F
                        )
                    }
            )
            Column(
                modifier = Modifier
                    .width(IntrinsicSize.Max)
                    .fillMaxHeight()
            ) {
                SkeletonDragTarget(
                    skeletonPart = SkeletonPart.RIB_CAGE,
                    isSolved = solvedParts.contains(SkeletonPart.RIB_CAGE),
                    playAnimation = playAnimation,
                    animationSide = AnimationSide.NONE,
                    onSolved = {
                        onSolved(SkeletonPart.RIB_CAGE)
                    },
                    onMisplaced = onMisplaced,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.6F)
                )
                SkeletonDragTarget(
                    skeletonPart = SkeletonPart.PELVIS,
                    isSolved = solvedParts.contains(SkeletonPart.PELVIS),
                    playAnimation = playAnimation,
                    animationSide = AnimationSide.TO_TOP,
                    onSolved = {
                        onSolved(SkeletonPart.PELVIS)
                    },
                    onMisplaced = onMisplaced,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1F)
                )
            }
            SkeletonDragTarget(
                skeletonPart = SkeletonPart.ARM_RIGHT,
                isSolved = solvedParts.contains(SkeletonPart.ARM_RIGHT),
                playAnimation = playAnimation,
                animationSide = AnimationSide.TO_LEFT,
                onSolved = {
                    onSolved(SkeletonPart.ARM_RIGHT)
                },
                onMisplaced = onMisplaced,
                modifier = Modifier
                    .graphicsLayer {
                        rotationZ = -armRotation.value
                        transformOrigin = TransformOrigin(
                            pivotFractionX = .5F,
                            pivotFractionY = 0F
                        )
                    }
            )
        }
        Row(modifier = Modifier.height(IntrinsicSize.Max)) {
            SkeletonDragTarget(
                skeletonPart = SkeletonPart.LEG_LEFT,
                isSolved = solvedParts.contains(SkeletonPart.LEG_LEFT),
                playAnimation = playAnimation,
                animationSide = AnimationSide.TO_TOP,
                onSolved = {
                    onSolved(SkeletonPart.LEG_LEFT)
                },
                onMisplaced = onMisplaced,
                modifier = Modifier
                    .fillMaxHeight()
            )
            SkeletonDragTarget(
                skeletonPart = SkeletonPart.LEG_RIGHT,
                isSolved = solvedParts.contains(SkeletonPart.LEG_RIGHT),
                playAnimation = playAnimation,
                animationSide = AnimationSide.TO_TOP,
                onSolved = {
                    onSolved(SkeletonPart.LEG_RIGHT)
                },
                onMisplaced = onMisplaced,
                modifier = Modifier
                    .fillMaxHeight()
            )
        }
    }
}

@Composable
fun SkeletonDragSourceGrid(
    modifier: Modifier = Modifier,
    solvedParts: Set<SkeletonPart> = emptySet(),
    triggerShake: SkeletonPart? = null,
) {
    ConstraintLayout(modifier = modifier) {
        val (
            head,
            leftArm,
            rightArm,
            ribCage,
            pelvis,
            leftLeg,
            rightLeg
        ) = createRefs()

        createHorizontalChain(
            leftLeg,
            rightArm,
            ribCage,
            rightLeg,
            chainStyle = ChainStyle.Spread
        )

        SkeletonDragSource(
            skeletonPart = SkeletonPart.LEG_LEFT,
            isSolved = solvedParts.contains(SkeletonPart.LEG_LEFT),
            triggerShake = triggerShake == SkeletonPart.LEG_LEFT,
            modifier = Modifier.constrainAs(leftLeg) {
                top.linkTo(parent.top)
            }
        )

        SkeletonDragSource(
            skeletonPart = SkeletonPart.ARM_RIGHT,
            isSolved = solvedParts.contains(SkeletonPart.ARM_RIGHT),
            triggerShake = triggerShake == SkeletonPart.ARM_RIGHT,
            modifier = Modifier.constrainAs(rightArm) {
                top.linkTo(parent.top)
            }
        )

        SkeletonDragSource(
            skeletonPart = SkeletonPart.RIB_CAGE,
            isSolved = solvedParts.contains(SkeletonPart.RIB_CAGE),
            triggerShake = triggerShake == SkeletonPart.RIB_CAGE,
            modifier = Modifier.constrainAs(ribCage) {
                top.linkTo(parent.top)
            }
        )

        SkeletonDragSource(
            skeletonPart = SkeletonPart.LEG_RIGHT,
            isSolved = solvedParts.contains(SkeletonPart.LEG_RIGHT),
            triggerShake = triggerShake == SkeletonPart.LEG_RIGHT,
            modifier = Modifier.constrainAs(rightLeg) {
                top.linkTo(parent.top)
            }
        )
        SkeletonDragSource(
            skeletonPart = SkeletonPart.HEAD,
            isSolved = solvedParts.contains(SkeletonPart.HEAD),
            triggerShake = triggerShake == SkeletonPart.HEAD,
            modifier = Modifier.constrainAs(head) {
                width = Dimension.fillToConstraints
                top.linkTo(rightArm.bottom, margin = 16.dp)
                start.linkTo(leftLeg.start)
                end.linkTo(rightArm.end)
                bottom.linkTo(parent.bottom, margin = 16.dp)
            }
        )
        SkeletonDragSource(
            skeletonPart = SkeletonPart.ARM_LEFT,
            isSolved = solvedParts.contains(SkeletonPart.ARM_LEFT),
            triggerShake = triggerShake == SkeletonPart.ARM_LEFT,
            modifier = Modifier.constrainAs(leftArm) {
                width = Dimension.fillToConstraints
                top.linkTo(ribCage.bottom, margin = 16.dp)
                start.linkTo(ribCage.start)
                bottom.linkTo(parent.bottom, margin = 16.dp)
            }
        )

        SkeletonDragSource(
            skeletonPart = SkeletonPart.PELVIS,
            isSolved = solvedParts.contains(SkeletonPart.PELVIS),
            triggerShake = triggerShake == SkeletonPart.PELVIS,
            modifier = Modifier.constrainAs(pelvis) {
                width = Dimension.fillToConstraints
                top.linkTo(rightLeg.bottom, margin = 16.dp)
                start.linkTo(leftArm.end, margin = 16.dp)
                end.linkTo(rightLeg.end)
            }
        )
    }
}

@Preview
@Composable
private fun SkeletonDragSourceTargetPreview() {
    OctoberTheme {
        SkeletonDragTarget(
            skeletonPart = SkeletonPart.HEAD,
            isSolved = false,
            modifier = Modifier
                .padding(
                    horizontal = 20.dp
                )
                .padding(top = 12.dp, bottom = 4.dp)
        )
    }
}

enum class AnimationSide {
    TO_LEFT,
    TO_RIGHT,
    TO_TOP,
    TO_BOTTOM,
    NONE
}

@Composable
fun SkeletonDragTarget(
    skeletonPart: SkeletonPart,
    isSolved: Boolean,
    modifier: Modifier = Modifier,
    playAnimation: Boolean = false,
    animationSide: AnimationSide = AnimationSide.TO_BOTTOM,
    onSolved: () -> Unit = {},
    onMisplaced: (SkeletonPart) -> Unit = {}
) {
    val offset = remember {
        Animatable(0F)
    }

    val (offsetX, offsetY) = remember(offset.value, animationSide) {
        when (animationSide) {
            AnimationSide.TO_LEFT -> Pair(-offset.value.dp, 0.dp)
            AnimationSide.TO_RIGHT -> Pair(offset.value.dp, 0.dp)
            AnimationSide.TO_TOP -> Pair(0.dp, -offset.value.dp)
            AnimationSide.TO_BOTTOM -> Pair(0.dp, offset.value.dp)
            AnimationSide.NONE -> Pair(0.dp, 0.dp)
        }
    }

    LaunchedEffect(playAnimation) {
        if (playAnimation) {
            offset.animateTo(8F, animationSpec = tween(100))
        }
    }

    var isHovered by remember {
        mutableStateOf(false)
    }
    val tint: Color
    var strokeColor: Color
    if (isSolved) {
        tint = MaterialTheme.colorScheme.textPrimary
        strokeColor = MaterialTheme.colorScheme.outlineActive
    } else {
        tint = MaterialTheme.colorScheme.slot
        strokeColor = MaterialTheme.colorScheme.outlineInactive
    }
    if (playAnimation) {
        strokeColor = Color.Transparent
    }
    Box(
        modifier = Modifier
            .background(color = if (isHovered) MaterialTheme.colorScheme.outlineActive.copy(alpha = .2F) else Color.Transparent)
            .offset(x = offsetX, y = offsetY)
            .border(2.dp, color = strokeColor)
            .padding(skeletonPadding[skeletonPart] ?: PaddingValues(0.dp))
            .then(modifier)
            .dragAndDropTarget(shouldStartDragAndDrop = { event ->
                event
                    .mimeTypes()
                    .contains(ClipDescription.MIMETYPE_TEXT_PLAIN)
            }, target = remember {
                object : DragAndDropTarget {
                    override fun onDrop(event: DragAndDropEvent): Boolean {
                        val part = event.toAndroidDragEvent().clipData.getItemAt(0).text.toString()
                        val isValid =
                            part == "$skeletonPart"

                        if (isValid) {
                            onSolved()
                        } else {
                            val droppedPart =
                                SkeletonPart.valueOf(part)
                            onMisplaced(droppedPart)
                        }

                        return isValid
                    }

                    override fun onMoved(event: DragAndDropEvent) {
                        super.onMoved(event)
                        isHovered = true && !isSolved
                    }

                    override fun onExited(event: DragAndDropEvent) {
                        super.onExited(event)
                        isHovered = false
                    }

                    override fun onEnded(event: DragAndDropEvent) {
                        super.onEnded(event)
                        isHovered = false
                    }
                }
            }),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(skeletonPart.getDrawableRes()),
            contentDescription = null,
            tint = tint
        )
    }
}

@Preview
@Composable
private fun SkeletonDragSourcePreview() {
    OctoberTheme {
        SkeletonDragSource(
            skeletonPart = SkeletonPart.HEAD,
            modifier = Modifier
                .padding(
                    horizontal = 20.dp
                )
                .padding(top = 12.dp, bottom = 4.dp)
        )
    }
}

@Composable
fun SkeletonDragSource(
    skeletonPart: SkeletonPart,
    modifier: Modifier = Modifier,
    isSolved: Boolean = false,
    triggerShake: Boolean = false,
) {
    val offset = remember {
        Animatable(0F)
    }

    LaunchedEffect(triggerShake) {
        Log.e("SkeletonDragSource", "LaunchedEffect: $triggerShake")
        if (triggerShake) {
            repeat(3) { // shake 3 times
                offset.animateTo(8F, animationSpec = tween(50))
                offset.animateTo(-8F, animationSpec = tween(50))
            }
            offset.animateTo(0f, animationSpec = tween(50))
        }
    }

    val outline =
        if (offset.isRunning) MaterialTheme.colorScheme.outlineError else MaterialTheme.colorScheme.outlineActive
    val imageVector = ImageVector.vectorResource(skeletonPart.getDrawableRes())
    val painter = rememberVectorPainter(image = imageVector)
    val mod = if (isSolved) {
        Modifier
            .alpha(0F)
            .border(2.dp, color = outline)
            .padding(skeletonPadding[skeletonPart] ?: PaddingValues(0.dp))
            .then(modifier)
    } else {
        Modifier
            .offset(x = offset.value.dp)
            .border(2.dp, color = outline)
            .padding(skeletonPadding[skeletonPart] ?: PaddingValues(0.dp))
            .then(modifier)
            .dragAndDropSource(
                drawDragDecoration = {
                    drawRect(
                        size = size,
                        color = outline,
                        style = Stroke(width = 2.dp.toPx())
                    )

                    with(painter) {
                        draw(size = size)
                    }

                }
            ) { offset ->
                DragAndDropTransferData(
                    clipData = ClipData.newPlainText(
                        "type",
                        "$skeletonPart"
                    )
                )
            }
    }

    Box(
        modifier = mod,
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(skeletonPart.getDrawableRes()),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.textPrimary
        )
    }
}