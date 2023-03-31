package com.example.recipeapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.recipeapp.data.NetworkRequest
import com.example.recipeapp.databinding.ActivityMainBinding
import com.example.recipeapp.ui.API_RECIPE_URL
import com.google.gson.Gson
import java.net.URL
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("ActivityMainBinding must not be null")

    private val navController by lazy {
        this.findNavController(R.id.navHostFragmentContainer)
    }

    private val gson = Gson().newBuilder().create()

    private val threadPool = Executors.newFixedThreadPool(10)
    private val networkRequest = NetworkRequest(serializer = gson)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        threadPool.execute {
            val categoriesIdsList = networkRequest.loadCategories(
                url = URL("$API_RECIPE_URL/category")
            ).map { it.id }

            val recipesList =
                categoriesIdsList.map { categoryId ->
                    networkRequest.loadRecipesListByCategoryId(
                        url = URL("$API_RECIPE_URL/category/$categoryId/recipes")
                    )
                }
        }

        with(binding) {
            btnCategoryButton.setOnClickListener {
                navController.navigate(R.id.categoriesListFragment)
            }
            btnFavoriteButton.setOnClickListener {
                navController.navigate(R.id.favoritesFragment)
            }
        }

    }
}

