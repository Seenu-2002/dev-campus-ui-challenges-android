package com.seenu.dev.android.august25.viewmodels

import androidx.lifecycle.ViewModel

class ParcePigeonViewModel : ViewModel() {

    private val _count = 6
    private val smallPicUrl = "https://picsum.photos/20D"
    private val largePicUrl = "https://source.unsplash.com/random/800x800"

}

data class ParcelPigeonState(
    val count: Int,
    val smallPicUrl: String,
    val largePicUrl: String
)