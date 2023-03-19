package com.example.recipeapp.ui.recipes.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentFavoritesBinding
import com.example.recipeapp.ui.ARG_RECIPE_ID

class FavoritesFragment : Fragment() {

    private val binding: FragmentFavoritesBinding by lazy {
        FragmentFavoritesBinding.inflate(layoutInflater)
    }

    private val navController by lazy { this.findNavController() }

    private val favoritesViewModel: FavoritesViewModel by viewModels()
    private val favoriteRecipesListAdapter = FavoriteRecipesListAdapter()

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
        favoritesViewModel.uiState.observe(viewLifecycleOwner) { favoritesUiState ->
            val recipesList = favoritesUiState.favoritesRecipesList
            if (recipesList.isNotEmpty()) {
                with(binding) {
                    tvRecipeFavoriteIsEmptyText.visibility = View.GONE
                    rvFavoriteRecipe.visibility = View.VISIBLE
                }

                favoriteRecipesListAdapter.dataSet = recipesList
                val recyclerView: RecyclerView = binding.rvFavoriteRecipe
                recyclerView.adapter = favoriteRecipesListAdapter

                favoriteRecipesListAdapter.setOnRecipeClickListener(
                    object : FavoriteRecipesListAdapter.OnRecipeClickListener {
                        override fun onRecipeClick(recipeId: Int) {
                            navController.navigate(
                                FavoritesFragmentDirections
                                    .actionFavoritesFragmentToRecipeFragment(recipeId)
                            )
                        }
                    }
                )
            }
        }
        favoritesViewModel.loadFavorites()
    }

}