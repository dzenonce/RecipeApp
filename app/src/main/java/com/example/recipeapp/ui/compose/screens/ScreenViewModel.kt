package com.example.recipeapp.ui.compose.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

sealed class Screen {
    object CategoriesScreen : Screen()
    object RecipesList : Screen()
    object Favorites : Screen()
}

data class ScreenUiState(
    var currentScreen: Screen? = null
)

class ScreenViewModel : ViewModel() {

    private var _screenState = MutableLiveData<ScreenUiState>()
    val screenState: LiveData<ScreenUiState> = _screenState

    fun navigateTo(screen: Screen) {
        _screenState.value = ScreenUiState(currentScreen = screen)
    }
}
