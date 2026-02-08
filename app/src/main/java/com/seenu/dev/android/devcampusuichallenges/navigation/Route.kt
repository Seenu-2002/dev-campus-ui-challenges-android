package com.seenu.dev.android.devcampusuichallenges.navigation

import android.net.Uri
import androidx.navigation3.runtime.NavKey
import com.seenu.dev.android.february26.shared_valentine.components.ValentineGift
import com.seenu.dev.android.january26.holiday_movie_collection.data.entity.CollectionWithMovie
import com.seenu.dev.android.january26.state.Destination
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey {
    @Serializable
    data object ListScreen : Route
    @Serializable
    data object June : Route

    @Serializable
    data object July : Route

    @Serializable
    data object July2 : Route

    @Serializable
    data object July3 : Route

    @Serializable
    data object August1 : Route

    @Serializable
    data object August2 : Route

    @Serializable
    data object August3 : Route

    @Serializable
    data object September1 : Route

    @Serializable
    data object September2 : Route

    @Serializable
    data object September3 : Route

    @Serializable
    data object September4 : Route

    @Serializable
    data object September5 : Route

    @Serializable
    data object October2 : Route

    @Serializable
    data object October3 : Route

    @Serializable
    data object October4 : Route

    @Serializable
    data object October5 : Route

    @Serializable
    data object November1 : Route

    @Serializable
    data object November2 : Route

    @Serializable
    data object November3 : Route

    @Serializable
    data object November4 : Route

    @Serializable
    data class November4CompareScreen constructor(
        val product1Id: String,
        val product2Id: String
    ) : Route

    @Serializable
    data object November5 : Route

    @Serializable
    data object December2 : Route

    @Serializable
    data object December3 : Route

    @Serializable
    data object December4 : Route

    @Serializable
    data object December5 : Route

    @Serializable
    data object January1 : Route

    @Serializable
    data class January1_1 constructor(val destination: Destination) : Route

    @Serializable
    data object January2: Route

    @Serializable
    data object January3 : Route

    @Serializable
    data object January3_1: Route

    @Serializable
    data class January3_2 constructor(@Contextual val collectionWithMovie: CollectionWithMovie) : Route

    @Serializable
    data object January4 : Route

    @Serializable
    data object January5 : Route

    @Serializable
    data class January5_1 constructor(val uri: String) : Route

    @Serializable
    data object SharedValentineTypeChooser : Route

    @Serializable
    data object SharedValentineSender : Route

    @Serializable
    data class SharedValentineReceiver(val receivedGift: ValentineGift?) : Route

}