package com.example.recipeapp.ui.recipes.recipesList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.recipeapp.R
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.ui.API_RECIPE_IMAGE_URL

open class RecipesListAdapter(
    var dataSet: List<Recipe> = listOf(),
) : RecyclerView.Adapter<RecipesListAdapter.ViewHolder>() {

    interface OnRecipeClickListener {
        fun onRecipeClick(recipeId: Int)
    }

    private var recipeClickListener: OnRecipeClickListener? = null
    fun setOnRecipeClickListener(listener: OnRecipeClickListener?) {
        recipeClickListener = listener
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recipeCard: CardView
        val recipeImage: ImageView
        val recipeName: TextView

        init {
            recipeCard = view.findViewById(R.id.cvRecipeCard)
            recipeImage = view.findViewById(R.id.ivRecipeImage)
            recipeName = view.findViewById(R.id.tvRecipeName)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_recipe, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        with(viewHolder) {
            Glide.with(recipeImage)
                .load("$API_RECIPE_IMAGE_URL/${dataSet[position].imageUrl}")
                .error(R.drawable.img_error)
                .placeholder(R.drawable.img_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(recipeImage)

            recipeImage.contentDescription =
                itemView.context
                    .getString(R.string.content_description_image) + dataSet[position].title

            recipeName.text = dataSet[position].title

            recipeCard.setOnClickListener {
                recipeClickListener?.onRecipeClick(dataSet[position].id)
            }
        }
    }

    override fun getItemCount() = dataSet.size

}