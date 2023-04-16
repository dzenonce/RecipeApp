package com.example.recipeapp.ui.recipes.recipesList

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.ui.TOAST_TEXT_ERROR_LOADING
import kotlinx.coroutines.launch

data class RecipesListUiState(
    var recipeList: List<Recipe> = emptyList(),
    var categoryName: String? = null,
    var categoryImageUrl: String? = null,
)

class RecipesListViewModel(
    private val application: Application
) : AndroidViewModel(
    application = application
) {

    private val recipeRepository = RecipeRepository()

    private var _uiState = MutableLiveData<RecipesListUiState>()
    val uiState: LiveData<RecipesListUiState> = _uiState

    @SuppressLint("ShowToast")
    fun loadRecipesList(categoryId: Int) {
        viewModelScope.launch {
            val recipesList =
                try {
                    recipeRepository.loadRecipesListByCategoryId(categoryId)
                } catch (e: Exception) {
                    Log.e("internet error", e.stackTraceToString())
                    null
                }

            if (recipesList != null)
                _uiState.value =
                    RecipesListUiState(
                        recipeList = recipeRepository.loadRecipesListByCategoryId(categoryId)
                            ?: emptyList()
                    )
            else Toast.makeText(
                application.applicationContext,
                TOAST_TEXT_ERROR_LOADING,
                Toast.LENGTH_SHORT,
            ).show()
        }
    }

}