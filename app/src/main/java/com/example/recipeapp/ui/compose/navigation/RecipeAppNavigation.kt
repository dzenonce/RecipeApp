package com.example.recipeapp.ui.compose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.example.recipeapp.di.AppContainer
import com.example.recipeapp.ui.compose.screens.CategoriesScreen
import com.example.recipeapp.ui.compose.screens.FavoriteScreen
import com.example.recipeapp.ui.compose.screens.RecipesListScreen

@Composable
fun RecipeAppNavigation(
    appContainer: AppContainer,
) {

    val navigationViewModel = appContainer.getNavigationViewModelFactory().create()
    val categoriesListViewModel = appContainer.getCategoriesListViewModelFactory().create()
    val recipesListViewModel = appContainer.getRecipesListViewModelFactory().create()
    val favoritesViewModel = appContainer.getFavoritesViewModelFactory().create()

    val screenState: ScreenState by navigationViewModel.currentScreen.observeAsState(ScreenState())
    when (screenState.currentScreen) {
        is Screen.CategoriesScreen -> {
            CategoriesScreen(
                navigateTo = navigationViewModel::navigateTo,
                categoriesListViewModel = categoriesListViewModel
            )
        }

        is Screen.RecipesList -> {
            val categoryInfo = (screenState.currentScreen as Screen.RecipesList)
            RecipesListScreen(
                categoryId = categoryInfo.categoryId,
                categoryName = categoryInfo.categoryName,
                categoryImageUrl = categoryInfo.categoryImageUrl,
                navigateTo = navigationViewModel::navigateTo,
                recipesListViewModel = recipesListViewModel,
            )
        }

        is Screen.Favorites -> {
            FavoriteScreen(
                navigateTo = navigationViewModel::navigateTo,
                favoritesViewModel = favoritesViewModel,
            )
        }

    }
}
