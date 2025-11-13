package com.seenu.dev.android.november25.screens

import android.R.attr.contentDescription
import android.app.Activity
import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seenu.dev.android.november25.R
import com.seenu.dev.android.november25.state.GlobalBlackFridayDealsProductData
import com.seenu.dev.android.november25.state.Language
import com.seenu.dev.android.november25.theme.HostGrotesk
import com.seenu.dev.android.november25.theme.NovemberTheme
import com.seenu.dev.android.november25.theme.discount
import com.seenu.dev.android.november25.theme.textAlt
import com.seenu.dev.android.november25.theme.textDisabled
import com.seenu.dev.android.november25.theme.textPrimary
import kotlinx.coroutines.delay
import java.util.Locale
import kotlin.apply
import androidx.core.content.edit

@Preview
@Composable
private fun GlobalBlackFridayDealsPreview() {
    NovemberTheme {
        GlobalBlackFridayDeals()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlobalBlackFridayDeals() {

    var language by remember {
        mutableStateOf(Language.ENGLISH)
    }

    val context = LocalContext.current
    val activity = LocalActivity.current

    val sharedPreferences = remember {
        context.getSharedPreferences("app_prefs", android.content.Context.MODE_PRIVATE)
    }

    LaunchedEffect(Unit) {
        val lang = sharedPreferences.getString("language", "ENGLISH") ?: "ENGLISH"

        if (language != Language.valueOf(lang)) {
            activity?.let {
                updateLanguage(it, Language.valueOf(lang))
            }
        }

        language = Language.valueOf(lang)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(), topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_left),
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                },
                title = {
                    Text(
                        text = "Sale",
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontFamily = HostGrotesk,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 22.sp
                    )
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(R.drawable.ic_cart),
                            contentDescription = "Cart",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    LanguageIcon(
                        selectedLanguage = language,
                        onLanguageSelected = { selectedLang ->
                            sharedPreferences.edit {
                                putString("language", selectedLang.name)
                            }
                            activity?.let {
                                updateLanguage(it, selectedLang)
                            }
                        }
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {


            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            ) {
                val items =
                    GlobalBlackFridayDealsProductData.getProductsForLanguage(language = language)

                val columnCount = 2
                items((items.size + 1) / columnCount) { rowIndex ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .height(IntrinsicSize.Min),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        for (columnIndex in 0 until columnCount) {
                            val itemIndex = rowIndex * columnCount + columnIndex
                            if (itemIndex < items.size) {
                                val item = items[itemIndex]
                                GlobalBlackFridayDealsProductCard(
                                    product = item,
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                                )
                            } else {
                                Spacer(modifier = Modifier.weight(1F))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun GlobalBlackFridayDealsProductCardPreview() {
    NovemberTheme {
        GlobalBlackFridayDealsProductCard(
            product = GlobalBlackFridayDealsProduct(
                id = 1L,
                name = "Wireless Headphones",
                price = 199,
                discount = 30,
                discountedPrice = 139,
                imageRes = R.drawable.columbia_jacket,
                isFavourite = false
            )
        )
    }
}

@Composable
fun GlobalBlackFridayDealsProductCard(
    product: GlobalBlackFridayDealsProduct,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(4.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(product.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(178F / 218F),
                contentScale = ContentScale.FillWidth
            )

            Icon(
                painter = painterResource(R.drawable.ic_heart),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.textDisabled,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp),
            )

            Text(
                text = "-${product.discount}%",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = HostGrotesk,
                color = MaterialTheme.colorScheme.textAlt,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.BottomStart)
                    .background(MaterialTheme.colorScheme.discount)
                    .padding(vertical = 2.dp)
                    .padding(start = 8.dp, end = 4.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = product.name,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = HostGrotesk,
            color = MaterialTheme.colorScheme.textPrimary,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "$${product.discountedPrice}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = HostGrotesk,
                color = MaterialTheme.colorScheme.discount,
            )
            Spacer(modifier = Modifier.padding(6.dp))
            Text(
                text = "$${product.price}",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = HostGrotesk,
                color = MaterialTheme.colorScheme.textDisabled,
                textDecoration = TextDecoration.LineThrough
            )
        }
    }
}

data class GlobalBlackFridayDealsProduct constructor(
    val id: Long,
    val name: String,
    val price: Int,
    val discount: Int,
    val discountedPrice: Int,
    val imageRes: Int,
    val isFavourite: Boolean
)

fun Language.getFlagRes(): Int {
    return when (this) {
        Language.ENGLISH -> R.drawable.ic_flag_english
        Language.SPANISH -> R.drawable.ic_flag_spanish
        Language.ARABIC -> R.drawable.ic_flag_arab
    }
}

fun Language.getLocale(): Locale {
    return when (this) {
        Language.ENGLISH -> Locale.forLanguageTag("en")
        Language.SPANISH -> Locale.forLanguageTag("es")
        Language.ARABIC -> Locale.forLanguageTag("ar")
    }
}

fun Language.getDisplayName(): String {
    return when (this) {
        Language.ENGLISH -> "English"
        Language.SPANISH -> "Español"
        Language.ARABIC -> "العربية"
    }
}

@Composable
fun LanguageIcon(
    modifier: Modifier = Modifier,
    selectedLanguage: Language,
    onLanguageSelected: (Language) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(
                painter = painterResource(selectedLanguage.getFlagRes()),
                contentDescription = "Language",
                tint = Color.Unspecified
            )
        }

        val entries = Language.entries.toMutableList()
        entries.remove(selectedLanguage)

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            offset = DpOffset(x = 0.dp, y = 8.dp),
            containerColor = MaterialTheme.colorScheme.surface,
            shape = RectangleShape
        ) {
            for (entry in entries) {
                DropdownMenuItem(
                    leadingIcon = {
                        Icon(
                            painter = painterResource(entry.getFlagRes()),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    },
                    text = {
                        Text(
                            entry.getDisplayName(),
                            fontFamily = HostGrotesk,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp
                        )
                    },
                    onClick = {
                        expanded = false
                        onLanguageSelected(entry)
                    },
                    colors = MenuDefaults.itemColors(
                        textColor = MaterialTheme.colorScheme.textPrimary,
                    ),
                )
            }
        }
    }
}

fun updateLanguage(activity: Activity, language: Language) {
    val resources = activity.resources
    val configuration = resources.configuration

    val systemLocale = Locale.getDefault()
    if (systemLocale.language == language.getLocale().language) {
        return
    }

    val locale = language.getLocale()
    Locale.setDefault(locale)
    configuration.setLocale(locale)
    configuration.setLayoutDirection(locale)
    activity.recreate()
}