package com.example.recipeapp.data.room

import androidx.room.TypeConverter
import com.example.recipeapp.model.Ingredient
import com.example.recipeapp.model.Recipe
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

    @TypeConverter
    fun convertRecipesListToJson(recipesList: List<Recipe>): String =
        gson.toJson(recipesList)

    @TypeConverter
    fun convertRecipesListFromJson(jsonData: String): List<Recipe> =
        gson.fromJson(
            jsonData,
            object : TypeToken<List<Recipe>>() {}.type
        )

    @TypeConverter
    fun convertRecipeToJson(recipe: Recipe): String =
        gson.toJson(recipe)

    @TypeConverter
    fun convertRecipeFromJson(jsonData: String): Recipe =
        gson.fromJson(
            jsonData,
            object : TypeToken<Recipe>() {}.type
        )

}