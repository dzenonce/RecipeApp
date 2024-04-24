package com.example.recipeapp.ui.recipes.recipe

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.data.room.RecipeDatabase
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.ui.API_RECIPE_IMAGE_URL
import com.example.recipeapp.ui.DATABASE_NAME
import com.example.recipeapp.ui.TOAST_TEXT_ERROR_LOADING
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

data class RecipeUiState(
    var isFavorite: Boolean = false,
    var recipe: Recipe? = null,
    var recipeImageUrl: String = "",
)

class RecipeViewModel(
    private val application: Application,
) : AndroidViewModel(
    application = application
) {

    private val _uiState = MutableLiveData<RecipeUiState>()
    val uiState: LiveData<RecipeUiState> = _uiState

    private val recipeDatabase = Room.databaseBuilder(
        context = application.applicationContext,
        RecipeDatabase::class.java,
        DATABASE_NAME
    ).build()
    private val recipeRepository = RecipeRepository(recipeDatabase)

    fun loadRecipe(recipeId: Int) {
        viewModelScope.launch(exceptionHandler) {
            var recipe = recipeRepository.recipesCache.getRecipeFromCache(recipeId)
            if (recipe == null) recipeRepository.loadRecipeByRecipeId(recipeId).let { recipe = it }
            _uiState.value =
                RecipeUiState(
                    isFavorite = recipe?.isFavorite ?: false,
                    recipe = recipe,
                    recipeImageUrl = "$API_RECIPE_IMAGE_URL/${recipe?.imageUrl}"
                )
        }
    }

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.e("internet error", throwable.stackTraceToString())
        Toast.makeText(
            application.applicationContext,
            TOAST_TEXT_ERROR_LOADING,
            Toast.LENGTH_SHORT
        ).show()
    }

    fun onFavoritesClicked(recipeId: Int) {
        viewModelScope.launch(exceptionHandler) {
            if (uiState.value?.isFavorite == true) {
                _uiState.value?.isFavorite = false
                recipeRepository.recipesCache.deleteRecipeFromFavorite(recipeId)
            } else {
                _uiState.value?.isFavorite = true
                recipeRepository.recipesCache.addRecipeToFavorite(recipeId)
            }
        }
    }
}