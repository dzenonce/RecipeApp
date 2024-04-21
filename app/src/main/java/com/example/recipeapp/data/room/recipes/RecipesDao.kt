package com.example.recipeapp.data.room.recipes

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipeapp.model.RecipesList

@Dao
interface RecipesDao {

    @Query("SELECT * FROM recipeslist WHERE category_id = :categoryId")
    suspend fun getRecipesList(categoryId: Int): RecipesList

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecipesByCategoryId(recipesListByCategory: RecipesList)
}