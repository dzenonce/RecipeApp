package com.example.recipeapp.ui.category

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.data.room.RecipeDatabase
import com.example.recipeapp.model.Category
import com.example.recipeapp.ui.DATABASE_NAME
import kotlinx.coroutines.launch

data class CategoriesUiState(
    var categoriesList: List<Category> = emptyList(),
)

class CategoriesViewModel(
    private val application: Application,
) : AndroidViewModel(
    application = Application()
) {

    private var _uiState = MutableLiveData<CategoriesUiState>()
    val uiState: LiveData<CategoriesUiState> = _uiState

    fun loadCategories() {
        viewModelScope.launch {
            var categories: List<Category> =
                recipeRepository.getCategoriesFromCache() ?: emptyList()
            if (categories.isEmpty()) {
                categories = recipeRepository.loadCategories() ?: emptyList()
            }
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
