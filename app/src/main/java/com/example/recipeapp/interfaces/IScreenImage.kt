package com.example.recipeapp.interfaces

import android.graphics.drawable.Drawable

interface IScreenImage {

    fun loadScreenHeaderImage(imageUrl: String): Drawable?

}