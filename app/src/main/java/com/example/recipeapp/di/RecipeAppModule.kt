package com.example.recipeapp.di

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.room.Room
import com.example.recipeapp.API_RECIPE_URL
import com.example.recipeapp.DATABASE_NAME
import com.example.recipeapp.TOAST_TEXT_ERROR_LOADING
import com.example.recipeapp.data.api.RecipeApiService
import com.example.recipeapp.data.retrofit.IRecipeApiService
import com.example.recipeapp.data.room.RecipeDatabase
import com.example.recipeapp.data.room.categories.CategoriesDao
import com.example.recipeapp.data.room.recipes.recipes.favorites.FavoritesDao
import com.example.recipeapp.data.room.recipes.recipesList.RecipesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
class RecipeAppModule {

    @Provides
    fun provideRecipeDatabase(@ApplicationContext context: Context): RecipeDatabase =
        Room.databaseBuilder(
            context,
            RecipeDatabase::class.java,
            DATABASE_NAME
        ).build()

    @Provides
    fun provideCategoriesDao(recipeDatabase: RecipeDatabase): CategoriesDao =
        recipeDatabase.categoriesDao()

    @Provides
    fun provideRecipesDao(recipeDatabase: RecipeDatabase): RecipesDao =
        recipeDatabase.recipesDao()

    @Provides
    fun provideFavoritesDao(recipeDatabase: RecipeDatabase): FavoritesDao =
        recipeDatabase.favoritesDao()

    @Provides
    fun provideHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addNetworkInterceptor(interceptor)
            .build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("$API_RECIPE_URL/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    fun provideRecipeApiService(retrofit: Retrofit): IRecipeApiService =
        retrofit.create(IRecipeApiService::class.java)

    @Provides
    fun provideRecipeDataSource(iRecipeApiService: IRecipeApiService): RecipeApiService =
        RecipeApiService(iRecipeApiService)

    @Provides
    @IoDispatcher
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun provideCoroutineExceptionHandler(@ApplicationContext context: Context): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, exception ->
            Log.e("coroutine exception", exception.stackTraceToString())
            Toast.makeText(
                context,
                TOAST_TEXT_ERROR_LOADING,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class IoDispatcher

}
