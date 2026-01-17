package com.seenu.dev.android.january26.january_recipe_refresh

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.seenu.dev.android.january26.R
import com.seenu.dev.android.january26.theme.InstrumentSans
import com.seenu.dev.android.january26.theme.InstrumentSerif
import com.seenu.dev.android.january26.theme.JanuaryRecipeRefreshTheme

@Preview
@Composable
private fun JanuaryRecipeRefreshPreview() {
    JanuaryRecipeRefreshTheme {
        JanuaryRecipeRefresh()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JanuaryRecipeRefresh() {

    val viewModel: JanuaryRecipeRefreshViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel) {
        viewModel.snackBarMessage.collect { _ ->
            snackBarHostState.showSnackbar("Added to Favourites")
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) { data ->
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Row(
                        modifier = Modifier
                            .wrapContentSize()
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = MaterialTheme.shapes.medium
                            )
                            .padding(horizontal = 20.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_heart_rounded),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Added to Favourites",
                            fontSize = 16.sp,
                            fontFamily = InstrumentSans,
                            color = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "January Recipes",
                        fontFamily = InstrumentSerif,
                        fontSize = 40.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { innerPadding ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val maxWidth = this.maxWidth

            val columnCount = if (maxWidth > 600.dp) 2 else 1

            val state = rememberPullToRefreshState()
            PullToRefreshBox(
                isRefreshing = uiState.isLoading,
                state = state,
                onRefresh = {
                    viewModel.onIntent(JanuaryRecipeIntent.RefreshData)
                },
                indicator = {
                    Indicator(
                        modifier = Modifier.align(Alignment.TopCenter),
                        isRefreshing = uiState.isLoading,
                        state = state,
                        color = MaterialTheme.colorScheme.primary,
                        containerColor = MaterialTheme.colorScheme.background
                    )
                },
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 12.dp)
                ) {
                    BasicTextField(
                        value = uiState.searchQuery,
                        onValueChange = {
                            viewModel.onIntent(JanuaryRecipeIntent.UpdateSearchQuery(it))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .background(
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                shape = MaterialTheme.shapes.medium
                            )
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outline,
                                shape = MaterialTheme.shapes.medium
                            )
                            .padding(horizontal = 14.dp, vertical = 12.dp),
                        textStyle = TextStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 16.sp,
                            fontFamily = InstrumentSans,
                            lineHeight = 24.sp,
                            letterSpacing = 0.5.sp
                        ),
                        decorationBox = { innerTextField ->
                            if (uiState.searchQuery.isEmpty()) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_search),
                                        contentDescription = "Search",
                                        tint = MaterialTheme.colorScheme.secondary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Search for recipes",
                                        style = TextStyle(
                                            color = MaterialTheme.colorScheme.primary,
                                            fontSize = 16.sp,
                                            fontFamily = InstrumentSans,
                                            lineHeight = 24.sp,
                                            letterSpacing = 0.5.sp
                                        )
                                    )
                                }
                            }
                            innerTextField()
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    val lazyListState = rememberLazyGridState()

                    LaunchedEffect(uiState.recipes) {
                        lazyListState.scrollToItem(0)
                    }
                    val keyboardController = LocalSoftwareKeyboardController.current
                    val focusManager = LocalFocusManager.current

                    LazyVerticalGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1F)
                            .padding(horizontal = 10.dp),
                        state = lazyListState,
                        columns = GridCells.Fixed(columnCount),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(uiState.recipes.size, key = { index ->
                            uiState.recipes[index].title
                        }) { index ->
                            val recipe = uiState.recipes[index]
                            RecipeCard(
                                recipe,
                                isFavourite = uiState.favourites.contains(recipe),
                                onToggleFavourite = {
                                    focusManager.clearFocus(true)
                                    keyboardController?.hide()
                                    viewModel.onIntent(
                                        JanuaryRecipeIntent.ToggleFavourite(
                                            recipe = recipe,
                                            isFavorite = it
                                        )
                                    )
                                },
                                onClick = {
                                    focusManager.clearFocus(true)
                                    keyboardController?.hide()
                                    viewModel.onIntent(JanuaryRecipeIntent.SelectRecipe(recipe))
                                },
                                modifier = Modifier.animateItem()
                            )
                        }

                        if (uiState.recipes.isEmpty()) {
                            item {
                                Text(
                                    text = "No recipes match your search",
                                    fontFamily = InstrumentSerif,
                                    fontSize = 28.sp,
                                    color = MaterialTheme.colorScheme.primary,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 16.dp)
                                )
                            }
                        }
                    }
                }
            }

            if (uiState.activeRecipe != null) {
                RecipeDetailDialog(
                    onDismissRequest = {
                        viewModel.onIntent(JanuaryRecipeIntent.RemoveRecipe)
                    },
                    recipe = uiState.activeRecipe!!,
                )
            }
        }
    }
}

@Preview
@Composable
private fun RecipeCardPreview() {
    JanuaryRecipeRefreshTheme {
        RecipeCard(
            recipe = Recipe.CREAMY_MUSHROOM_SOUP,
            onClick = {},
            onToggleFavourite = {},
            isFavourite = true
        )
    }
}

@Composable
fun RecipeCard(
    recipe: Recipe,
    isFavourite: Boolean,
    onToggleFavourite: (Boolean) -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.medium)
            .clickable(onClick = onClick)
            .padding(vertical = 6.dp, horizontal = 6.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = MaterialTheme.shapes.medium)
        ) {
            Image(
                painter = painterResource(recipe.imageRes),
                contentDescription = recipe.title,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )

            IconButton(
                onClick = {
                    onToggleFavourite(!isFavourite)
                }, modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.scrim
                )
            ) {
                Icon(
                    painter = painterResource(if (isFavourite) R.drawable.ic_heart_filled else R.drawable.ic_heart_rounded),
                    contentDescription = "Favorite",
                    tint = Color.White
                )
            }
        }
        Text(
            text = recipe.title,
            fontFamily = InstrumentSerif,
            fontSize = 28.sp,
            color = MaterialTheme.colorScheme.primary
        )
    }
}