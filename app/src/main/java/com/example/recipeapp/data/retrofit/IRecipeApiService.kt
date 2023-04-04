package com.example.recipeapp.data.retrofit

import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IRecipeApiService {

    @GET("category")
    fun getCategories(): Call<List<Category>>

    @GET("category/{id}/recipes")
    fun getRecipesListByCategoryId(@Path("id") categoryId: Int): Call<List<Recipe>>

    @GET("recipe/{id}")
    fun getRecipeByRecipeId(@Path("id") recipeId: Int): Call<Recipe>

    @GET("recipes")
    fun getRecipesListByIds(
        @Query(
            value = "ids",
            encoded = true
        ) categoryIds: String
    ): Call<List<Recipe>>

    @GET("category/{id}")
    fun getCategoryByCategoryId(@Path("id") categoryId: Int): Call<Category>

}