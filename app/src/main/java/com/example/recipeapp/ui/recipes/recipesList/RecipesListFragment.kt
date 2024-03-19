package com.example.recipeapp.ui.recipes.recipesList

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentRecipesListBinding
import java.io.InputStream

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
        val category = recipesListFragmentArgs.category ?: throw IllegalArgumentException()

        with(binding) {
            ivRecipesListImage.setImageDrawable(loadCategoryImage(category.imageUrl))
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

    private fun loadCategoryImage(categoryImageUrl: String) =
        try {
            val inputStream: InputStream? = context?.assets?.open(categoryImageUrl)
            Drawable.createFromStream(inputStream, null)
        } catch (e: Error) {
            Log.e("assets error", e.stackTraceToString())
            null
        }

}