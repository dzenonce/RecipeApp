package com.example.recipeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.recipeapp.databinding.ActivityMainBinding

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

    fun showCategories() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<CategoriesListFragment>(R.id.frgMainFragmentContainer)
            setReorderingAllowed(true)
            addToBackStack(null)
        }

    }

    fun showFavorites() {
        supportFragmentManager.commit {
            replace<FavoritesFragment>(R.id.frgMainFragmentContainer)
            setReorderingAllowed(true)
            addToBackStack(null)
        }

    }

}