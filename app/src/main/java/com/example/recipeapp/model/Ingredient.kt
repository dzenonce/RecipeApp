package com.example.recipeapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class Ingredient(
    val quantity: String,
    @ColumnInfo(name = "unit_of_measure") val unitOfMeasure: String,
    val description: String,
)
