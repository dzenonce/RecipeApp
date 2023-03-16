package com.example.recipeapp.ui.recipes.recipesList

import android.app.Application
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.data.STUB
import com.example.recipeapp.model.Recipe
import java.io.InputStream

data class RecipesListUiState(
    var recipeList: List<Recipe> = listOf(),
    var categoryName: String? = null,
    var categoryImageUrl: Drawable? = null,
)


class RecipesListViewModel(
    private val application: Application,
) : AndroidViewModel(
    application = Application()
) {

    private var _uiState = MutableLiveData<RecipesListUiState>()
    val uiState: LiveData<RecipesListUiState> = _uiState

    // TODO load from Network
    fun loadRecipesList(categoryId: Int) {
        val categoryInfo = STUB.getCategoryInfoByCategoryId(categoryId)
        _uiState.value =
            RecipesListUiState(
                recipeList = STUB.getRecipesByCategoryId(categoryId),
                categoryName = categoryInfo?.title,
                categoryImageUrl = loadCategoryImage(categoryInfo?.imageUrl)
            )
    }

    private fun loadCategoryImage(categoryImageUrl: String?) =
        try {
            val inputStream: InputStream? = application.assets?.open(categoryImageUrl.toString())
            Drawable.createFromStream(inputStream, null)
        } catch (e: Error) {
            Log.e("assets error", e.stackTraceToString())
            null
        }

}