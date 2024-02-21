package com.example.recipeapp.ui.recipes.recipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.recipeapp.model.Recipe

data class RecipeUiState(
    var isFavorite: Boolean = false,
    var recipe: Recipe? = null,
    var portionsCount: Int = 1,
)

class RecipeViewModel: ViewModel() {

    private val _uiState = MutableLiveData<RecipeUiState>()
    val uiState: LiveData<RecipeUiState> = _uiState

    fun observe() {
        _uiState.value = RecipeUiState(isFavorite = true)
        Log.i("!!!", "From viewModel: ${uiState.value?.isFavorite}")
    }

}