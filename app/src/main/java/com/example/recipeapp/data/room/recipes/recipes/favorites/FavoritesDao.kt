package com.example.recipeapp.data.room.recipes.recipes.favorites

import androidx.room.Dao
import androidx.room.Query
import com.example.recipeapp.model.Recipe

@Dao
interface FavoritesDao {

    @Query("SELECT * FROM recipes WHERE is_favorite = 1")
    fun getFavoritesRecipes(): List<Recipe>?

    @Query("UPDATE recipes SET is_favorite = 1 WHERE id = :recipeId")
    fun addFavoriteRecipe(recipeId: Int)

    @Query("UPDATE recipes SET is_favorite = 0 WHERE id = :recipeId ")
    fun deleteFavoriteRecipeFromCache(recipeId: Int)


}