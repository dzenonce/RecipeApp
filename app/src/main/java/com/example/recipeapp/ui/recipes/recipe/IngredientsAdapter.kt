package com.example.recipeapp.ui.recipes.recipe

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.model.Ingredient

class IngredientsAdapter(
    var dataSet: List<Ingredient> = emptyList(),
) : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    private var quantity = 1

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
        with(viewHolder) {
            ingredientName.text = dataSet[position].description
            val currentIngredientQuantity = getCurrentIngredientQuantity(position)
            ingredientCountAndMeasure.text =
                "$currentIngredientQuantity ${dataSet[position].unitOfMeasure}"
        }
    }

    override fun getItemCount() = dataSet.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateIngredients(portionCount: Int) {
        quantity = portionCount
        notifyDataSetChanged()
    }

    private fun getCurrentIngredientQuantity(position: Int): String {
        val quantityIngredient = dataSet[position].quantity

        if (quantityIngredient.toDoubleOrNull() == null) return quantityIngredient
        quantityIngredient.toDouble().let {
            val currentQuantity = it * quantity
            return if (currentQuantity % 1 == 0.0) currentQuantity.toInt().toString()
            else String.format("%.1f", currentQuantity)
        }
    }

}



