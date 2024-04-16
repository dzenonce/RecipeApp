package com.example.recipeapp.data.room

import androidx.room.RoomDatabase
import androidx.room.Database
import com.example.recipeapp.data.room.categories.CategoriesDao
import com.example.recipeapp.data.room.recipes.RecipesDao
import com.example.recipeapp.model.Category

@Database(entities = [Category::class], version = 2)
abstract class RecipeAppDatabase: RoomDatabase() {
    abstract fun categoriesDao(): CategoriesDao

    abstract fun recipesDao(): RecipesDao
}