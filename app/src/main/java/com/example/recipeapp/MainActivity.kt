package com.example.recipeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.recipeapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("ActivityMainBinding must not be null")

    private val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val transactionManager = fragmentManager.beginTransaction()
        transactionManager.add(R.id.frgMainFragmentContainer, CategoriesListFragment())
        transactionManager.commit()

    }

}