package com.example.recipeapp.ui.category

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.data.room.Database
import com.example.recipeapp.model.Category
import com.example.recipeapp.ui.DATABASE_NAME
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

data class CategoriesUiState(
    var categoriesList: List<Category> = emptyList(),
)

class CategoriesViewModel(
    private val application: Application,
) : AndroidViewModel(
    application = Application()
) {

    private var _uiState = MutableLiveData<CategoriesUiState>()
    val uiState: LiveData<CategoriesUiState> = _uiState

    private val recipeRepository = RecipeRepository()

    fun loadCategories() {
//        thread {
//            val s = loadCategoryFromRoomDatabase()
//            Log.d("!!!", "Room s $s")
//        }

        viewModelScope.launch {
            _uiState.value =
                CategoriesUiState(
                    categoriesList = recipeRepository.loadCategories() ?: emptyList()
                )
        }
    }

    private fun loadCategoryFromRoomDatabase(): List<Category> {
        val db = Room.databaseBuilder(
            context = application.applicationContext,
            klass = Database::class.java,
            name = DATABASE_NAME,
        ).build()

        val categoryDao = db.categoryDao()
        val listCategory = categoryDao.getAllCategory()
        return listCategory
    }

}