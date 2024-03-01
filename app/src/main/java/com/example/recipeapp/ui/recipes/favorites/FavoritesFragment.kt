package com.example.recipeapp.ui.recipes.favorites

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.example.recipeapp.data.STUB
import com.example.recipeapp.databinding.FragmentFavoritesBinding
import com.example.recipeapp.ui.ARG_RECIPE_ID
import com.example.recipeapp.ui.PREFERENCE_FILE_KEY
import com.example.recipeapp.ui.PREFERENCE_RECIPE_IDS_SET_KEY
import com.example.recipeapp.ui.recipes.recipe.RecipeFragment
import com.example.recipeapp.ui.recipes.recipe.RecipeViewModel

class FavoritesFragment : Fragment() {

    private val binding: FragmentFavoritesBinding by lazy {
        FragmentFavoritesBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkRecipeIdsListAndInitRecycler()
    }

    private fun checkRecipeIdsListAndInitRecycler() {
        val recipeIds = getFavorites().map { it.toInt() }.toSet()
        if (recipeIds.isNotEmpty()) {
            binding.tvRecipeFavoriteIsEmptyText.visibility = View.GONE
            initRecycler(recipeIds)
        }
    }

    private fun initRecycler(recipeIds: Set<Int>) {
        val recipeList = STUB.getRecipesByIds(recipeIds)
        val favoriteRecipesListAdapter = FavoriteRecipesListAdapter(
            dataSet = recipeList,
        )
        val recyclerView: RecyclerView = binding.rvFavoriteRecipe
        recyclerView.adapter = favoriteRecipesListAdapter

        favoriteRecipesListAdapter.setOnRecipeClickListener(
            object : FavoriteRecipesListAdapter.OnRecipeClickListener {
                override fun onRecipeClick(recipeId: Int) {
                    openRecipeByRecipeId(getBundle(recipeId))
                    Log.d("!!!", "FavoritesFragment recipeId $recipeId")
                }
            }
        )
    }


    private fun getFavorites(): HashSet<String> {
        val sharedPrefs = activity?.getSharedPreferences(
            PREFERENCE_FILE_KEY,
            Context.MODE_PRIVATE,
        )
        val favoritesSet = sharedPrefs?.getStringSet(
            PREFERENCE_RECIPE_IDS_SET_KEY,
            null,
        ) ?: mutableSetOf()

        return HashSet(favoritesSet)
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