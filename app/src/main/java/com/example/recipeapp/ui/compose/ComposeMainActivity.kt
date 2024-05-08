package com.example.recipeapp.ui.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.recipeapp.RecipeApplication
import com.example.recipeapp.ui.compose.navigation.RecipeAppNavigation

class ComposeMainActivity: ComponentActivity() {
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