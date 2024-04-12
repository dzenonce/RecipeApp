package com.example.recipeapp.data.room

import androidx.room.RoomDatabase
import androidx.room.Database
import com.example.recipeapp.model.Category

@Database(entities = [Category::class], version = 1)
abstract class Database: RoomDatabase() {
    abstract fun categoryDao(): CategoryDao

}