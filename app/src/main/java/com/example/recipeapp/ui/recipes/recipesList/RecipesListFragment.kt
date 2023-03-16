package com.example.recipeapp.ui.recipes.recipesList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentRecipesListBinding
import com.example.recipeapp.ui.ARG_CATEGORY_ID
import com.example.recipeapp.ui.ARG_RECIPE_ID
import com.example.recipeapp.ui.recipes.recipe.RecipeFragment

class RecipesListFragment : Fragment() {

    private val binding: FragmentRecipesListBinding by lazy {
        FragmentRecipesListBinding.inflate(layoutInflater)
    }

    private val categoryId: Int by lazy { initCategoryId() }
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

    private fun initCategoryId() = arguments?.getInt(ARG_CATEGORY_ID)
        ?: throw Error("Category Id is not empty")

    private fun initUi() {
        recipesUiState.uiState.observe(viewLifecycleOwner) { recipeUiState ->
            with(binding) {
                ivRecipesListImage.setImageDrawable(recipeUiState.categoryImageUrl)
                ivRecipesListImage.contentDescription =
                    "${context?.getString(R.string.content_description_image)}" +
                            " ${recipeUiState.categoryName}"
                tvRecipesListHeaderName.text = recipeUiState.categoryName
            }

            recipesListAdapter.dataSet = recipeUiState.recipeList
            val recyclerView: RecyclerView = binding.rvRecipes
            recyclerView.adapter = recipesListAdapter

            recipesListAdapter.setOnRecipeClickListener(
                object : RecipesListAdapter.OnRecipeClickListener {
                    override fun onRecipeClick(recipeId: Int) {
                        openRecipeByRecipeId(getBundle(recipeId))
                    }
                }
            )
        }
        recipesUiState.loadRecipesList(categoryId)
    }

    private fun openRecipeByRecipeId(bundle: Bundle) {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<RecipeFragment>(R.id.frgMainFragmentContainer, args = bundle)
            addToBackStack(null)
        }
    }

    private fun getBundle(recipeId: Int) = bundleOf(ARG_RECIPE_ID to recipeId)

}