package com.example.recipeapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.recipeapp.data.room.categories.CategoriesDao
import com.example.recipeapp.data.room.recipes.recipes.favorites.FavoritesDao
import com.example.recipeapp.data.room.recipes.recipesList.RecipesDao
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe


@Database(
    entities = [
        Category::class,
        Recipe::class
    ],
    version = 1
)
@TypeConverters(RoomConverter::class)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun categoriesDao(): CategoriesDao
    abstract fun recipesDao(): RecipesDao
    abstract fun favoritesDao(): FavoritesDao
}