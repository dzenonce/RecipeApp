package com.example.recipeapp.data

import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.ui.API_RECIPE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class RecipeRepository {

    private val threadPool: ExecutorService = Executors.newFixedThreadPool(10)

    private val retrofit = Retrofit.Builder()
        .baseUrl("$API_RECIPE_URL/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service: RecipeApiService = retrofit.create(RecipeApiService::class.java)

    fun loadCategories(): List<Category> =
        threadPool.submit(
            Callable {
                service.getCategories().execute().body()
            }
        ).get() ?: emptyList()

    fun loadRecipesListByCategoryId(categoryId: Int): List<Recipe> =
        threadPool.submit(
            Callable {
                service.getRecipesListByCategoryId(categoryId).execute().body() ?: emptyList()
            }
        ).get() ?: emptyList()

    fun loadRecipesByIds(ids: String): List<Recipe> =
        threadPool.submit(
            Callable {
                service.getRecipesListByIds(ids).execute().body() ?: emptyList()
            }
        ).get() ?: emptyList()

    fun loadRecipeByRecipeId(recipeId: Int): Recipe? =
        threadPool.submit(
            Callable {
                service.getRecipeByRecipeId(recipeId).execute().body()
            }
        ).get()

    fun loadCategoryByCategoryId(categoryId: Int): Category? =
        threadPool.submit(
            Callable {
                service.getCategoryByCategoryId(categoryId).execute().body()
            }
        ).get()

}