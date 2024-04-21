package com.example.recipeapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecipesList(
    @PrimaryKey
    @ColumnInfo(name = "category_id")
    val categoryId: Int,
    @ColumnInfo(name = "recipes_list")
    val recipesList: List<Recipe>,
)
