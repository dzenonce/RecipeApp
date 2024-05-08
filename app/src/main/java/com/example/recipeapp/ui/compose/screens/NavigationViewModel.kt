package com.example.recipeapp.ui.compose.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

sealed class Screen {
    object CategoriesScreen : Screen()
    object RecipesList : Screen()
    object Favorites : Screen()
}

data class ScreenState(
    var currentScreen: Screen = Screen.CategoriesScreen,
)

class NavigationViewModel : ViewModel() {

    private var _screenState = MutableLiveData<ScreenState>()
    val currentScreen: LiveData<ScreenState> = _screenState

    fun navigateTo(screen: Screen) {
        _screenState.value = ScreenState(currentScreen = screen)
    }
}
