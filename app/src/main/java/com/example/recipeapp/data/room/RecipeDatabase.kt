package com.example.recipeapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.recipeapp.data.room.categories.CategoriesDao
import com.example.recipeapp.data.room.recipes.RecipesDao
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.RecipesList


@Database(
    entities = [
        Category::class,
        RecipesList::class
    ],
    version = 1
)
@TypeConverters(RoomConverter::class)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun categoriesDao(): CategoriesDao
    abstract fun recipesDao(): RecipesDao
}