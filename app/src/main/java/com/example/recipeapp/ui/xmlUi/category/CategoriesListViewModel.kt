package com.example.recipeapp.ui.xmlUi.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.model.Category
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CategoriesUiState(
    var categoriesList: List<Category> = emptyList(),
)

@HiltViewModel
class CategoriesListViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val exceptionHandler: CoroutineExceptionHandler
) : ViewModel() {

    private var _uiState = MutableLiveData<CategoriesUiState>()
    val uiState: LiveData<CategoriesUiState> = _uiState

    fun loadCategories() {
        viewModelScope.launch(exceptionHandler) {
            var categories = recipeRepository.recipesCache.getCategoriesFromCache()
            if (categories.isEmpty()) recipeRepository.loadCategories()?.let { categories = it }
            _uiState.value =
                CategoriesUiState(
                    categoriesList = categories
                )
            recipeRepository.recipesCache.addCategoryToCache(
                categories = categories
            )
        }
    }

}
