package com.example.recipeapp.ui.xmlUi.category

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
import com.example.recipeapp.model.Category
import com.example.recipeapp.API_RECIPE_IMAGE_URL
import dagger.hilt.android.AndroidEntryPoint

class CategoriesListAdapter(
    var dataSet: List<Category> = emptyList(),
) : RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(categoryId: Int)
    }

    private var itemClickListener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener?) {
        itemClickListener = listener
    }

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
        with(viewHolder) {
            Glide.with(categoryImage)
                .load("$API_RECIPE_IMAGE_URL/${dataSet[position].imageUrl}")
                .error(R.drawable.img_error)
                .placeholder(R.drawable.img_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(categoryImage)

            categoryImage.contentDescription =
                itemView.context.getString(R.string.content_description_image) + viewHolder.categoryName

            categoryName.text = dataSet[position].title
            categoryDescription.text = dataSet[position].description

            categoryItem.setOnClickListener {
                itemClickListener?.onItemClick(dataSet[position].id)
            }
        }
    }

    override fun getItemCount() = dataSet.size

}
