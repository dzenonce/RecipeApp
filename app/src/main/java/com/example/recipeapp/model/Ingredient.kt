package com.example.recipeapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class Ingredient(
    @ColumnInfo(name = "quantity")
    val quantity: String,
    @ColumnInfo(name = "unit_of_measure")
    val unitOfMeasure: String,
    @ColumnInfo(name = "description")
    val description: String,
)
