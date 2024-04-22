package com.example.recipeapp.ui.recipes.favorites

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
import com.example.recipeapp.ui.DATABASE_NAME
import com.example.recipeapp.ui.TOAST_TEXT_ERROR_LOADING
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

data class FavoritesUiState(
    var favoritesRecipesList: List<Recipe> = emptyList(),
)

class FavoritesViewModel(
    private val application: Application,
) : AndroidViewModel(
    application = application
) {

    private val recipeDatabase = Room.databaseBuilder(
        context = application.applicationContext,
        RecipeDatabase::class.java,
        DATABASE_NAME
    ).build()
    private val recipeRepository = RecipeRepository(recipeDatabase)

    private var _uiState = MutableLiveData<FavoritesUiState>()
    val uiState: LiveData<FavoritesUiState> = _uiState

    fun loadFavorites() {
        viewModelScope.launch(exceptionHandler) {
            _uiState.value =
                FavoritesUiState(
                    recipeRepository.recipesCache.getFavoritesFromCache()
                        ?: emptyList()
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

}