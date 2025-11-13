package com.seenu.dev.android.november25.state

import com.seenu.dev.android.november25.R
import com.seenu.dev.android.november25.screens.GlobalBlackFridayDealsProduct

object GlobalBlackFridayDealsProductData {

    fun getProductsForLanguage(language: Language): List<GlobalBlackFridayDealsProduct> {
        return when (language) {
            Language.ENGLISH -> englishProducts
            Language.SPANISH -> spanishProducts
            Language.ARABIC -> arabicProducts
        }
    }

    val englishProducts = listOf(
        GlobalBlackFridayDealsProduct(
            id = 1L,
            name = "Guess VINCENT Sneakers",
            price = 160,
            discount = 38,
            discountedPrice = 100,
            imageRes = R.drawable.guess_vincent,
            isFavourite = false
        ),
        GlobalBlackFridayDealsProduct(
            id = 2L,
            name = "Armani Exchange T-Shirt 2-pack",
            price = 81,
            discount = 56,
            discountedPrice = 35,
            imageRes = R.drawable.armani_tshirt,
            isFavourite = false
        ),
        GlobalBlackFridayDealsProduct(
            id = 3L,
            name = "Gant Cotton T-Shirt",
            price = 57,
            discount = 19,
            discountedPrice = 46,
            imageRes = R.drawable.gant_tshirt,
            isFavourite = false
        ),
        GlobalBlackFridayDealsProduct(
            id = 4L,
            name = "United Colors of Benetton Cotton Shirt",
            price = 40,
            discount = 33,
            discountedPrice = 27,
            imageRes = R.drawable.benetton_cotton,
            isFavourite = false
        ),
        GlobalBlackFridayDealsProduct(
            id = 5L,
            name = "United Colors of Benetton Linen Blend Polo",
            price = 75,
            discount = 32,
            discountedPrice = 51,
            imageRes = R.drawable.benetton_linen,
            isFavourite = false
        ),
        GlobalBlackFridayDealsProduct(
            id = 6L,
            name = "Daniel Wellington Watch",
            price = 177,
            discount = 13,
            discountedPrice = 153,
            imageRes = R.drawable.daniel_watch,
            isFavourite = false
        ),
        GlobalBlackFridayDealsProduct(
            id = 7L,
            name = "Calvin Klein Polo",
            price = 103,
            discount = 61,
            discountedPrice = 40,
            imageRes = R.drawable.calvin_polo,
            isFavourite = false
        ),
        GlobalBlackFridayDealsProduct(
            id = 8L,
            name = "Columbia Skien Valley Outdoor Jacket",
            price = 111,
            discount = 21,
            discountedPrice = 86,
            imageRes = R.drawable.columbia_jacket,
            isFavourite = false
        )
    )
    val spanishProducts = listOf(
        GlobalBlackFridayDealsProduct(
            id = 1L,
            name = "Zapatillas Guess VINCENT",
            price = 160,
            discount = 38,
            discountedPrice = 100,
            imageRes = R.drawable.guess_vincent,
            isFavourite = false
        ),
        GlobalBlackFridayDealsProduct(
            id = 2L,
            name = "Pack de 2 Camisetas Armani Exchange",
            price = 81,
            discount = 56,
            discountedPrice = 35,
            imageRes = R.drawable.armani_tshirt,
            isFavourite = false
        ),
        GlobalBlackFridayDealsProduct(
            id = 3L,
            name = "Camiseta de Algodón Gant",
            price = 57,
            discount = 19,
            discountedPrice = 46,
            imageRes = R.drawable.gant_tshirt,
            isFavourite = false
        ),
        GlobalBlackFridayDealsProduct(
            id = 4L,
            name = "Camisa de Algodón United Colors of Benetton",
            price = 40,
            discount = 33,
            discountedPrice = 27,
            imageRes = R.drawable.benetton_cotton,
            isFavourite = false
        ),
        GlobalBlackFridayDealsProduct(
            id = 5L,
            name = "Polo de Lino United Colors of Benetton",
            price = 75,
            discount = 32,
            discountedPrice = 51,
            imageRes = R.drawable.benetton_linen,
            isFavourite = false
        ),
        GlobalBlackFridayDealsProduct(
            id = 6L,
            name = "Reloj Daniel Wellington",
            price = 177,
            discount = 13,
            discountedPrice = 153,
            imageRes = R.drawable.daniel_watch,
            isFavourite = false
        ),
        GlobalBlackFridayDealsProduct(
            id = 7L,
            name = "Polo Calvin Klein",
            price = 103,
            discount = 61,
            discountedPrice = 40,
            imageRes = R.drawable.calvin_polo,
            isFavourite = false
        ),
        GlobalBlackFridayDealsProduct(
            id = 8L,
            name = "Chaqueta Outdoor Columbia Skien Valley",
            price = 111,
            discount = 21,
            discountedPrice = 86,
            imageRes = R.drawable.columbia_jacket,
            isFavourite = false
        )
    )
    val arabicProducts = listOf(
        GlobalBlackFridayDealsProduct(
            id = 1L,
            name = "أحذية رياضية غيس فنسنت",
            price = 160,
            discount = 38,
            discountedPrice = 100,
            imageRes = R.drawable.guess_vincent,
            isFavourite = false
        ),
        GlobalBlackFridayDealsProduct(
            id = 2L,
            name = "حزمة من 2 تي شيرت من أرماني إكستشينج",
            price = 81,
            discount = 56,
            discountedPrice = 35,
            imageRes = R.drawable.armani_tshirt,
            isFavourite = false
        ),
        GlobalBlackFridayDealsProduct(
            id = 3L,
            name = "تي شيرت قطني من غانت",
            price = 57,
            discount = 19,
            discountedPrice = 46,
            imageRes = R.drawable.gant_tshirt,
            isFavourite = false
        ),
        GlobalBlackFridayDealsProduct(
            id = 4L,
            name = "قميص قطني من يونايتد كولورز أوف بينيتون",
            price = 40,
            discount = 33,
            discountedPrice = 27,
            imageRes = R.drawable.benetton_cotton,
            isFavourite = false
        ),
        GlobalBlackFridayDealsProduct(
            id = 5L,
            name = "بولو بخليط الكتان من يونايتد كولورز أوف بينيتون",
            price = 75,
            discount = 32,
            discountedPrice = 51,
            imageRes = R.drawable.benetton_linen,
            isFavourite = false
        ),
        GlobalBlackFridayDealsProduct(
            id = 6L,
            name = "ساعة دانيال ويلينغتون",
            price = 177,
            discount = 13,
            discountedPrice = 153,
            imageRes = R.drawable.daniel_watch,
            isFavourite = false
        ),
        GlobalBlackFridayDealsProduct(
            id = 7L,
            name = "بولو كالفن كلاين",
            price = 103,
            discount = 61,
            discountedPrice = 40,
            imageRes = R.drawable.calvin_polo,
            isFavourite = false
        ),
        GlobalBlackFridayDealsProduct(
            id = 8L,
            name = "سترة خارجية كولومبيا سكيين فالي",
            price = 111,
            discount = 21,
            discountedPrice = 86,
            imageRes = R.drawable.columbia_jacket,
            isFavourite = false
        )
    )


}

enum class Language {
    ENGLISH,
    SPANISH,
    ARABIC
}