package com.example.recipeapp.ui.xmlUi.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.model.Category
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

data class CategoriesUiState(
    var categoriesList: List<Category> = emptyList(),
)

data class CategoryInfo(
    var category: Category? = null,
)

class CategoriesListViewModel(
    private val recipeRepository: RecipeRepository,
    private val exceptionHandler: CoroutineExceptionHandler
) : ViewModel() {

    private var _categoriesListUiState = MutableLiveData<CategoriesUiState>()
    val categoriesListUiState: LiveData<CategoriesUiState> = _categoriesListUiState

    private var _categoryInfo = MutableLiveData<CategoryInfo>()
    val categoryInfo: LiveData<CategoryInfo> = _categoryInfo

    fun loadCategories() {
        viewModelScope.launch(exceptionHandler) {
            var categories = recipeRepository.recipesCache.getCategoriesFromCache()
            if (categories.isEmpty()) recipeRepository.loadCategories()?.let { categories = it }
            _categoriesListUiState.value =
                CategoriesUiState(
                    categoriesList = categories
                )
            recipeRepository.recipesCache.addCategoryToCache(
                categories = categories
            )
        }
    }

    fun loadCategoriesByCategoryId(categoryId: Int) {
        viewModelScope.launch(exceptionHandler) {
            // TODO выбор инфы по id из room

            val categoryInfo = recipeRepository.loadCategoryByCategoryId(categoryId)
            if (categoryInfo != null) CategoryInfo(category = categoryInfo)

            _categoryInfo.value =
                CategoryInfo(
                    category = categoryInfo
                )
        }
    }

}
