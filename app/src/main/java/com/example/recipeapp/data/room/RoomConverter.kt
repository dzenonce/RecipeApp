package com.example.recipeapp.data.room

import androidx.room.TypeConverter
import com.example.recipeapp.model.Ingredient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RoomConverter {
    private val gson = Gson()

    @TypeConverter
    fun convertIngredientsListToJson(ingredientsList: List<Ingredient>): String =
        gson.toJson(ingredientsList)

    @TypeConverter
    fun convertIngredientsListFromJson(jsonData: String): List<Ingredient> =
        gson.fromJson(
            jsonData,
            object : TypeToken<List<Ingredient>>() {}.type
            )

    @TypeConverter
    fun convertMethodListToJson(methodList: List<String>): String =
        gson.toJson(methodList)

    @TypeConverter
    fun convertMethodListFromJson(jsonData: String): List<String> =
        gson.fromJson(
            jsonData,
            object : TypeToken<List<String>>() {}.type
        )
}