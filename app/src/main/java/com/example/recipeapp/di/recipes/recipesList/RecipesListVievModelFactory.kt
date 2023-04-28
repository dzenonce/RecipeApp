package com.example.recipeapp.di.recipes.recipesList

import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.di.Factory
import com.example.recipeapp.ui.xmlUi.recipes.recipesList.RecipesListViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

class RecipesListViewModelFactory(
    private val recipeRepository: RecipeRepository,
    private val exceptionHandler: CoroutineExceptionHandler,
) : Factory<RecipesListViewModel> {

    override fun create(): RecipesListViewModel {
        return RecipesListViewModel(
            recipeRepository = recipeRepository,
            exceptionHandler = exceptionHandler,
        )
    }
}