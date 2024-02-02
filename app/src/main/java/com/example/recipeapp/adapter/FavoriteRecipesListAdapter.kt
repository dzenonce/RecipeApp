package com.example.recipeapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recipeapp.R
import com.example.recipeapp.model.Recipe

class FavoriteRecipesListAdapter(
    dataSet: List<Recipe>,
    fragment: Fragment,
) : RecipesListAdapter(dataSet, fragment) {

    interface OnRecipeClickListener {
        fun onRecipeClick(recipeId: Int)
    }

    private var recipeClickListener: OnRecipeClickListener? = null
    fun setOnRecipeClickListener(listener: OnRecipeClickListener?) {
        recipeClickListener = listener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_favorite_recipe, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        super.onBindViewHolder(viewHolder, position)
        viewHolder.recipeCard.setOnClickListener {
            recipeClickListener?.onRecipeClick(dataSet[position].id)
        }
    }

}