package com.example.recipeapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.model.Ingredient

class IngredientsAdapter(
    private val dataSet: List<Ingredient>,
) : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    private var quantity = 3

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ingredientName: TextView
        val ingredientCountAndMeasure: TextView

        init {
            ingredientName = view.findViewById(R.id.tvIngredientName)
            ingredientCountAndMeasure = view.findViewById(R.id.tvIngredientCountAndMeasure)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_ingredient, viewGroup, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.ingredientName.text = dataSet[position].description
        val currentIngredientQuantity = getCurrentIngredientQuantity(position)
        viewHolder.ingredientCountAndMeasure.text =
            "$currentIngredientQuantity ${dataSet[position].unitOfMeasure}"
    }

    override fun getItemCount() = dataSet.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateIngredients(progress: Int) {
        quantity = progress
        notifyDataSetChanged()
    }

    private fun getCurrentIngredientQuantity(position: Int): String {
        val recipeIngredientQuantity = dataSet[position].quantity
        val currentIngredientQuantityInDouble = recipeIngredientQuantity.toDouble() * quantity
        currentIngredientQuantityInDouble.apply {
            return if (this % 1 == 0.0)
                this.toInt().toString()
            else String.format("%.1f", this)
        }
    }


}


