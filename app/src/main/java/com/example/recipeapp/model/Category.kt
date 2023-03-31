package com.example.recipeapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
) : Parcelable
