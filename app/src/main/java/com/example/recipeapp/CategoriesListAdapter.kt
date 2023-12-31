package com.example.recipeapp

import Category
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import java.io.InputStream

class CategoriesListAdapter(
    private val dataSet: List<Category>,
    private val fragment: CategoriesListFragment,
) : RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var categoryItem: CardView
        var categoryImage: ImageView
        var categoryName: TextView
        var categoryDescription: TextView

        init {
            categoryItem = view.findViewById(R.id.cvCategoryItem)
            categoryImage = view.findViewById(R.id.ivCategoryImage)
            categoryName = view.findViewById(R.id.tvCategoryName)
            categoryDescription = view.findViewById(R.id.tvCategoryDescription)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_category, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.categoryName.text = dataSet[position].title

        try {
            val inputStream: InputStream? =
                fragment.context?.assets?.open(dataSet[position].imageUrl)
            val drawable = Drawable.createFromStream(inputStream, null)
            viewHolder.categoryImage.setImageDrawable(drawable)
        } catch (e: Error) {
            Log.e("Stack Trace", e.stackTraceToString())
        }

        viewHolder.categoryDescription.text = dataSet[position].description

    }

    override fun getItemCount() = dataSet.size

}
