package com.example.recipeapp.ui.recipes.recipe

import androidx.lifecycle.ViewModel
import com.example.recipeapp.model.Ingredient

class RecipeViewModel: ViewModel() {

    data class RecipeUiState(
        var isFavorite: Boolean = false,
        var recipeName: String? = null,
        var ingredients: List<Ingredient> = listOf(),
        var method: List<String> = listOf(),
    )

}