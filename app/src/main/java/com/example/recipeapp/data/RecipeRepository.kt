package com.example.recipeapp.data

import com.example.recipeapp.data.api.RecipeApiService
import com.example.recipeapp.data.room.RecipesRoomStorage
import com.example.recipeapp.data.room.categories.CategoriesDao
import com.example.recipeapp.data.room.recipes.recipes.favorites.FavoritesDao
import com.example.recipeapp.data.room.recipes.recipesList.RecipesDao
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RecipeRepository(
    private val categoriesDao: CategoriesDao,
    private val recipesDao: RecipesDao,
    private val favoritesDao: FavoritesDao,
    private val ioDispatcher: CoroutineDispatcher,
    private val recipeDataSource: RecipeApiService,
) {

    val recipesCache = RecipesRoomStorage(
        categoriesDao = categoriesDao,
        recipesDao = recipesDao,
        favoritesDao = favoritesDao,
    )

    suspend fun loadCategories(): List<Category>? =
        withContext(ioDispatcher) {
            recipeDataSource.getCategories()
        }

    suspend fun loadRecipesListByCategoryId(categoryId: Int): List<Recipe>? =
        withContext(ioDispatcher) {
            recipeDataSource.getRecipesListByCategoryId(categoryId)
        }

    suspend fun loadRecipesByIds(ids: String): List<Recipe>? =
        withContext(ioDispatcher) {
            recipeDataSource.getRecipesByIds(ids)
        }

    suspend fun loadRecipeByRecipeId(recipeId: Int): Recipe? =
        withContext(ioDispatcher) {
            recipeDataSource.getRecipeByRecipeId(recipeId)
        }

    suspend fun loadCategoryByCategoryId(categoryId: Int): Category? =
        withContext(ioDispatcher) {
            recipeDataSource.getCategoryByCategoryId(categoryId)
        }

}