package com.example.recipeapp.ui.recipes.recipe

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.data.STUB
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.ui.PREFERENCE_FILE_KEY
import com.example.recipeapp.ui.PREFERENCE_RECIPE_IDS_SET_KEY

data class RecipeUiState(
    var isFavorite: Boolean = false,
    var recipe: Recipe? = null,
    var portionsCount: Int? = 1,
)

class RecipeViewModel(
    private val application: Application,
) : AndroidViewModel(
    application = Application()
) {

    private val _uiState = MutableLiveData<RecipeUiState>()
    val uiState: LiveData<RecipeUiState> = _uiState

    // TODO load from network
    fun loadRecipe(recipeId: Int) {
        _uiState.value =
            RecipeUiState(
                isFavorite = getFavorites().contains(recipeId.toString()),
                recipe = STUB.getRecipeByRecipeId(recipeId),
                portionsCount = RecipeUiState().portionsCount ?: 1,
            )
    }

    fun onFavoritesClicked() {
        if (uiState.value?.isFavorite == true) {
            this._uiState.value?.isFavorite = false
            saveFavorites(
                getFavorites().minus(uiState.value?.recipe?.id.toString())
            )
        } else {
            _uiState.value?.isFavorite = true
            saveFavorites(
                getFavorites().plus(uiState.value?.recipe?.id.toString())
            )
        }
    }

    private fun getFavorites(): HashSet<String> {
        val sharedPreference = application.getSharedPreferences(
            PREFERENCE_FILE_KEY,
            Context.MODE_PRIVATE,
        )
        val favoriteSet = sharedPreference?.getStringSet(
            PREFERENCE_RECIPE_IDS_SET_KEY,
            null,
        ) ?: mutableSetOf()
        return HashSet(favoriteSet)
    }

    private fun saveFavorites(collectionRecipeIds: Set<String>?) {
        val sharedPrefs = application.getSharedPreferences(
            PREFERENCE_FILE_KEY,
            Context.MODE_PRIVATE,
        ) ?: return
        with(sharedPrefs.edit()) {
            clear()
            putStringSet(
                PREFERENCE_RECIPE_IDS_SET_KEY,
                collectionRecipeIds,
            )
            apply()
        }
    }

}