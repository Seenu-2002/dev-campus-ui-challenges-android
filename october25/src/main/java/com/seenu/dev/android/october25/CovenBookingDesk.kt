package com.seenu.dev.android.october25

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.seenu.dev.android.october25.state.BookingDetail
import com.seenu.dev.android.october25.state.CovenBookingUiState
import com.seenu.dev.android.october25.state.Slot
import com.seenu.dev.android.october25.state.SlotStatus
import com.seenu.dev.android.october25.state.Witch
import com.seenu.dev.android.october25.state.getImageRes
import com.seenu.dev.android.october25.state.getStringRes
import com.seenu.dev.android.october25.theme.Cinzel
import com.seenu.dev.android.october25.theme.Montserrat
import com.seenu.dev.android.october25.theme.OctoberTheme
import com.seenu.dev.android.october25.theme.frameActive
import com.seenu.dev.android.october25.theme.frameInactive
import com.seenu.dev.android.october25.theme.outlineActive
import com.seenu.dev.android.october25.theme.outlineInactive
import com.seenu.dev.android.october25.theme.slot
import com.seenu.dev.android.october25.theme.textActive
import com.seenu.dev.android.october25.theme.textDisabled
import com.seenu.dev.android.october25.theme.textPrimary
import com.seenu.dev.android.october25.theme.textSecondary
import com.seenu.dev.android.october25.theme.toastBg
import com.seenu.dev.android.october25.viewmodel.CovenBookingViewModel
import kotlinx.coroutines.launch
import kotlin.Long

@Preview
@Composable
private fun CovenBookingDeskPreview() {
    OctoberTheme {
        CovenBookingDesk()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CovenBookingDesk() {
    val viewModel: CovenBookingViewModel = viewModel()
    val bookingUiState by viewModel.bookingsUiState.collectAsStateWithLifecycle()
    val selectedWitch: Witch? = bookingUiState?.witch
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.coven_booking_desk),
                        fontFamily = Cinzel,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        color = MaterialTheme.colorScheme.textPrimary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
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
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
        ) {

            Land(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5F)
                    .align(Alignment.BottomCenter)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    val witches = Witch.entries
                    items(witches.size) { index ->
                        val witch = witches[index]
                        WitchCard(
                            witch = witch,
                            isSelected = selectedWitch == witch,
                            isStar = index % 2 == 0,
                            modifier = Modifier
                                .clickable {
                                    val witch = if (selectedWitch == witch) {
                                        null
                                    } else {
                                        witch
                                    }
                                    viewModel.selectWitch(witch)
                                }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                if (selectedWitch != null) {
                    ReservationCard(
                        modifier = Modifier.fillMaxWidth(),
                        bookingDetails = viewModel.getBookingDetail(selectedWitch),
                        chooseArrivalDate = {
                            viewModel.confirmWitch(selectedWitch)
                        },
                        changeArrivalDate = {
                            viewModel.confirmWitch(selectedWitch)
                        }
                    )
                }
            }
        }
    }

    when (val bookingUiState = bookingUiState) {
        is CovenBookingUiState.SelectDate -> {
            DatePickerDialog(
                onDatePicked = { month, date ->
                    viewModel.selectDate(bookingUiState.witch, "$month $date")
                },
                onDismiss = {
                    viewModel.clearSelection()
                }
            )
        }

        is CovenBookingUiState.SelectSlot -> {
            TimeSlotDialog(
                witch = bookingUiState.witch,
                date = bookingUiState.date,
                slots = viewModel.getSlots(bookingUiState.date),
                onSlotSelected = { slot ->
                    viewModel.selectSlot(bookingUiState.witch, bookingUiState.date, slot)
                },
                onDismiss = {
                    viewModel.clearSelection()
                }
            )
        }

        is CovenBookingUiState.ConfirmBooking -> {
            val confirmMessage = stringResource(R.string.booking_confirm_msg, stringResource(bookingUiState.witch.getStringRes())).uppercase()
            SlotBookingConfirmationDialog(
                witch = bookingUiState.witch,
                date = bookingUiState.bookingDetail.date,
                slot = bookingUiState.bookingDetail.slot,
                onDismiss = {
                    viewModel.clearSelection()
                },
                onConfirm = {
                    viewModel.confirmBooking(bookingUiState.bookingDetail)
                    scope.launch {
                        snackBarHostState.showSnackbar(
                            message = confirmMessage
                        )
                    }
                }
            )
        }

        else -> {
            // No-op
        }
    }
}

@Preview
@Composable
fun Land(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(R.drawable.witch_land),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )
        Image(
            painter = painterResource(R.drawable.witch_tree),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 52.dp)
        )
        Image(
            painter = painterResource(R.drawable.witch_pot),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .width(172.dp)
                .align(Alignment.Center)
                .padding(top = 80.dp)
        )
    }
}

@Composable
fun ReservationCard(
    modifier: Modifier = Modifier,
    bookingDetails: BookingDetail?,
    chooseArrivalDate: () -> Unit = {},
    changeArrivalDate: () -> Unit = {}
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        var buttonText: String
        if (bookingDetails != null) {
            buttonText = stringResource(R.string.change_arrival_date)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.booked),
                    fontFamily = Montserrat,
                    fontSize = 21.sp,
                    color = MaterialTheme.colorScheme.textPrimary
                )
                Text(
                    text = bookingDetails.dateWithTime,
                    fontFamily = Montserrat,
                    fontSize = 21.sp,
                    color = MaterialTheme.colorScheme.textPrimary,
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            buttonText = stringResource(R.string.choose_arrival_date)
            Text(
                text = stringResource(R.string.no_reservation),
                fontFamily = Montserrat,
                fontSize = 21.sp,
                color = MaterialTheme.colorScheme.textPrimary
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        CovenBookingDeskButton(
            text = buttonText,
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if (bookingDetails != null) {
                    chooseArrivalDate()
                } else {
                    changeArrivalDate()
                }
            }
        )
    }
}

@Preview
@Composable
fun WitchCardPreview() {
    OctoberTheme {
        var isSelected by remember { mutableStateOf(false) }
        WitchCard(
            Witch.MORGANA,
            isSelected = isSelected,
            isStar = true,
            modifier = Modifier.clickable {
                isSelected = !isSelected
            }
        )
    }
}

@Composable
fun WitchCard(witch: Witch, isSelected: Boolean, isStar: Boolean, modifier: Modifier = Modifier) {

    val transition = updateTransition(targetState = isSelected, label = "WitchCardTransition")

    val frameColor by transition.animateColor(label = "frameColor") { selected ->
        if (selected) MaterialTheme.colorScheme.frameActive else MaterialTheme.colorScheme.frameInactive
    }
    val textColor by transition.animateColor(label = "textColor") { selected ->
        if (selected) MaterialTheme.colorScheme.textActive else MaterialTheme.colorScheme.textPrimary
    }
    val backgroundColor by transition.animateColor(label = "backgroundColor") { selected ->
        if (selected) MaterialTheme.colorScheme.textPrimary else MaterialTheme.colorScheme.outlineActive
    }


    Column(
        modifier = modifier
            .width(IntrinsicSize.Max)
            .background(color = backgroundColor)
            .padding(all = 4.dp)
    ) {
        Box(modifier = Modifier, contentAlignment = Alignment.Center) {
            Icon(
                painter = painterResource(R.drawable.ic_frame),
                contentDescription = null,
                tint = frameColor,
            )
            Image(
                painter = painterResource(id = witch.getImageRes()),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .matchParentSize()
                    .padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            val iconRes = if (isStar) {
                R.drawable.ic_star
            } else {
                R.drawable.ic_moon_2
            }
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                tint = textColor
            )

            Text(
                text = stringResource(witch.getStringRes()).uppercase(),
                fontFamily = Cinzel,
                fontSize = 15.sp,
                color = textColor,
                modifier = Modifier.padding(horizontal = 4.dp)
            )

            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                tint = textColor,
                modifier = Modifier.graphicsLayer {
                    rotationY = 180F
                }
            )

        }
    }
}

@Preview
@Composable
private fun CovenBookingDeskButtonPreview() {
    OctoberTheme {
        CovenBookingDeskButton(
            text = " CHANGE DATE ",
            onClick = {

            }
        )
    }
}

@Composable
fun CovenBookingDeskButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .background(
                shape = CutCornerShape(12.dp),
                color = MaterialTheme.colorScheme.frameInactive
            )
            .clickable(
                role = Role.Button,
                onClick = onClick
            )
            .padding(2.dp)
            .border(1.dp, shape = RectangleShape, color = MaterialTheme.colorScheme.outlineActive),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = text,
            fontFamily = Cinzel,
            fontWeight = FontWeight.Bold,
            fontSize = 21.sp,
            color = MaterialTheme.colorScheme.textPrimary,
            modifier = Modifier.padding(12.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CovenBookingAlertDialog(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    BasicAlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ),
    ) {
        Column(
            modifier = Modifier.background(color = MaterialTheme.colorScheme.slot)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                IconButton(onClick = onDismiss, modifier = Modifier.align(Alignment.TopEnd)) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.textDisabled
                    )
                }
            }

            content()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    modifier: Modifier = Modifier,
    onDatePicked: (String, Int) -> Unit,
    onDismiss: () -> Unit,
) {
    var selectedDate by remember {
        mutableStateOf(Pair("OCT", 30))
    }
    var selectedDateLabel by remember {
        mutableStateOf("WED, OCT 30")
    }
    CovenBookingAlertDialog(
        modifier = modifier,
        onDismiss = onDismiss
    ) {

        Text(
            text = stringResource(R.string.choose_arrival_date),
            fontFamily = Montserrat,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.textSecondary,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = selectedDateLabel,
            fontFamily = Cinzel,
            fontSize = 32.sp,
            color = MaterialTheme.colorScheme.textPrimary,
            modifier = Modifier
                .padding(horizontal = 20.dp),
            fontWeight = FontWeight.Bold
        )

        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.frameInactive,
        )

        ArrivalDatePicker(
            modifier = Modifier
                .padding(horizontal = 20.dp), selectedDate = selectedDate.second
        ) { day, month, date ->
            selectedDate = Pair(month, date)
            selectedDateLabel = "$day, $month $date"
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.frameInactive,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            Spacer(modifier = Modifier.weight(1F))
            Text(
                text = stringResource(R.string.cancel),
                fontFamily = Montserrat,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.textPrimary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .clickable {
                        onDismiss()
                    }
                    .padding(horizontal = 12.dp, vertical = 10.dp)
            )
            Text(
                text = stringResource(R.string.ok),
                fontFamily = Montserrat,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.textPrimary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .clickable {
                        onDatePicked(selectedDate.first, selectedDate.second)
                    }
                    .padding(horizontal = 12.dp, vertical = 10.dp)
            )
        }
    }
}

@Preview(
    device = "spec:width=411dp,height=891dp",
)
@Composable
private fun ArrivalDatePickerPreview() {
    OctoberTheme {
        var selectedDate by remember {
            mutableIntStateOf(27)
        }
        ArrivalDatePicker(
            selectedDate = selectedDate
        ) { day, month, dateNum ->
            selectedDate = dateNum
        }
    }
}

@Composable
fun ArrivalDatePicker(
    modifier: Modifier = Modifier,
    selectedDate: Int = 27,
    onDateSelected: (String, String, Int) -> Unit = { _, _, _ -> }
) {
    val daysList = remember {
        listOf(
            'S',
            'M',
            'T',
            'W',
            'T',
            'F',
            'S'
        )
    }
    val days = remember {
        listOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")
    }
    Column(modifier = modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            for (day in daysList) {
                Text(
                    text = day.toString(),
                    fontFamily = Cinzel,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.textPrimary,
                    modifier = Modifier
                        .padding(12.dp)
                        .weight(1F),
                    textAlign = TextAlign.Center
                )
            }
        }

        LazyVerticalGrid(columns = GridCells.Fixed(7)) {
            item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                Text(
                    text = stringResource(R.string.october_25),
                    fontSize = 14.sp,
                    fontFamily = Montserrat,
                    color = MaterialTheme.colorScheme.textSecondary,
                    modifier = Modifier
                        .padding(12.dp)
                )
            }

            items(5) {
                val date = 27 + it
                val isSelected = selectedDate == date
                var itemModifier = Modifier
                    .size(48.dp)
                    .aspectRatio(1F)
                    .padding(4.dp)
                    .clip(CircleShape)

                val textColor = if (isSelected) {
                    itemModifier = itemModifier
                        .background(
                            color = MaterialTheme.colorScheme.textPrimary,
                            shape = CircleShape
                        )
                    MaterialTheme.colorScheme.outlineInactive
                } else {
                    MaterialTheme.colorScheme.textPrimary
                }
                itemModifier = itemModifier
                    .clickable {
                        onDateSelected(days[it], "Oct", date)
                    }
                Box(modifier = Modifier, contentAlignment = Alignment.Center) {
                    Box(modifier = itemModifier, contentAlignment = Alignment.Center) {
                        Text(
                            text = date.toString(),
                            fontSize = 16.sp,
                            fontFamily = Cinzel,
                            color = textColor,
                            modifier = Modifier,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            item(span = { GridItemSpan(2) }) {}
            item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                Text(
                    text = stringResource(R.string.november_25),
                    fontSize = 14.sp,
                    fontFamily = Montserrat,
                    color = MaterialTheme.colorScheme.textSecondary,
                    modifier = Modifier
                        .padding(12.dp)
                )
            }
            item(span = { GridItemSpan(5) }) {}
            items(2) {
                val date = 1 + it
                val isSelected = selectedDate == date
                var itemModifier = Modifier
                    .size(48.dp)
                    .aspectRatio(1F)
                    .padding(4.dp)
                    .clip(CircleShape)

                val textColor = if (isSelected) {
                    itemModifier = itemModifier
                        .background(
                            color = MaterialTheme.colorScheme.textPrimary,
                            shape = CircleShape
                        )
                    MaterialTheme.colorScheme.outlineInactive
                } else {
                    MaterialTheme.colorScheme.textPrimary
                }
                itemModifier = itemModifier
                    .clickable {
                        onDateSelected(days[it + 5], "Nov", date)
                    }
                Box(modifier = Modifier, contentAlignment = Alignment.Center) {
                    Box(modifier = itemModifier, contentAlignment = Alignment.Center) {
                        Text(
                            text = date.toString(),
                            fontSize = 16.sp,
                            fontFamily = Cinzel,
                            color = textColor,
                            modifier = Modifier,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TimeSlotDialog(
    modifier: Modifier = Modifier,
    witch: Witch,
    date: String,
    slots: List<Slot>,
    onSlotSelected: (Slot) -> Unit,
    onDismiss: () -> Unit,
) {
    CovenBookingAlertDialog(onDismiss = onDismiss) {
        TimeSlotDialogContent(
            modifier = modifier,
            witch = witch,
            date = date,
            slots = slots,
            onSlotSelected = onSlotSelected
        )
    }
}

@Composable
fun TimeSlotDialogContent(
    modifier: Modifier = Modifier,
    witch: Witch,
    date: String,
    slots: List<Slot>,
    onSlotSelected: (Slot) -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        val slotsWithDate = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.textSecondary,
                    fontFamily = Montserrat
                )
            ) {
                append(stringResource(R.string.time_slots))
            }
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.textPrimary,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.SemiBold
                )
            ) {
                append(" $date")
            }
        }
        Text(
            text = slotsWithDate,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(top = 8.dp)
                .padding(horizontal = 20.dp),
        )


        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(vertical = 12.dp)
        ) {
            for (slot in slots) {
                TimeSlot(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    witch = witch,
                    slot = slot,
                    onSlotSelected = onSlotSelected
                )
            }
        }
    }
}

class TimeSlotPreviewParameterProvider : CollectionPreviewParameterProvider<Slot>(
    listOf(
        Slot(
            time = "10:00",
            status = SlotStatus.Available
        ),
        Slot(
            time = "11:00",
            status = SlotStatus.Reserved(by = Witch.MORGANA)
        ),
        Slot(
            time = "12:00",
            status = SlotStatus.Reserved(by = Witch.CIRCE)
        )
    )
)

@Preview
@Composable
private fun TimeSlotPreview(
    @PreviewParameter(provider = TimeSlotPreviewParameterProvider::class)
    data: Slot
) {
    OctoberTheme {
        TimeSlot(
            witch = Witch.MORGANA,
            slot = data
        ) {}
    }
}

@Composable
fun TimeSlot(
    modifier: Modifier = Modifier,
    witch: Witch,
    slot: Slot,
    onSlotSelected: (Slot) -> Unit
) {
    var modifier = modifier
    var textColor: Color
    var statusText: String
    var slotTimeWeight = FontWeight.Normal
    var reservedTextWeight = FontWeight.Normal
    when (slot.status) {
        SlotStatus.Available -> {
            modifier = modifier
                .border(1.dp, color = MaterialTheme.colorScheme.frameInactive)
                .clickable {
                    onSlotSelected(slot)
                }
            textColor = MaterialTheme.colorScheme.textPrimary
            statusText = stringResource(R.string.available)
        }

        is SlotStatus.Reserved -> {
            if (slot.status.by == witch) {
                modifier = modifier
                    .background(
                        color = MaterialTheme.colorScheme.frameInactive,
                    )
                textColor = MaterialTheme.colorScheme.textSecondary
                slotTimeWeight = FontWeight.Bold
                reservedTextWeight = FontWeight.SemiBold
                statusText =
                    stringResource(R.string.reserved_by, stringResource(witch.getStringRes()))
            } else {
                textColor = MaterialTheme.colorScheme.textPrimary
                statusText = stringResource(R.string.reserved)
            }
            textColor
        }
    }
    modifier = modifier
        .padding(vertical = 8.dp, horizontal = 12.dp)
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = slot.time,
            fontFamily = Cinzel,
            fontSize = 16.sp,
            color = textColor,
            fontWeight = slotTimeWeight
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = statusText,
            fontFamily = Cinzel,
            fontSize = 16.sp,
            color = textColor,
            fontWeight = reservedTextWeight
        )
    }
}

@Composable
fun SlotBookingConfirmationDialog(
    modifier: Modifier = Modifier,
    witch: Witch,
    date: String,
    slot: Slot,
    onDismiss: () -> Unit, onConfirm: () -> Unit
) {
    CovenBookingAlertDialog(onDismiss = onDismiss, modifier = modifier) {
        val text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Normal
                )
            ) {
                append("Book ")
            }
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.SemiBold
                )
            ) {
                append(stringResource(witch.getStringRes()))
            }
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Normal
                )
            ) {
                append(" for ")
            }
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.SemiBold
                )
            ) {
                append("$date at ${slot.time}?")
            }
        }
        Text(
            text = text,
            fontSize = 21.sp,
            fontFamily = Montserrat,
            color = MaterialTheme.colorScheme.textPrimary,
            modifier = Modifier.padding(20.dp)
        )

        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.frameInactive,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            Spacer(modifier = Modifier.weight(1F))
            Text(
                text = stringResource(R.string.cancel),
                fontFamily = Montserrat,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.textPrimary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .clickable {
                        onDismiss()
                    }
                    .padding(horizontal = 12.dp, vertical = 10.dp)
            )
            Text(
                text = stringResource(R.string.ok),
                fontFamily = Montserrat,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.textPrimary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .clickable {
                        onConfirm()
                    }
                    .padding(horizontal = 12.dp, vertical = 10.dp)
            )
        }
    }
}