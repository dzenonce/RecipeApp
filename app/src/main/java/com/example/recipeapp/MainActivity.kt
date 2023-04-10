package com.example.recipeapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.recipeapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("ActivityMainBinding must not be null")

    private val navController by lazy {
        this.findNavController(R.id.navHostFragmentContainer)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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