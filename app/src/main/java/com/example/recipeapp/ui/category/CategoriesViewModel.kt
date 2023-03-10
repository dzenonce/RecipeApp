package com.example.recipeapp.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipeapp.data.STUB
import com.example.recipeapp.model.Category

data class CategoriesUiState(
    var categoriesList: List<Category> = listOf(),
)

class CategoriesViewModel : ViewModel() {

    private var _uiState = MutableLiveData<CategoriesUiState>()
    val uiState: LiveData<CategoriesUiState> = _uiState

    // TODO load from Network
    fun loadCategories() {
        _uiState.value =
            CategoriesUiState(
                categoriesList = STUB.getCategories(),
            )
    }

}

