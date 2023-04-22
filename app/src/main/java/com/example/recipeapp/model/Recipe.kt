package com.example.recipeapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "category_id")
    var categoryId: Int = 0,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "ingredients")
    val ingredients: List<Ingredient>,
    @ColumnInfo(name = "method")
    val method: List<String>,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
)