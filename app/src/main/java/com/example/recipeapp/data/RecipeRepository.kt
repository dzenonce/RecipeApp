package com.example.recipeapp.data

import android.content.Context
import androidx.room.Room
import com.example.recipeapp.data.room.RecipeDatabase
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.ui.API_RECIPE_URL
import com.example.recipeapp.ui.DATABASE_NAME
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RecipeRepository(
    val context: Context? = null
) {

    private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val okHttpCli = OkHttpClient.Builder()
        .addNetworkInterceptor(interceptor)
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl("$API_RECIPE_URL/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpCli)
        .build()
    private val recipeDataSource: RecipeDataSource = RecipeDataSource(retrofit.create())

    private val recipeDatabase = Room.databaseBuilder(
        context = context!!,
        RecipeDatabase::class.java,
        DATABASE_NAME
    ).build()
    private val categoriesDao = recipeDatabase.categoriesDao()

    suspend fun getCategoriesFromCache(): List<Category> =
        withContext(Dispatchers.IO) {
            categoriesDao.getAllCategory()
        }

    suspend fun loadCategoryToCache(categories: List<Category>) =
        withContext(Dispatchers.IO) {
            categoriesDao.addCategory(categories)
        }

    suspend fun loadCategories(): List<Category>? =
        withContext(Dispatchers.IO) {
            recipeDataSource.getCategories()
        }

    suspend fun loadRecipesListByCategoryId(categoryId: Int): List<Recipe>? =
        withContext(Dispatchers.IO) {
            recipeDataSource.getRecipesListByCategoryId(categoryId)
        }

    suspend fun loadRecipesByIds(ids: String): List<Recipe>? =
        withContext(Dispatchers.IO) {
            recipeDataSource.getRecipesByIds(ids)
        }

    suspend fun loadRecipeByRecipeId(recipeId: Int): Recipe? =
        withContext(Dispatchers.IO) {
            recipeDataSource.getRecipeByRecipeId(recipeId)
        }

    suspend fun loadCategoryByCategoryId(categoryId: Int): Category? =
        withContext(Dispatchers.IO) {
            recipeDataSource.getCategoryByCategoryId(categoryId)
        }

}