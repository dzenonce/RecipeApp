package com.example.recipeapp.ui.xmlUi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.recipeapp.R
import com.example.recipeapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val navController by lazy {
        this.findNavController(R.id.navHostFragmentContainer)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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