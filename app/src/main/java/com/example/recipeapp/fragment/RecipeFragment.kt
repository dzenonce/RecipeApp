package com.example.recipeapp.fragment

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.ARG_RECIPE
import com.example.recipeapp.R
import com.example.recipeapp.adapter.IngredientsAdapter
import com.example.recipeapp.adapter.MethodAdapter
import com.example.recipeapp.databinding.FragmentRecipeBinding
import com.example.recipeapp.model.DividerItemDecorator
import com.example.recipeapp.model.Recipe
import java.io.InputStream

fun RecyclerView.addItemDecorationWithoutLastItem() {
    if (layoutManager !is LinearLayoutManager)
        return

    addItemDecoration(DividerItemDecorator(context))
}

class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipeBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Recipe Fragment Binding must not be null")

    private var _recipe: Recipe? = null
    private val recipe
        get() = _recipe
            ?: throw IllegalStateException("Recipe list in RecipeFragment must not be null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            _recipe =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                    it.getParcelable(ARG_RECIPE, Recipe::class.java)
                else
                    it.getParcelable(ARG_RECIPE)
        }
        initUI()
        initRecycler()
    }

    private fun initRecycler() {
        val ingredientsAdapter = IngredientsAdapter(
            dataSet = recipe.ingredients,
        )
        val recyclerViewIngredients: RecyclerView = binding.rvIngredients
        recyclerViewIngredients.adapter = ingredientsAdapter

        val methodAdapter = MethodAdapter(
            dataSet = recipe.method,
        )
        val recyclerViewMethod: RecyclerView = binding.rvMethod
        recyclerViewMethod.adapter = methodAdapter

        binding.sbPortionCountSeekBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    ingredientsAdapter.updateIngredients(progress)
                    binding.tvPortionCountText.text = progress.toString()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }
            }
        )
    }

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    private fun initUI() {
        with(binding) {
            try {
                val inputStream: InputStream? = context?.assets?.open(recipe.imageUrl)
                val drawable: Drawable? = Drawable.createFromStream(inputStream, null)
                ivIngredientRecipeImage.setImageDrawable(drawable)
                ivIngredientRecipeImage.contentDescription =
                    "${context?.getString(R.string.content_description_image)} ${recipe.title}"
            } catch (e: Error) {
                Log.e("assets error", e.stackTraceToString())
            }

            tvIngredientRecipeHeader.text = recipe.title
            tvPortionText.text = "${context?.getString(R.string.title_portion_count)} "

            rvIngredients.addItemDecorationWithoutLastItem()
            rvMethod.addItemDecorationWithoutLastItem()
        }
    }

}