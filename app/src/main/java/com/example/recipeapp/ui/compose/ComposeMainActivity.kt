package com.example.recipeapp.ui.compose

import android.os.Bundle
import android.text.Editable.Factory
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipeapp.RecipeApplication
import com.example.recipeapp.di.AppContainer
import com.example.recipeapp.ui.compose.screens.CategoriesScreen
import com.example.recipeapp.ui.xmlUi.category.CategoriesListViewModel

//enum Screens( // sealed
//    CategoriesList(1)
//)

sealed class Screens(
) {
    object CategoriesScreen: Screens()
    object RecipesList: Screens()
    object Favorites: Screens()
}

class ComposeMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appContainer = (this.application as RecipeApplication).appContainer
        val categoriesListViewModel = appContainer.getCategoriesListViewModelFactory().create()

//        val currentScreen = Screens.CategoriesList(categoriesListViewModel)
//        categoriesListViewModel.uiState.observe(this) {
        setContent {
                CategoriesScreen(
                    categoriesListViewModel,
//                    { currentScreen = it },
//                    categoriesList = it.categoriesList
                )
            }
//        }
//        categoriesListViewModel.loadCategories()
    }
}