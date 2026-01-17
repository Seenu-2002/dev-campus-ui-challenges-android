package com.seenu.dev.android.devcampusuichallenges.navigation.month

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import com.seenu.dev.android.devcampusuichallenges.navigation.Route
import com.seenu.dev.android.january26.fresh_start_settings.FreshStartSettings
import com.seenu.dev.android.january26.profile_avatar_editor.ProfileAvatarEditor
import com.seenu.dev.android.january26.WinterTravelGallery
import com.seenu.dev.android.january26.WinterTravelGalleryDetail
import com.seenu.dev.android.january26.holiday_movie_collection.presentation.CollectionDetailScreen
import com.seenu.dev.android.january26.holiday_movie_collection.presentation.CreateCollectionScreen
import com.seenu.dev.android.january26.holiday_movie_collection.presentation.HolidayMovieCollection
import com.seenu.dev.android.january26.january_recipe_refresh.JanuaryRecipeRefresh
import com.seenu.dev.android.january26.profile_avatar_editor.AvatarEditorScreen
import com.seenu.dev.android.january26.theme.FreshStartSettingsTheme
import com.seenu.dev.android.january26.theme.HolidayMovieCollectionTheme
import com.seenu.dev.android.january26.theme.JanuaryRecipeRefreshTheme
import com.seenu.dev.android.january26.theme.JanuaryTheme
import com.seenu.dev.android.january26.theme.ProfileAvatarEditorTheme

@Composable
fun EntryProviderBuilder<NavKey>.JanuaryNavigation(
    backStack: NavBackStack,
    onNavigateBack: () -> Unit
) {
    entry<Route.January1> {
        JanuaryTheme {
            WinterTravelGallery(openDestination = {
                backStack.add(Route.January1_1(it))
            })
        }
    }

    entry<Route.January1_1> {
        JanuaryTheme {
            WinterTravelGalleryDetail(
                destination = it.destination,
                onBack = onNavigateBack
            )
        }
    }

    entry<Route.January2> {
        FreshStartSettingsTheme {
            FreshStartSettings(onBack = onNavigateBack)
        }
    }

    entry<Route.January3> {
        key(it) {
            HolidayMovieCollectionTheme {
                HolidayMovieCollection(
                    openAddCollectionScreen = {
                        backStack.add(Route.January3_1)
                    },
                    openCollectionDetailScreen = {
                        backStack.add(Route.January3_2(it))
                    }
                )
            }
        }
    }

    entry<Route.January3_1> {
        key(it) {
            HolidayMovieCollectionTheme {
                CreateCollectionScreen(onBack = onNavigateBack)
            }
        }
    }

    entry<Route.January3_2> {
        key(it) {
            HolidayMovieCollectionTheme {
                CollectionDetailScreen(
                    collectionWithMovie = it.collectionWithMovie,
                    onBack = onNavigateBack
                )
            }
        }
    }

    entry<Route.January4> {
        JanuaryRecipeRefreshTheme {
            JanuaryRecipeRefresh()
        }
    }

    var isImageUpdatedFlag: Any? by remember { mutableStateOf(null) }

    entry<Route.January5> {
        ProfileAvatarEditorTheme {
            ProfileAvatarEditor(isImageUpdatedFlag, openEditorScreen = {
                backStack.add(Route.January5_1(Uri.encode(it.toString())))
            })
        }
    }

    entry<Route.January5_1> {
        ProfileAvatarEditorTheme {
            AvatarEditorScreen(
                uri = it.uri,
                onBack = onNavigateBack,
                onImageSaved = {
                    isImageUpdatedFlag = Any()
                    onNavigateBack()
                }
            )
        }
    }
}