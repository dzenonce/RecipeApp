package com.example.recipeapp.data.room

import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipesCache(
    private val recipeDatabase: RecipeDatabase? = null
) {

    suspend fun getFavoritesFromCache(): List<Recipe>? =
        withContext(Dispatchers.IO) {
            recipeDatabase?.favoritesDao()?.getFavoritesRecipes()
        }

    suspend fun addRecipeToFavorite(recipeId: Int) =
        withContext(Dispatchers.IO) {
            recipeDatabase?.favoritesDao()?.addFavoriteRecipe(recipeId)
        }

    suspend fun deleteRecipeFromFavorite(recipeId: Int) =
        withContext(Dispatchers.IO) {
            recipeDatabase?.favoritesDao()?.deleteFavoriteRecipeFromCache(recipeId)
        }

    suspend fun getRecipeFromCache(recipeId: Int): Recipe? =
        withContext(Dispatchers.IO) {
            recipeDatabase?.recipesDao()?.getRecipeByRecipeId(recipeId)
        }

    suspend fun getRecipesByCategoryIdFromCache(categoryId: Int): List<Recipe>? =
        withContext(Dispatchers.IO) {
            recipeDatabase?.recipesDao()?.getRecipesList(categoryId)
        }

    suspend fun addRecipesToCache(recipesList: List<Recipe>) =
        withContext(Dispatchers.IO) {
            recipeDatabase?.recipesDao()?.addRecipesList(recipesList)
        }

    suspend fun getCategoriesFromCache(): List<Category>? =
        withContext(Dispatchers.IO) {
            recipeDatabase?.categoriesDao()?.getAllCategory()
        }

    suspend fun addCategoryToCache(categories: List<Category>) =
        withContext(Dispatchers.IO) {
            recipeDatabase?.categoriesDao()?.addCategory(categories)
        }

}