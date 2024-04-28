package com.example.recipeapp.di.recipes.favorites

import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.di.Factory
import com.example.recipeapp.ui.xmlUi.recipes.favorites.FavoritesViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

class FavoritesViewModelFactory(
    private val recipeRepository: RecipeRepository,
    private val exceptionHandler: CoroutineExceptionHandler,
) : Factory<FavoritesViewModel> {

    override fun create(): FavoritesViewModel {
        return FavoritesViewModel(
            recipeRepository = recipeRepository,
            exceptionHandler = exceptionHandler,
        )
    }
}