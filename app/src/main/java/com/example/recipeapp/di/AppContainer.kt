package com.example.recipeapp.di

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.room.Room
import com.example.recipeapp.API_RECIPE_URL
import com.example.recipeapp.DATABASE_NAME
import com.example.recipeapp.TOAST_TEXT_ERROR_LOADING
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.data.api.RecipeApiService
import com.example.recipeapp.data.retrofit.IRecipeApiService
import com.example.recipeapp.data.room.RecipeDatabase
import com.example.recipeapp.di.categories.CategoriesListViewModelFactory
import com.example.recipeapp.di.recipes.favorites.FavoritesViewModelFactory
import com.example.recipeapp.di.recipes.recipe.RecipeViewModelFactory
import com.example.recipeapp.di.recipes.recipesList.RecipesListViewModelFactory
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer(context: Context) {

    private val recipeDatabase = Room.databaseBuilder(
        context,
        RecipeDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val ioDispatcher = Dispatchers.IO
    private val categoriesDao = recipeDatabase.categoriesDao()
    private val recipesDao = recipeDatabase.recipesDao()
    private val favoritesDao = recipeDatabase.favoritesDao()

    private val interceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(interceptor)
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl("$API_RECIPE_URL/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    private val recipeApiService = retrofit.create(IRecipeApiService::class.java)
    private val recipeDataSource = RecipeApiService(recipeApiService)

    private val recipeRepository =
        RecipeRepository(
            categoriesDao = categoriesDao,
            recipesDao = recipesDao,
            favoritesDao = favoritesDao,
            ioDispatcher = ioDispatcher,
            recipeDataSource = recipeDataSource,
        )

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.e("internet error", throwable.stackTraceToString())
        Toast.makeText(
            context,
            TOAST_TEXT_ERROR_LOADING,
            Toast.LENGTH_SHORT
        ).show()
    }

    fun getCategoriesListViewModelFactory() =
        CategoriesListViewModelFactory(
            recipeRepository = recipeRepository,
            exceptionHandler = exceptionHandler,
        )

    fun getFavoritesViewModelFactory() =
        FavoritesViewModelFactory(
            recipeRepository = recipeRepository,
            exceptionHandler = exceptionHandler,
        )

    fun getRecipesListViewModelFactory() =
        RecipesListViewModelFactory(
            recipeRepository = recipeRepository,
            exceptionHandler = exceptionHandler,
        )

    fun getRecipeViewModelFactory() =
        RecipeViewModelFactory(
            recipeRepository = recipeRepository,
            exceptionHandler = exceptionHandler,
        )

}
