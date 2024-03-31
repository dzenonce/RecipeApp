package com.example.recipeapp.data

import android.util.Log
import com.example.recipeapp.model.Category
import com.example.recipeapp.model.Recipe
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.net.URL


class NetworkRequest(
    private val serializer: Gson,
) {

    private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    fun loadCategories(url: URL): Array<Category> {
        val request = Request.Builder()
            .url(url)
            .build()

        try {
            client.newCall(request).execute().use { response ->
                return serializer.fromJson(
                    response.body?.string(),
                    Array<Category>::class.java,
                ) ?: emptyArray()
            }
        } catch (e: Error) {
            Log.e("[-] category load failed", e.stackTraceToString())
            return emptyArray()
        }
    }

    fun loadRecipesListByCategoryId(url: URL): Array<Recipe> {
        val request = Request.Builder()
            .url(url)
            .build()

        try {
            client.newCall(request).execute().use { response ->
                return serializer.fromJson(
                    response.body?.string(),
                    Array<Recipe>::class.java
                ) ?: emptyArray()
            }
        } catch (e: Error) {
            Log.e("[-] load recipe list failed", e.stackTraceToString())
            return emptyArray()
        }
    }

}