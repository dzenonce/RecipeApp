package com.example.recipeapp.ui.category

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
import com.example.recipeapp.model.Category
import com.example.recipeapp.ui.DATABASE_NAME
import com.example.recipeapp.ui.TOAST_TEXT_ERROR_LOADING
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

data class CategoriesUiState(
    var categoriesList: List<Category> = emptyList(),
)

class CategoriesViewModel(
    private val application: Application,
) : AndroidViewModel(
    application = application
) {

    private var _uiState = MutableLiveData<CategoriesUiState>()
    val uiState: LiveData<CategoriesUiState> = _uiState

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.e("internet error", throwable.stackTraceToString())
        Toast.makeText(
            application.applicationContext,
            TOAST_TEXT_ERROR_LOADING,
            Toast.LENGTH_SHORT
        ).show()
    }

    fun loadCategories() {
        viewModelScope.launch(exceptionHandler) {
            var categories = recipeRepository.getCategoriesFromCache() ?: emptyList()
            if (categories.isEmpty()) recipeRepository.loadCategories()?.let { categories = it }
            _uiState.value =
                CategoriesUiState(
                    categoriesList = categories
                )
            recipeRepository.loadCategoryToCache(
                categories = categories
            )
        }
    }

    private val recipeDatabase = Room.databaseBuilder(
        context = application.applicationContext,
        RecipeDatabase::class.java,
        DATABASE_NAME
    ).build()
    private val recipeRepository = RecipeRepository(
        recipeDatabase = recipeDatabase
    )

}
