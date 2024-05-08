package com.example.recipeapp.ui.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.example.recipeapp.RecipeApplication
import com.example.recipeapp.di.AppContainer
import com.example.recipeapp.ui.compose.screens.CategoriesScreen
import com.example.recipeapp.ui.compose.screens.NavigationViewModel
import com.example.recipeapp.ui.compose.screens.RecipesListScreen
import com.example.recipeapp.ui.compose.screens.Screen
import com.example.recipeapp.ui.compose.screens.ScreenState

class ComposeMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appContainer = (this.application as RecipeApplication).appContainer

        setContent {
            RecipeAppNavigation(
                appContainer = appContainer,
            )
        }
    }

}

@Composable
fun RecipeAppNavigation(
    appContainer: AppContainer,
) {

    val categoriesListViewModel = appContainer.getCategoriesListViewModelFactory().create()
    val recipesListViewModel = appContainer.getRecipesListViewModelFactory().create()
    val navigationViewModel = appContainer.getNavigationViewModelFactory().create()
    val screenState: ScreenState by navigationViewModel.currentScreen.observeAsState(ScreenState())

    when (screenState.currentScreen) {
        is Screen.CategoriesScreen -> {
            CategoriesScreen(
                navigateTo = navigationViewModel::navigateTo,
                categoriesListViewModel = categoriesListViewModel
            )
        }

        is Screen.Favorites -> {
//            CategoriesScreen(categoriesListViewModel = categoriesListViewModel)
        }

        is Screen.RecipesList -> {
            RecipesListScreen(
                navigateTo = navigationViewModel::navigateTo,
                recipesListViewModel = recipesListViewModel
            )
        }
    }

}
