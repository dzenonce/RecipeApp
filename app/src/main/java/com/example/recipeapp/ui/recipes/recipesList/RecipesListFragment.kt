package com.example.recipeapp.ui.recipes.recipesList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentRecipesListBinding
import com.example.recipeapp.ui.API_RECIPE_IMAGE_URL

class RecipesListFragment : Fragment() {

    private val binding: FragmentRecipesListBinding by lazy {
        FragmentRecipesListBinding.inflate(layoutInflater)
    }

    private val navController by lazy { this.findNavController() }
    private val recipesListFragmentArgs: RecipesListFragmentArgs by navArgs()

    private val recipesUiState: RecipesListViewModel by viewModels()
    private val recipesListAdapter = RecipesListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        val category = recipesListFragmentArgs.category
            ?: throw IllegalArgumentException("Category in RecipesListFragment must not be null")

        with(binding) {
            Glide.with(this@RecipesListFragment)
                .load("$API_RECIPE_IMAGE_URL/${category.imageUrl}")
                .error(R.drawable.img_error)
                .placeholder(R.drawable.img_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivRecipesListImage)

            ivRecipesListImage.contentDescription =
                "${context?.getString(R.string.content_description_image)}" +
                        " ${category.title}"
            tvRecipesListHeaderName.text = category.title
        }

        recipesUiState.uiState.observe(viewLifecycleOwner) { recipeUiState ->
            recipesListAdapter.dataSet = recipeUiState.recipeList
            val recyclerView: RecyclerView = binding.rvRecipes
            recyclerView.adapter = recipesListAdapter

            recipesListAdapter.setOnRecipeClickListener(
                object : RecipesListAdapter.OnRecipeClickListener {
                    override fun onRecipeClick(recipeId: Int) {
                        navController.navigate(
                            RecipesListFragmentDirections
                                .actionRecipesListFragmentToRecipeFragment(recipeId)
                        )
                    }
                }
            )
        }
        recipesUiState.loadRecipesList(category.id)
    }
}