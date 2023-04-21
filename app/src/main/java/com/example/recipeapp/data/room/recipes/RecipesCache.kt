package com.example.recipeapp.data.room.recipes

import com.example.recipeapp.data.room.RecipeDatabase
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.RecipesList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipesCache(
    private val recipeDatabase: RecipeDatabase? = null
) {

    suspend fun getRecipesByCategoryIdFromCache(categoryId: Int): RecipesList? =
        withContext(Dispatchers.IO) {
            recipeDatabase?.recipesDao()?.getRecipesList(categoryId)
        }

    suspend fun addRecipesByCategoryIdToCache(recipesList: RecipesList) =
        withContext(Dispatchers.IO) {
            recipeDatabase?.recipesDao()?.addRecipesByCategoryId(recipesList)
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