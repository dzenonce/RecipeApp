package com.example.recipeapp.ui.recipes.recipe

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
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentRecipeBinding
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.ui.ARG_RECIPE
import com.example.recipeapp.ui.decorator.DividerItemDecorator
import java.io.InputStream

fun RecyclerView.addItemDecorationWithoutLastItem() {
    if (layoutManager !is LinearLayoutManager) return
    addItemDecoration(DividerItemDecorator(context))
}

class RecipeFragment : Fragment() {

    private val binding: FragmentRecipeBinding by lazy {
        FragmentRecipeBinding.inflate(layoutInflater)
    }
    private val recipe: Recipe by lazy { initRecipe() }
    private val viewModel: RecipeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initRecycler()
    }

    private fun initRecipe() =
        arguments?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                it.getParcelable(
                    ARG_RECIPE,
                    Recipe::class.java
                )
            else it.getParcelable(ARG_RECIPE)
        } ?: throw IllegalStateException("Recipe list in RecipeFragment must not be null")

    private fun initRecycler() {
        val ingredientsAdapter = IngredientsAdapter(
            dataSet = viewModel.uiState.value?.recipe?.ingredients ?: listOf(),
        )
        val recyclerViewIngredients: RecyclerView = binding.rvIngredients
        recyclerViewIngredients.adapter = ingredientsAdapter

        val methodAdapter = MethodAdapter(
            dataSet = viewModel.uiState.value?.recipe?.method ?: listOf(),
        )
        val recyclerViewMethod: RecyclerView = binding.rvMethod
        recyclerViewMethod.adapter = methodAdapter

        binding.sbPortionCountSeekBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                @SuppressLint("SetTextI18n")
                override fun onProgressChanged(
                    seekBar: SeekBar?, progress: Int, fromUser: Boolean
                ) {
                    ingredientsAdapter.updateIngredients(progress)
                    binding.tvPortionText.text =
                        "${context?.getString(R.string.title_portion_count)} $progress"
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            }
        )
    }

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    private fun initUI() {
        viewModel.uiState.observe(viewLifecycleOwner) { recipeState ->

            with(binding) {
                try {
                    val inputStream: InputStream? =
                        context?.assets?.open(recipeState.recipe?.imageUrl.toString())
                    val drawable: Drawable? =
                        Drawable.createFromStream(inputStream, null)
                    ivIngredientRecipeImage.setImageDrawable(drawable)
                    ivIngredientRecipeImage.contentDescription =
                        "${context?.getString(R.string.content_description_image)} ${viewModel.uiState.value?.recipe?.title}"
                } catch (e: Error) {
                    Log.e("assets error", e.stackTraceToString())
                }

                setFavoriteIconState(recipeState.isFavorite)
                ibIngredientFavoriteButton.setOnClickListener {
                    viewModel.onFavoritesClicked()
                    setFavoriteIconState(recipeState.isFavorite)
                }

                tvIngredientRecipeHeader.text = viewModel.uiState.value?.recipe?.title
                tvPortionText.text =
                    "${context?.getString(R.string.title_portion_count)} ${sbPortionCountSeekBar.progress}"

                rvIngredients.addItemDecorationWithoutLastItem()
                rvMethod.addItemDecorationWithoutLastItem()
            }
        }
        viewModel.loadRecipe(recipe.id)
    }

    private fun setFavoriteIconState(isFavorite: Boolean) =
        if (isFavorite) setColoredFavoriteIcon()
        else setUncoloredFavoriteIcon()

     private fun setUncoloredFavoriteIcon() =
        binding.ibIngredientFavoriteButton.setImageResource(R.drawable.ic_heart_empty)

     private fun setColoredFavoriteIcon() =
        binding.ibIngredientFavoriteButton.setImageResource(R.drawable.ic_heart)

}