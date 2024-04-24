package com.example.recipeapp.data.room.recipes.recipesList

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipeapp.model.Recipe

@Dao
interface RecipesDao {
    @Query("SELECT * FROM recipes WHERE category_id = :categoryId")
    suspend fun getRecipesList(categoryId: Int): List<Recipe>

    @Query("SELECT * FROM recipes WHERE id = :recipeId")
    suspend fun getRecipeByRecipeId(recipeId: Int): Recipe?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecipesList(recipes: List<Recipe>)
}