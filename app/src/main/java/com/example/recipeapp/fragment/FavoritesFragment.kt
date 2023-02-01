package com.example.recipeapp.fragment

import STUB
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.adapter.RecipeListAdapter
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

        if (collectionRecipeIds.isEmpty())
            binding.tvRecipeFavoriteIsEmptyText.visibility = View.VISIBLE
        else {
            binding.tvRecipeFavoriteIsEmptyText.visibility = View.GONE
            initRecycler()

        }
    }

    private fun initRecycler() {
        val recipesListByRecipeId =
            collectionRecipeIds.map {
                STUB.getRecipeByRecipeId(it.toInt())
            }

        val recipeListAdapter = RecipeListAdapter(
            dataSet = recipesListByRecipeId,
            fragment = this,
        )
        val recyclerView: RecyclerView = binding.rvFavoriteRecipe
        recyclerView.adapter = recipeListAdapter
    }

}