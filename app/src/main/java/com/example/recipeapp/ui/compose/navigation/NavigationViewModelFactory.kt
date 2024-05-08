package com.example.recipeapp.ui.compose.navigation

import com.example.recipeapp.di.Factory

class NavigationViewModelFactory : Factory<NavigationViewModel> {
    override fun create(): NavigationViewModel {
        return NavigationViewModel()
    }
}
