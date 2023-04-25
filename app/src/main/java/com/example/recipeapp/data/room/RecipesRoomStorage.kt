package com.example.recipeapp.data.room

import com.example.recipeapp.data.room.categories.CategoriesDao
import com.example.recipeapp.data.room.recipes.recipes.favorites.FavoritesDao
import com.example.recipeapp.data.room.recipes.recipesList.RecipesDao
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipesRoomStorage(
    private val categoriesDao: CategoriesDao,
    private val recipesDao: RecipesDao,
    private val favoritesDao: FavoritesDao,
) {

    suspend fun getFavoritesFromCache(): List<Recipe>? =
        withContext(Dispatchers.IO) {
            favoritesDao.getFavoritesRecipes()
        }

    suspend fun addRecipeToFavorite(recipeId: Int) =
        withContext(Dispatchers.IO) {
            favoritesDao.addFavoriteRecipe(recipeId)
        }

    suspend fun deleteRecipeFromFavorite(recipeId: Int) =
        withContext(Dispatchers.IO) {
            favoritesDao.deleteFavoriteRecipeFromCache(recipeId)
        }

    suspend fun getRecipeFromCache(recipeId: Int): Recipe? =
        withContext(Dispatchers.IO) {
            recipesDao.getRecipeByRecipeId(recipeId)
        }

    suspend fun getRecipesByCategoryIdFromCache(categoryId: Int): List<Recipe> =
        withContext(Dispatchers.IO) {
            recipesDao.getRecipesList(categoryId)
        }

    suspend fun addRecipesToCache(recipesList: List<Recipe>) =
        withContext(Dispatchers.IO) {
            recipesDao.addRecipesList(recipesList)
        }

    suspend fun getCategoriesFromCache(): List<Category> =
        withContext(Dispatchers.IO) {
            categoriesDao.getAllCategory()
        }

    suspend fun addCategoryToCache(categories: List<Category>) =
        withContext(Dispatchers.IO) {
            categoriesDao.addCategory(categories)
        }

}