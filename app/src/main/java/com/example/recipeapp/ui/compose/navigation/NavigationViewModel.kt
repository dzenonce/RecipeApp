package com.example.recipeapp.ui.compose.navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

sealed class Screen {
    data object CategoriesScreen: Screen()
    class RecipesList(
        val categoryId: Int,
        val categoryName: String,
        val categoryImageUrl: String,
    ): Screen()
    data object Favorites: Screen()
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
