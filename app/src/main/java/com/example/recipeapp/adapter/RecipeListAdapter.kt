package com.example.recipeapp.adapter

import com.example.recipeapp.model.Recipe
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.fragment.RecipesListFragment
import java.io.InputStream

class RecipeListAdapter(
    private val dataSet: List<Recipe>,
    private val fragment: RecipesListFragment
) : RecyclerView.Adapter<RecipeListAdapter.ViewHolder>() {

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
        viewHolder.recipeName.text = dataSet[position].title

        try {
            val inputStream: InputStream? =
                fragment.context?.assets?.open(dataSet[position].imageUrl)
            val drawable: Drawable? = Drawable.createFromStream(inputStream, null)
            viewHolder.recipeImage.setImageDrawable(drawable)
        } catch (e: Error) {
            Log.e("assets error", e.stackTraceToString())
        }
        viewHolder.recipeImage.contentDescription =
            fragment.context?.getString(R.string.content_description_image) + dataSet[position].title

        viewHolder.recipeCard.setOnClickListener {
            recipeClickListener?.onRecipeClick(dataSet[position].id)
        }
    }

    override fun getItemCount() = dataSet.size

}