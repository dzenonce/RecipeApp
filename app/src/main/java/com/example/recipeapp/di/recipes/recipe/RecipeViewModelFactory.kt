package com.example.recipeapp.di.recipes.recipe

import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.di.Factory
import com.example.recipeapp.ui.recipes.recipe.RecipeViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

class RecipeViewModelFactory(
    private val recipeRepository: RecipeRepository,
    private val exceptionHandler: CoroutineExceptionHandler,
) : Factory<RecipeViewModel> {

    override fun create(): RecipeViewModel {
        return RecipeViewModel(
            recipeRepository = recipeRepository,
            exceptionHandler = exceptionHandler,
        )
    }
}