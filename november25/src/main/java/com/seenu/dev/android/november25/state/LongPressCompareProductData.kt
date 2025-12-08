package com.seenu.dev.android.november25.state

import com.seenu.dev.android.november25.R
import com.seenu.dev.android.november25.screens.LongPressCompareProduct

val compareProducts = listOf(
    LongPressCompareProduct(
        name = "Google Pixel 9",
        price = 799,
        displayInches = 6.3,
        imageRes = R.drawable.google_pixel_9,
        mainCameraMP = 50,
        frontCameraMP = 12,
        processor = "Tensor G4",
        ramGB = 8,
        storageGB = 128,
        batteryMAh = 4600
    ),
    LongPressCompareProduct(
        name = "Google Pixel 9 Pro",
        price = 999,
        displayInches = 6.7,
        imageRes = R.drawable.google_pixel_9_pro,
        mainCameraMP = 50,
        frontCameraMP = 12,
        processor = "Tensor G4",
        ramGB = 12,
        storageGB = 256,
        batteryMAh = 5100
    ),
    LongPressCompareProduct(
        name = "Samsung Galaxy S24+",
        price = 999,
        salePrice = 899,
        imageRes = R.drawable.samsung_galaxy_s24_,
        displayInches = 6.7,
        mainCameraMP = 50,
        frontCameraMP = 12,
        processor = "Snapdragon 8 Gen 3",
        ramGB = 12,
        storageGB = 256,
        batteryMAh = 4900
    ),
    LongPressCompareProduct(
        name = "OnePlus 12",
        price = 899,
        salePrice = 799,
        imageRes = R.drawable.oneplus_12,
        displayInches = 6.8,
        mainCameraMP = 50,
        frontCameraMP = 32,
        processor = "Snapdragon 8 Gen 3",
        ramGB = 12,
        storageGB = 256,
        batteryMAh = 5400
    ),
    LongPressCompareProduct(
        name = "iPhone 15 Pro",
        price = 1099,
        displayInches = 6.1,
        imageRes = R.drawable.iphone_15_pro,
        mainCameraMP = 48,
        frontCameraMP = 12,
        processor = "A17 Pro",
        ramGB = 8,
        storageGB = 256,
        batteryMAh = 4350
    ),
    LongPressCompareProduct(
        name = "Xiaomi 14",
        price = 799,
        salePrice = 699,
        imageRes = R.drawable.xiaomi_14,
        displayInches = 6.4,
        mainCameraMP = 50,
        frontCameraMP = 32,
        processor = "Snapdragon 8 Gen 3",
        ramGB = 12,
        storageGB = 256,
        batteryMAh = 4610
    )
)
