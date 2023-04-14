package com.example.recipeapp.ui.recipes.recipesList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.model.Recipe
import kotlinx.coroutines.launch

data class RecipesListUiState(
    var recipeList: List<Recipe> = emptyList(),
    var categoryName: String? = null,
    var categoryImageUrl: String? = null,
)

class RecipesListViewModel(application: Application) : AndroidViewModel(
    application = Application()
) {

    private val recipeRepository = RecipeRepository(
        context = application.applicationContext
    )

    private var _uiState = MutableLiveData<RecipesListUiState>()
    val uiState: LiveData<RecipesListUiState> = _uiState

    fun loadRecipesList(categoryId: Int) {
        viewModelScope.launch {
            _uiState.value =
                RecipesListUiState(
                    recipeList = recipeRepository.loadRecipesListByCategoryId(categoryId)
                        ?: emptyList(),
                )

        }
    }

}