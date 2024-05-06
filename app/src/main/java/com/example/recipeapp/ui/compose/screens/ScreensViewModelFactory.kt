package com.example.recipeapp.ui.compose.screens

import com.example.recipeapp.di.Factory

class ScreensViewModelFactory : Factory<ScreenViewModel> {
    override fun create(): ScreenViewModel {
        return ScreenViewModel()
    }
}
