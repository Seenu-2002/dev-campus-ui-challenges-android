package com.seenu.dev.android.january26.january_recipe_refresh

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seenu.dev.android.january26.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class JanuaryRecipeRefreshViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<RecipeUiState> = MutableStateFlow(RecipeUiState())
    val uiState: StateFlow<RecipeUiState> = _uiState
        .onStart {
            getData()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = _uiState.value
        )

    private val _snackBarMessage: MutableSharedFlow<String> = MutableSharedFlow()
    val snackBarMessage: SharedFlow<String> = _snackBarMessage.asSharedFlow()

    fun onIntent(intent: JanuaryRecipeIntent) {
        viewModelScope.launch {
            when (intent) {
                JanuaryRecipeIntent.RefreshData -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = true
                    )
                    delay(1000)
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        recipes = getOrderedList(
                            recipes = filter(_uiState.value.searchQuery),
                            favourites = _uiState.value.favourites
                        )
                    )
                }

                JanuaryRecipeIntent.RemoveRecipe -> {
                    _uiState.value = _uiState.value.copy(
                        activeRecipe = null
                    )
                }

                is JanuaryRecipeIntent.SelectRecipe -> {
                    _uiState.value = _uiState.value.copy(
                        activeRecipe = intent.recipe
                    )
                }

                is JanuaryRecipeIntent.ToggleFavourite -> {
                    _uiState.value = if (intent.isFavorite) {
                        val favourites = _uiState.value.favourites + intent.recipe
                        _snackBarMessage.emit("${intent.recipe.title} added to favourites")
                        _uiState.value.copy(
                            favourites = favourites,
                            recipes = getOrderedList(
                                recipes = _uiState.value.recipes,
                                favourites = favourites
                            )
                        )
                    } else {
                        val favourites = _uiState.value.favourites - intent.recipe
                        _uiState.value.copy(
                            favourites = favourites,
                            recipes = getOrderedList(
                                recipes = _uiState.value.recipes,
                                favourites = favourites
                            )
                        )
                    }
                }

                is JanuaryRecipeIntent.UpdateSearchQuery -> {
                    val recipes = filter(intent.query)
                    _uiState.value = _uiState.value.copy(
                        searchQuery = intent.query,
                        recipes = getOrderedList(
                            recipes = recipes,
                            favourites = _uiState.value.favourites
                        )
                    )

                }
            }
        }
    }

    private fun getData() {
        _uiState.value = _uiState.value.copy(
            isLoading = false,
            recipes = getOrderedList(
                recipes = Recipe.entries,
                favourites = _uiState.value.favourites
            ),
        )
    }

    private fun getOrderedList(recipes: List<Recipe>, favourites: List<Recipe>): List<Recipe> {
        val (favourites, others) = recipes.partition { it in favourites }
        return favourites + others.shuffled()
    }

    private fun filter(searchQuery: String): List<Recipe> {
        if (searchQuery.isBlank()) {
            return Recipe.entries
        }
        val query = searchQuery.trim().lowercase()
        return Recipe.entries.filter {
            it.title.lowercase().contains(query)
        }
    }

}

data class RecipeUiState constructor(
    val isLoading: Boolean = false,
    val recipes: List<Recipe> = Recipe.entries,
    val favourites: List<Recipe> = listOf(),
    val searchQuery: String = "",
    val activeRecipe: Recipe? = null,
)

sealed interface JanuaryRecipeIntent {
    data class ToggleFavourite(val recipe: Recipe, val isFavorite: Boolean) : JanuaryRecipeIntent
    data class UpdateSearchQuery(val query: String) : JanuaryRecipeIntent
    data class SelectRecipe(val recipe: Recipe?) : JanuaryRecipeIntent
    data object RemoveRecipe : JanuaryRecipeIntent
    data object RefreshData : JanuaryRecipeIntent
}

enum class Recipe(
    val imageRes: Int,
    val title: String,
    val ingredients: String,
    val description: String
) {
    CREAMY_MUSHROOM_SOUP(
        R.drawable.creamy_mushroom_soup,
        "Creamy Mushroom Soup",
        "Mushrooms, vegetable broth, onion, garlic, cream, thyme",
        "Sauté onions and garlic until fragrant, then add mushrooms and broth. Simmer gently and finish with cream for a smooth, comforting soup."
    ),
    WINTER_VEGETABLE_STEW(
        R.drawable.winter_vegetable_soup,
        "Winter Vegetable Stew",
        "Carrots, potatoes, parsnips, onion, vegetable broth, rosemary",
        "Cook chopped vegetables with herbs in vegetable broth until tender. Serve hot as a hearty winter stew."
    ),
    SPICED_LENTIL_SOUP(
        R.drawable.spiced_lentil_soup,
        "Spiced Lentil Soup",
        "Red lentils, vegetable broth, carrot, onion, garlic, cumin",
        "Sauté the chopped vegetables with garlic and spices until fragrant, then add lentils and broth. Simmer until the lentils soften and the soup thickens."
    ),
    POTATO_LEEK_COMFORT_SOUP(
        R.drawable.potato_leek_comfort_soup,
        "Potato & Leek Comfort Soup",
        "Potatoes, leeks, butter, vegetable broth, cream",
        "Slowly cook leeks in butter, add potatoes and broth, then simmer until soft. Blend lightly and finish with cream."
    ),
    GINGER_HONEY_TEA(
        R.drawable.ginger_honey_tea,
        "Ginger Honey Tea",
        "Fresh ginger, honey, lemon, water",
        "Steep sliced ginger in hot water, then add honey and lemon. Serve warm for a soothing winter drink."
    ),
    HOT_SPICED_COCOA(
        R.drawable.hot_spiced_cocoa,
        "Hot Spiced Cocoa",
        "Cocoa powder, milk, cinnamon, nutmeg, sugar",
        "Heat milk with cocoa and spices until smooth and rich. Serve warm with a light sprinkle of cinnamon."
    ),
    APPLE_CINNAMON_BREW(
        R.drawable.apple_cinnamon_brew,
        "Apple Cinnamon Brew",
        "Apple slices, cinnamon sticks, cloves, honey, water",
        "Simmer apples and spices in water to release their flavor. Sweeten lightly and serve warm."
    ),
    WARM_BANANA_OAT_MUFFINS(
        R.drawable.warm_banana_oat_muffins,
        "Warm Banana Oat Muffins",
        "Bananas, oats, flour, eggs, honey, baking powder",
        "Mix mashed bananas with oats and batter ingredients, then bake until golden and soft."
    ),
    CINNAMON_SWIRL_ROLLS(
        R.drawable.cinnamon_swirl_rolls,
        "Cinnamon Swirl Rolls",
        "Flour, cinnamon, butter, sugar, yeast",
        "Roll soft dough with cinnamon sugar filling and bake until fluffy. Serve warm for best flavor."
    ),
    BAKED_APPLE_CRISP(
        R.drawable.baked_apple_crisp,
        "Baked Apple Crisp",
        "Apples, oats, butter, brown sugar, cinnamon",
        "Bake sliced apples topped with a crunchy oat mixture until golden and bubbling."
    )
}