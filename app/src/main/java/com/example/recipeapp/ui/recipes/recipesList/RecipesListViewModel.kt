package com.example.recipeapp.ui.recipes.recipesList

import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipeapp.data.STUB
import com.example.recipeapp.model.Recipe

data class RecipesListUiState(
    var recipeList: List<Recipe> = listOf(),
    var categoryName: String? = null,
    var categoryImageUrl: Drawable? = null,
)


class RecipesListViewModel : ViewModel() {

    private var _uiState = MutableLiveData<RecipesListUiState>()
    val uiState: LiveData<RecipesListUiState> = _uiState

    // TODO load from Network
    fun loadRecipesList(categoryId: Int) {
        _uiState.value =
            RecipesListUiState(
                recipeList = STUB.getRecipesByCategoryId(categoryId),
            )
    }

}