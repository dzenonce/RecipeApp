package com.example.recipeapp.data

import android.util.Log
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.ui.API_RECIPE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class RecipeRepository{

    private val threadPool: ExecutorService = Executors.newFixedThreadPool(10)

    private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val okHttpCli = OkHttpClient.Builder()
        .addNetworkInterceptor(interceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("$API_RECIPE_URL/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpCli)
        .build()
    private val service: RecipeApiService = retrofit.create(RecipeApiService::class.java)

    fun loadCategories(): List<Category> =
        try {
            threadPool.submit(
                Callable {
                    service.getCategories().execute().body()
                }
            ).get() ?: emptyList()
        } catch (e: Error) {
            Log.e("threadPool error", e.stackTraceToString())
            emptyList()
        }

    fun loadRecipesListByCategoryId(categoryId: Int): List<Recipe> =
        try {
            threadPool.submit(
                Callable {
                    service.getRecipesListByCategoryId(categoryId).execute().body() ?: emptyList()
                }
            ).get() ?: emptyList()
        } catch (e: Error) {
            Log.e("threadPool error", e.stackTraceToString())
            emptyList()
        }

    fun loadRecipesByIds(ids: String): List<Recipe> =
        try {
            threadPool.submit(
                Callable {
                    service.getRecipesListByIds(ids).execute().body() ?: emptyList()
                }
            ).get() ?: emptyList()
        } catch (e: Error) {
            Log.e("threadPool error", e.stackTraceToString())
            emptyList()
        }

    fun loadRecipeByRecipeId(recipeId: Int): Recipe? =
        try {
            threadPool.submit(
                Callable {
                    service.getRecipeByRecipeId(recipeId).execute().body()
                }
            ).get()
        } catch (e: Error) {
            Log.e("threadPool error", e.stackTraceToString())
            null
        }

    fun loadCategoryByCategoryId(categoryId: Int): Category? =
        try {
            threadPool.submit(
                Callable {
                    service.getCategoryByCategoryId(categoryId).execute().body()
                }
            ).get()
        } catch (e: Error) {
            Log.e("threadPool error", e.stackTraceToString())
            null
        }

}