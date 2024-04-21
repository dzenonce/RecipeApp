package com.example.recipeapp.ui.recipes.recipesList

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
import com.example.recipeapp.model.RecipesList
import com.example.recipeapp.ui.DATABASE_NAME
import com.example.recipeapp.ui.TOAST_TEXT_ERROR_LOADING
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

data class RecipesListUiState(
    var recipeList: List<Recipe> = emptyList(),
    var categoryName: String? = null,
    var categoryImageUrl: String? = null,
)

class RecipesListViewModel(
    private val application: Application
) : AndroidViewModel(
    application = application
) {

    private var _uiState = MutableLiveData<RecipesListUiState>()
    val uiState: LiveData<RecipesListUiState> = _uiState

    private val recipeDatabase = Room.databaseBuilder(
        context = application.applicationContext,
        RecipeDatabase::class.java,
        DATABASE_NAME
    ).build()
    private val recipeRepository = RecipeRepository(recipeDatabase)

    fun loadRecipesList(categoryId: Int) {
        viewModelScope.launch(exceptionHandler) {
            var recipesList =
                recipeRepository.recipesCache.getRecipesByCategoryIdFromCache(categoryId)?.recipesList
                    ?: emptyList()
            if (recipesList.isEmpty())
                recipeRepository.loadRecipesListByCategoryId(categoryId)?.let { recipesList = it }

            _uiState.value = RecipesListUiState(recipeList = recipesList)
            recipeRepository.recipesCache.addRecipesByCategoryIdToCache(
                RecipesList(
                    categoryId = categoryId,
                    recipesList = recipesList,
                )
            )
        }
    }

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.e("internet error", throwable.stackTraceToString())
        Toast.makeText(
            application.applicationContext,
            TOAST_TEXT_ERROR_LOADING,
            Toast.LENGTH_SHORT,
        ).show()
    }

}