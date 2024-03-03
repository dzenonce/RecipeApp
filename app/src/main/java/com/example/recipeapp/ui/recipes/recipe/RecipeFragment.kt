package com.example.recipeapp.ui.recipes.recipe

import android.annotation.SuppressLint
import android.os.Bundle
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
import com.example.recipeapp.ui.ARG_RECIPE_ID
import com.example.recipeapp.ui.decorator.DividerItemDecorator

fun RecyclerView.addItemDecorationWithoutLastItem() {
    if (layoutManager !is LinearLayoutManager) return
    addItemDecoration(DividerItemDecorator(context))
}

class RecipeFragment : Fragment() {

    private val binding: FragmentRecipeBinding by lazy {
        FragmentRecipeBinding.inflate(layoutInflater)
    }
    private val recipeId: Int by lazy { initRecipe() }
    private val recipeViewModel: RecipeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initRecipe() =
        arguments?.getInt(ARG_RECIPE_ID)
            ?: throw IllegalStateException("Recipe list in RecipeFragment must not be null")

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    private fun initUI() {
        recipeViewModel.uiState.observe(viewLifecycleOwner) { recipeState ->
            with(binding) {
                ivIngredientRecipeImage.setImageDrawable(recipeState.recipeImage)
                ivIngredientRecipeImage.contentDescription =
                    "${context?.getString(R.string.content_description_image)} ${recipeState.recipe?.title}"

                setFavoriteIconState(recipeState.isFavorite)
                ibIngredientFavoriteButton.setOnClickListener {
                    recipeViewModel.onFavoritesClicked()
                    setFavoriteIconState(recipeState.isFavorite)
                }

                tvIngredientRecipeHeader.text = recipeState.recipe?.title
                tvPortionText.text =
                    "${context?.getString(R.string.title_portion_count)} ${sbPortionCountSeekBar.progress}"

                rvIngredients.addItemDecorationWithoutLastItem()
                rvMethod.addItemDecorationWithoutLastItem()

                initRecycler(recipeState)
            }
        }
        recipeViewModel.loadRecipe(recipeId)
    }

    private fun initRecycler(recipeState: RecipeUiState) {
        val ingredientsAdapter = IngredientsAdapter(
            dataSet = recipeState.recipe?.ingredients ?: listOf(),
        )
        val recyclerViewIngredients: RecyclerView = binding.rvIngredients
        recyclerViewIngredients.adapter = ingredientsAdapter

        val methodAdapter = MethodAdapter(
            dataSet = recipeState.recipe?.method ?: listOf(),
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

    private fun setFavoriteIconState(isFavorite: Boolean) =
        if (isFavorite) binding.ibIngredientFavoriteButton.setImageResource(R.drawable.ic_heart)
        else binding.ibIngredientFavoriteButton.setImageResource(R.drawable.ic_heart_empty)

}