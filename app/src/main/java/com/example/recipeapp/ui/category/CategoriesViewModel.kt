package com.example.recipeapp.ui.category

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.model.Category
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

    private val recipeRepository = RecipeRepository(
        context = application.applicationContext
    )

    fun loadCategories() {
        viewModelScope.launch {
            var categories: List<Category> = recipeRepository.getCategoriesFromCache()
            if (categories.isEmpty()) {
                categories = recipeRepository.loadCategories() ?: emptyList()
                recipeRepository.loadCategoryToCache(categories)
            }
            _uiState.value =
                CategoriesUiState(
                    categoriesList = categories
                )
        }
    }

//    private fun loadCategoryFromRoomDatabase(): List<Category> = categoryDao.getAllCategory()
//    private fun addCategoryToDatabase(categories: List<Category>) = categoryDao.addCategory(categories)

}