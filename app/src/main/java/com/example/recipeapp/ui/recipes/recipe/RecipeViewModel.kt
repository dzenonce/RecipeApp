package com.example.recipeapp.ui.recipes.recipe

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.ui.API_RECIPE_IMAGE_URL
import com.example.recipeapp.ui.PREFERENCE_FILE_KEY
import com.example.recipeapp.ui.PREFERENCE_RECIPE_IDS_SET_KEY
import com.example.recipeapp.ui.TOAST_TEXT_ERROR_LOADING
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

data class RecipeUiState(
    var isFavorite: Boolean = false,
    var recipe: Recipe? = null,
    var portionsCount: Int = 1,
    var recipeImageUrl: String = "",
)

class RecipeViewModel(
    private val application: Application,
) : AndroidViewModel(
    application = application
) {

    private val _uiState = MutableLiveData<RecipeUiState>()
    val uiState: LiveData<RecipeUiState> = _uiState

    private val recipeRepository = RecipeRepository()

    private val exceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { coroutineContext, throwable ->
            Toast.makeText(
                application.applicationContext,
                TOAST_TEXT_ERROR_LOADING,
                Toast.LENGTH_SHORT
            ).show()
            // TODO error image
        }

    fun loadRecipe(recipeId: Int) {
        viewModelScope.launch(exceptionHandler) {
            recipeRepository.loadRecipeByRecipeId(recipeId).let { recipe ->
                if (recipe != null)
                    _uiState.value =
                        RecipeUiState(
                            isFavorite = getFavorites().contains(recipeId.toString()),
                            recipe = recipe,
                            portionsCount = RecipeUiState().portionsCount,
                            recipeImageUrl = "$API_RECIPE_IMAGE_URL/${recipe.imageUrl}"
                        )
            }

        }
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

    fun updatePortionsCount(progress: Int) {
        _uiState.value?.portionsCount = progress
    }

    private fun getFavorites(): HashSet<String> {
        val sharedPreference = application.getSharedPreferences(
            PREFERENCE_FILE_KEY,
            Context.MODE_PRIVATE,
        )
        val favoriteSet = sharedPreference?.getStringSet(
            PREFERENCE_RECIPE_IDS_SET_KEY,
            null,
        ) ?: emptySet()
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