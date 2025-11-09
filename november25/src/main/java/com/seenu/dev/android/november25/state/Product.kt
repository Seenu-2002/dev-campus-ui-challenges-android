package com.seenu.dev.android.november25.state

data class Product constructor(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val countInCart: Int,
    val imageRes: Int,
    val discount: Double? = null
)