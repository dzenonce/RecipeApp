package com.example.recipeapp.ui.compose.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.recipeapp.ARG_CATEGORY_ID
import com.example.recipeapp.ARG_RECIPE_ID
import com.example.recipeapp.di.AppContainer
import com.example.recipeapp.ui.compose.components.RecipeNavButton
import com.example.recipeapp.ui.compose.navigation.RecipeMainDestinations.SCREEN_CATEGORIES
import com.example.recipeapp.ui.compose.navigation.RecipeMainDestinations.SCREEN_FAVORITES
import com.example.recipeapp.ui.compose.navigation.RecipeMainDestinations.SCREEN_RECIPE
import com.example.recipeapp.ui.compose.navigation.RecipeMainDestinations.SCREEN_RECIPES_LIST
import com.example.recipeapp.ui.compose.screens.CategoriesScreen
import com.example.recipeapp.ui.compose.screens.FavoriteScreen
import com.example.recipeapp.ui.compose.screens.RecipeScreen
import com.example.recipeapp.ui.compose.screens.RecipesListScreen

object RecipeMainDestinations {
    const val SCREEN_CATEGORIES = "categories"
    const val SCREEN_FAVORITES = "favorites"
    const val SCREEN_RECIPES_LIST = "recipesList/{categoryId}"
    const val SCREEN_RECIPE = "recipe/{recipeId}"
}

@Composable
fun RecipeAppNavigation(
    appContainer: AppContainer,
) {

    val categoriesListViewModel = appContainer.getCategoriesListViewModelFactory().create()
    val recipesListViewModel = appContainer.getRecipesListViewModelFactory().create()
    val favoritesViewModel = appContainer.getFavoritesViewModelFactory().create()
    val recipeViewModel = appContainer.getRecipeViewModelFactory().create()

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = SCREEN_CATEGORIES,
    ) {

        composable(SCREEN_CATEGORIES) {
            Box {
                CategoriesScreen(
                    onCategoryCardClicked = { categoryId ->
                        navController.navigate("$SCREEN_RECIPES_LIST/${categoryId}")
                    },
                    categoriesListViewModel = categoriesListViewModel
                )
                RecipeNavButton(
                    modifier = Modifier
                        .align(Alignment.BottomCenter),
                    onCategoryClicked = { navController.navigate(SCREEN_CATEGORIES) },
                    onFavoritesClicked = { navController.navigate(SCREEN_FAVORITES) },
                )
            }
        }

        composable("$SCREEN_RECIPES_LIST/{$ARG_CATEGORY_ID}") { backStackEntry ->
            Box {
                RecipesListScreen(
                    categoryId = backStackEntry.arguments?.getString(ARG_CATEGORY_ID)?.toIntOrNull()
                        ?: 0,
                    onRecipeClicked = { navController.navigate("$SCREEN_RECIPE/${it}") },
                    recipesListViewModel = recipesListViewModel,
                    categoriesListViewModel = categoriesListViewModel,
                )
                RecipeNavButton(
                    modifier = Modifier
                        .align(Alignment.BottomCenter),
                    onCategoryClicked = { navController.navigate(SCREEN_CATEGORIES) },
                    onFavoritesClicked = { navController.navigate(SCREEN_FAVORITES) },
                )
            }
        }

        composable("$SCREEN_RECIPE/{$ARG_RECIPE_ID}") { recipeBackStack ->
            val recipeId = recipeBackStack.arguments?.getString(ARG_RECIPE_ID)?.toIntOrNull() ?: 0
            Box {
                RecipeScreen(
                    onFavoriteButtonClicked = {
                        recipeViewModel.onFavoritesClicked(recipeId = recipeId)
                    },
                    recipeId = recipeId,
                    recipeViewModel = recipeViewModel
                )
                RecipeNavButton(
                    modifier = Modifier
                        .align(Alignment.BottomCenter),
                    onCategoryClicked = { navController.navigate(SCREEN_CATEGORIES) },
                    onFavoritesClicked = { navController.navigate(SCREEN_FAVORITES) },
                )
            }
        }

        composable(SCREEN_FAVORITES) { favoritesBackStack ->
            Box {
                FavoriteScreen(
                    onRecipeCardClicked = { navController.navigate("$SCREEN_RECIPE/${it}") },
                    favoritesViewModel = favoritesViewModel,
                )
                RecipeNavButton(
                    modifier = Modifier
                        .align(Alignment.BottomCenter),
                    onCategoryClicked = { navController.navigate(SCREEN_CATEGORIES) },
                    onFavoritesClicked = { navController.navigate(SCREEN_FAVORITES) },
                )
            }
        }
    }
}