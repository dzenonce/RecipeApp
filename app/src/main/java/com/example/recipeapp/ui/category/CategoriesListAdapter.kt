package com.example.recipeapp.ui.category

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
import com.example.recipeapp.model.Category
import java.io.InputStream

class CategoriesListAdapter(
    var dataSet: List<Category> = listOf(),
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
            categoryName.text = dataSet[position].title
            try {
                val inputStream: InputStream =
                    itemView.context.assets.open(dataSet[position].imageUrl)
                val drawable = Drawable.createFromStream(inputStream, null)
                categoryImage.setImageDrawable(drawable)

            } catch (e: Error) {
                Log.e("assets error", e.stackTraceToString())
            }
            val contentDescriptionImage =
                itemView.context.getString(R.string.content_description_image)
            categoryImage.contentDescription =
                contentDescriptionImage + viewHolder.categoryName

            categoryDescription.text = dataSet[position].description

            categoryItem.setOnClickListener {
                itemClickListener?.onItemClick(dataSet[position].id)
            }
        }
    }

    override fun getItemCount() = dataSet.size

}
