package com.example.recipeapp.data.room

import androidx.room.Dao
import androidx.room.Query
import com.example.recipeapp.model.Category

@Dao
interface CategoryDao {
        @Query("SELECT * FROM category")
        fun getAllCategory(): List<Category>
}