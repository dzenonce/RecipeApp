package com.example.recipeapp.data.room.recipes

import com.example.recipeapp.data.room.RecipeDatabase
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipesCache(
    private val recipeDatabase: RecipeDatabase? = null
) {
    suspend fun getRecipesByCategoryIdFromCache(categoryId: Int): List<Recipe>? =
        withContext(Dispatchers.IO) {
            recipeDatabase?.recipesDao()?.getRecipesListByCategoryId()
        }

    suspend fun addRecipesByCategoryIdToCache(recipes: List<Recipe>) =
        withContext(Dispatchers.IO) {
            recipeDatabase?.recipesDao()?.addRecipesByCategoryId(recipes)
        }

    suspend fun getCategoriesFromCache(): List<Category>? =
        withContext(Dispatchers.IO) {
            recipeDatabase?.categoriesDao()?.getAllCategory()
        }

    suspend fun loadCategoryToCache(categories: List<Category>) =
        withContext(Dispatchers.IO) {
            recipeDatabase?.categoriesDao()?.addCategory(categories)
        }


}