package com.example.recipeapp.ui.xmlUi.recipes.recipesList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.model.Recipe
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

data class RecipesListUiState(
    var recipeList: List<Recipe> = emptyList(),
    var categoryName: String? = null,
    var categoryImageUrl: String? = null,
)

class RecipesListViewModel(
    private val recipeRepository: RecipeRepository,
    private val exceptionHandler: CoroutineExceptionHandler,
) : ViewModel() {

    private var _uiState = MutableLiveData<RecipesListUiState>()
    val uiState: LiveData<RecipesListUiState> = _uiState

    fun loadRecipesList(categoryId: Int) {
        viewModelScope.launch(exceptionHandler) {
            var recipesList =
                recipeRepository.recipesCache.getRecipesByCategoryIdFromCache(categoryId)
            if (recipesList.isNotEmpty()) _uiState.value =
                RecipesListUiState(recipeList = recipesList)

            if (recipesList.isEmpty()) {
                recipeRepository.loadRecipesListByCategoryId(categoryId)?.let { recipesList = it }
                recipeRepository.recipesCache.addRecipesToCache(
                    recipesList.map {
                        it.categoryId = categoryId
                        it
                    }
                )
            }
            _uiState.value = RecipesListUiState(recipeList = recipesList)
        }
    }

}