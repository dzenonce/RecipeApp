package com.example.recipeapp.ui.category

import android.app.Application
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.data.STUB
import com.example.recipeapp.interfaces.IScreenImage
import com.example.recipeapp.model.Category
import java.io.InputStream

data class CategoriesUiState(
    var screenHeaderImage: Drawable? = null,
    var categoriesList: List<Category> = listOf(),
)

class CategoriesViewModel(
    private val application: Application,
) : AndroidViewModel(
    application = Application(),
), IScreenImage {

    private var _uiState = MutableLiveData<CategoriesUiState>()
    val uiState: LiveData<CategoriesUiState> = _uiState

    // TODO load from Network
    fun loadCategories(category: String) {
        // TODO можно возвращать список drawable для адаптера
        _uiState.value =
            CategoriesUiState(
                screenHeaderImage =
                    loadScreenHeaderImage(
                        STUB.getScreenHeaderImageUrl(category)
                    ),
                categoriesList = STUB.getCategories(),
            )
    }

    override fun loadScreenHeaderImage(imageUrl: String) =
        try {
            val inputStream: InputStream? = application.assets?.open(imageUrl)
            Drawable.createFromStream(inputStream, null)
        } catch (e: Error) {
            Log.e("assets error", e.stackTraceToString())
            null
        }

    // TODO мы же грузим все из сети и передавать тоже можем загруженное
    fun loadRecipesImage() {

    }

}

