package com.example.recipeapp.di.categories

import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.di.Factory
import com.example.recipeapp.ui.xmlUi.category.CategoriesListViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

class CategoriesListViewModelFactory(
    private val recipeRepository: RecipeRepository,
    private val exceptionHandler: CoroutineExceptionHandler,
) : Factory<CategoriesListViewModel> {
    override fun create(): CategoriesListViewModel {
        return CategoriesListViewModel(recipeRepository, exceptionHandler)
    }
}