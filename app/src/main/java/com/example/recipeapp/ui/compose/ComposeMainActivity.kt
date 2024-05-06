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
import com.example.recipeapp.ui.compose.screens.RecipesListScreen
import com.example.recipeapp.ui.compose.screens.Screen
import com.example.recipeapp.ui.compose.screens.ScreenUiState
import com.example.recipeapp.ui.compose.screens.ScreenViewModel

class ComposeMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val startScreen = Screen.CategoriesScreen
        val appContainer = (this.application as RecipeApplication).appContainer
        val screenViewModel = appContainer.getScreenViewModelFactory().create()

        setContent {
            val currentScreen by screenViewModel.screenState.observeAsState(ScreenUiState())
            AppNavigator(
                screenViewModel = screenViewModel,
                currentScreen = currentScreen.currentScreen ?: startScreen,
                appContainer = appContainer
            )
        }
    }

}

@Composable
fun AppNavigator(screenViewModel: ScreenViewModel, currentScreen: Screen, appContainer: AppContainer) {

    val categoriesListViewModel = appContainer.getCategoriesListViewModelFactory().create()
    val recipesListViewModel = appContainer.getRecipesListViewModelFactory().create()

    when (currentScreen) {
        is Screen.CategoriesScreen -> {
            CategoriesScreen(
                screenViewModel = screenViewModel,
                categoriesListViewModel = categoriesListViewModel)
        }

        is Screen.Favorites -> {
//            CategoriesScreen(categoriesListViewModel = categoriesListViewModel)
        }

        is Screen.RecipesList -> {
            RecipesListScreen(recipesListViewModel = recipesListViewModel)
        }
    }

}
