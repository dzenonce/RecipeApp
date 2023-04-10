package com.example.recipeapp.data

import com.example.recipeapp.data.retrofit.IRecipeApiService
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe

class RecipeDataSource(
    private val recipeApiService: IRecipeApiService,
) {

    fun getCategories(): List<Category>? =
        recipeApiService.getCategories().execute().body()

    fun getRecipesListByCategoryId(categoryId: Int): List<Recipe>? =
        recipeApiService.getRecipesListByCategoryId(categoryId).execute().body()

    fun getRecipesByIds(ids: String): List<Recipe>? =
        recipeApiService.getRecipesListByIds(ids).execute().body()

    fun getRecipeByRecipeId(recipeId: Int): Recipe? =
        recipeApiService.getRecipeByRecipeId(recipeId).execute().body()

    fun getCategoryByCategoryId(categoryId: Int): Category? =
        recipeApiService.getCategoryByCategoryId(categoryId).execute().body()

}