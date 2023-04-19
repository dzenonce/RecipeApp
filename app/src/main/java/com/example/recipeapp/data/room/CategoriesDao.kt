package com.example.recipeapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipeapp.model.Category

@Dao
interface CategoriesDao {
        @Query("SELECT * FROM category")
        fun getAllCategory(): List<Category>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun addCategory(categories: List<Category>)

}