package com.example.recipeapp.ui.xmlUi.recipes.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.recipeapp.R
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.ui.xmlUi.recipes.recipesList.RecipesListAdapter

class FavoriteRecipesListAdapter(
    dataSet: List<Recipe> = listOf(),
) : RecipesListAdapter(dataSet) {

    interface OnRecipeClickListener {
        fun onRecipeClick(recipeId: Int)
    }

    private var recipeClickListener: OnRecipeClickListener? = null
    fun setOnRecipeClickListener(listener: OnRecipeClickListener?) {
        recipeClickListener = listener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_recipe, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        super.onBindViewHolder(viewHolder, position)
        with(viewHolder) {
            recipeCard.setOnClickListener {
                recipeClickListener?.onRecipeClick(dataSet[position].id)
            }
        }
    }

}