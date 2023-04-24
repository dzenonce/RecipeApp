package com.example.recipeapp.ui.recipes.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.API_RECIPE_IMAGE_URL
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.model.Recipe
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

data class RecipeUiState(
    var isFavorite: Boolean = false,
    var recipe: Recipe? = null,
    var recipeImageUrl: String = "",
)

class RecipeViewModel(
    private val recipeRepository: RecipeRepository,
    private val exceptionHandler: CoroutineExceptionHandler,
) : ViewModel() {

    private val _uiState = MutableLiveData<RecipeUiState>()
    val uiState: LiveData<RecipeUiState> = _uiState

    fun loadRecipe(recipeId: Int) {
        viewModelScope.launch(exceptionHandler) {
            var recipe = recipeRepository.recipesCache.getRecipeFromCache(recipeId)
            if (recipe == null) recipeRepository.loadRecipeByRecipeId(recipeId).let { recipe = it }
            _uiState.value =
                RecipeUiState(
                    isFavorite = recipe?.isFavorite ?: false,
                    recipe = recipe,
                    recipeImageUrl = "$API_RECIPE_IMAGE_URL/${recipe?.imageUrl}"
                )
        }
    }

    fun onFavoritesClicked(recipeId: Int) {
        viewModelScope.launch(exceptionHandler) {
            if (uiState.value?.isFavorite == true) {
                _uiState.value?.isFavorite = false
                recipeRepository.recipesCache.deleteRecipeFromFavorite(recipeId)
            } else {
                _uiState.value?.isFavorite = true
                recipeRepository.recipesCache.addRecipeToFavorite(recipeId)
            }
        }
    }
}