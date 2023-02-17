package com.example.recipeapp.ui.recipes.recipe

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipeapp.model.Ingredient
import com.example.recipeapp.model.Recipe

class RecipeViewModel: ViewModel() {

    data class RecipeUiState(
        var isFavorite: Boolean = false,
        var recipe: Recipe? = null,
        var portionCount: List<String> = listOf(),
    )

    private val _uiState = MutableLiveData<RecipeUiState>()
    val uiState: MutableLiveData<RecipeUiState> = _uiState

}