package com.example.recipeapp.ui.recipes.favorites

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.ui.PREFERENCE_FILE_KEY
import com.example.recipeapp.ui.PREFERENCE_RECIPE_IDS_SET_KEY
import com.example.recipeapp.ui.TOAST_TEXT_ERROR_LOADING
import kotlinx.coroutines.launch

data class FavoritesUiState(
    var favoritesRecipesList: List<Recipe> = listOf(),
)

class FavoritesViewModel(
    private val application: Application,
) : AndroidViewModel(
    application = application
) {

    private val recipeRepository = RecipeRepository()

    private var _uiState = MutableLiveData<FavoritesUiState>()
    val uiState: LiveData<FavoritesUiState> = _uiState

    fun loadFavorites() {
        val recipesIds = getFavoritesIds().map { it.toInt() }.toSet()
        viewModelScope.launch {
            val favoriteRecipesList =
                try {
                    recipeRepository.loadRecipesByIds(recipesIds.joinToString(","))
                } catch (e: Exception) {
                    Log.e("!!!", e.stackTraceToString())
                    null
                }
            if (favoriteRecipesList != null) {
                _uiState.value =
                    FavoritesUiState(
                        favoritesRecipesList = favoriteRecipesList
                    )
            } else
                Toast.makeText(
                    application.applicationContext,
                    TOAST_TEXT_ERROR_LOADING,
                    Toast.LENGTH_SHORT
                ).show()
        }
    }

    private fun getFavoritesIds(): HashSet<String> {
        val sharedPrefs = application.getSharedPreferences(
            PREFERENCE_FILE_KEY,
            Context.MODE_PRIVATE,
        )
        val favoritesSet = sharedPrefs?.getStringSet(
            PREFERENCE_RECIPE_IDS_SET_KEY,
            null,
        ) ?: setOf()

        return HashSet(favoritesSet)
    }

}