package com.example.recipeapp.ui.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.recipeapp.RecipeApplication
import dagger.hilt.android.AndroidEntryPoint


class ComposeMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val appContainer = (this.application as RecipeApplication).appContainer
//        val categoriesListViewModel = appContainer.getCategoriesListViewModelFactory().create()
//        categoriesListViewModel.uiState.observe(this) {
//            setContent {
//                CategoriesScreen(
//                    categoriesList = it.categoriesList
//                )
//            }
//        }
//        categoriesListViewModel.loadCategories()
    }
}