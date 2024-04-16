package com.example.recipeapp.ui.recipes.recipesList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.data.room.RecipeAppDatabase
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.ui.DATABASE_NAME
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

    private var _uiState = MutableLiveData<RecipesListUiState>()
    val uiState: LiveData<RecipesListUiState> = _uiState

    fun loadRecipesList(categoryId: Int) {
        viewModelScope.launch {
            val recipesList = recipeRepository.loadRecipesListByCategoryId(21)
            _uiState.value =
                RecipesListUiState(
                    recipeList = recipeRepository.loadRecipesListByCategoryId(categoryId)
                        ?: emptyList(),
                )
        }
    }

    private val recipeDatabase = Room.databaseBuilder(
        context = application.applicationContext,
        RecipeAppDatabase::class.java,
        DATABASE_NAME,
    ).build()
    private val recipeRepository = RecipeRepository(
        recipeDatabase = recipeDatabase
    )

}