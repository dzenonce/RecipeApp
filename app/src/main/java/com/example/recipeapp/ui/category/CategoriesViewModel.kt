package com.example.recipeapp.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.model.Category
import kotlinx.coroutines.launch

data class CategoriesUiState(
    var categoriesList: List<Category> = emptyList(),
)

class CategoriesViewModel : ViewModel() {

    private var _uiState = MutableLiveData<CategoriesUiState>()
    val uiState: LiveData<CategoriesUiState> = _uiState

    private val recipeRepository = RecipeRepository()

    fun loadCategories() {
        viewModelScope.launch {
            _uiState.value =
                CategoriesUiState(
                    categoriesList = recipeRepository.loadCategories() ?: emptyList()
                )
        }
    }

}