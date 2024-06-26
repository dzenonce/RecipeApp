package com.example.recipeapp.ui.xmlUi.recipes.recipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R


class MethodAdapter(
    var dataSet: List<String> = emptyList(),
) : RecyclerView.Adapter<MethodAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val method: TextView

        init {
            method = view.findViewById(R.id.tvMethod)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_method, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.method.text =
            dataSet.mapIndexed { index, method ->
                "${index.plus(1)}. $method"
            }[position]
    }

    override fun getItemCount() = dataSet.size

}

