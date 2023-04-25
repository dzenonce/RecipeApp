package com.example.recipeapp.ui.recipes.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.model.Recipe
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

data class FavoritesUiState(
    var favoritesRecipesList: List<Recipe> = emptyList(),
)

class FavoritesViewModel(
    private val recipeRepository: RecipeRepository,
    private val exceptionHandler: CoroutineExceptionHandler
) : ViewModel() {

    private var _uiState = MutableLiveData<FavoritesUiState>()
    val uiState: LiveData<FavoritesUiState> = _uiState

    fun loadFavorites() {
        viewModelScope.launch(exceptionHandler) {
            _uiState.value =
                FavoritesUiState(
                    recipeRepository.recipesCache.getFavoritesFromCache()
                        ?: emptyList()
                )
        }
    }
}