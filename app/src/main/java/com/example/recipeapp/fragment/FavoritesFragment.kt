package com.example.recipeapp.fragment

import STUB
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.ARG_RECIPE
import com.example.recipeapp.PREFERENCE_RECIPE_IDS_SET_KEY
import com.example.recipeapp.R
import com.example.recipeapp.adapter.FavoriteRecipesListAdapter
import com.example.recipeapp.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Favorite fragment binding must not be null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recipeIds = getFavorites()?.map { idInString ->
            idInString.toInt()
        }?.toSet()
        if (recipeIds?.isEmpty() == false) {
            binding.tvRecipeFavoriteIsEmptyText.visibility = View.GONE
            initRecycler(recipeIds)
        }
    }

    private fun initRecycler(recipeIds: Set<Int>?) {
        val recipeList = STUB.getRecipesByIds(recipeIds)
        val favoriteRecipesListAdapter = FavoriteRecipesListAdapter(
            dataSet = recipeList,
            fragment = this
        )
        val recyclerView: RecyclerView = binding.rvFavoriteRecipe
        recyclerView.adapter = favoriteRecipesListAdapter

        favoriteRecipesListAdapter.setOnRecipeClickListener(
            object : FavoriteRecipesListAdapter.OnRecipeClickListener {
                override fun onRecipeClick(recipeId: Int) {
                    val recipe = STUB.getRecipeByRecipeId(recipeId)
                    val bundle = bundleOf()
                    bundle.putParcelable(ARG_RECIPE, recipe)
                    openRecipeByRecipeId(bundle)
                }
            }
        )
    }

    private fun getFavorites(): MutableSet<String>? {
        val sharedPrefs = activity?.getSharedPreferences(
            com.example.recipeapp.PREFERENCE_FILE_KEY,
            Context.MODE_PRIVATE,
        )
        return setOf(
            sharedPrefs?.getStringSet(
                PREFERENCE_RECIPE_IDS_SET_KEY,
                mutableSetOf(),
            )
        ).first()
    }

    private fun openRecipeByRecipeId(bundle: Bundle) {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<RecipeFragment>(R.id.frgMainFragmentContainer, args = bundle)
            addToBackStack(null)
        }
    }

}