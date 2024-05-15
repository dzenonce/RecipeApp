package com.example.recipeapp.ui.compose.navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

sealed class Screen {
    data object CategoriesScreen: Screen()
    data object FavoritesScreen: Screen()
    class RecipesListScreen(val categoryId: Int): Screen()
    class RecipeScreen(val recipeId: Int): Screen()
}

data class ScreenState(
    var currentScreen: Screen = Screen.CategoriesScreen,
)

class NavigationViewModel: ViewModel() {

    private var _screenState = MutableLiveData<ScreenState>()
    val currentScreen: LiveData<ScreenState> = _screenState

    fun navigateTo(screen: Screen) {
        _screenState.value = ScreenState(currentScreen = screen)
    }

}
