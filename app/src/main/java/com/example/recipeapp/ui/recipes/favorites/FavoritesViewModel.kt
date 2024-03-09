package com.example.recipeapp.ui.recipes.favorites

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.data.STUB
import com.example.recipeapp.interfaces.IScreenImage
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.ui.PREFERENCE_FILE_KEY
import com.example.recipeapp.ui.PREFERENCE_RECIPE_IDS_SET_KEY
import java.io.InputStream

data class FavoritesUiState(
    var favoritesScreenImage: Drawable? = null,
    var favoritesRecipesList: List<Recipe> = listOf(),
)

class FavoritesViewModel(
    private val application: Application,
) : AndroidViewModel(
    application = Application()
), IScreenImage {

    private var _uiState = MutableLiveData<FavoritesUiState>()
    val uiState: LiveData<FavoritesUiState> = _uiState

    fun loadFavorites(screenName: String) {
        val recipesIds = getFavoritesIds().map { it.toInt() }.toSet()
        _uiState.value =
            FavoritesUiState(
                favoritesScreenImage =
                    loadScreenHeaderImage(STUB.getScreenHeaderImageUrl(screenName)),
                favoritesRecipesList = STUB.getRecipesByIds(recipesIds),
            )
    }

    private fun getFavoritesIds(): HashSet<String> {
        val sharedPrefs = application.getSharedPreferences(
            PREFERENCE_FILE_KEY,
            Context.MODE_PRIVATE,
        )
        val favoritesSet = sharedPrefs?.getStringSet(
            PREFERENCE_RECIPE_IDS_SET_KEY,
            null,
        ) ?: mutableSetOf()

        return HashSet(favoritesSet)
    }

    override fun loadScreenHeaderImage(imageUrl: String): Drawable? =
        try {
            val inputStream: InputStream = application.assets.open(imageUrl)
            Drawable.createFromStream(inputStream, null)
        } catch (e: Error) {
            Log.e("load image error", e.stackTraceToString())
            null
        }


}