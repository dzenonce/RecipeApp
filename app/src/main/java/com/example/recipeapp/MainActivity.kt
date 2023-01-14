package com.example.recipeapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.recipeapp.fragment.CategoriesListFragment
import com.example.recipeapp.databinding.ActivityMainBinding
import com.example.recipeapp.fragment.FavoritesFragment

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("ActivityMainBinding must not be null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<CategoriesListFragment>(R.id.frgMainFragmentContainer)
            }
        }

        with(binding) {

            btnCategoryButton.setOnClickListener {
                showCategories()
            }

            btnFavoriteButton.setOnClickListener {
                showFavorites()
            }
        }

    }

    private fun showCategories() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<CategoriesListFragment>(R.id.frgMainFragmentContainer)
            addToBackStack(null)
        }

    }

    private fun showFavorites() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<FavoritesFragment>(R.id.frgMainFragmentContainer)
            addToBackStack(null)
        }

    }

}