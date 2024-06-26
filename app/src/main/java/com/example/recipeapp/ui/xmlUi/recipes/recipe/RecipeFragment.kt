package com.example.recipeapp.ui.xmlUi.recipes.recipe

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentRecipeBinding
import com.example.recipeapp.ui.xmlUi.recipes.recipe.decorator.DividerItemDecorator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeFragment : Fragment() {

    private val binding: FragmentRecipeBinding by lazy {
        FragmentRecipeBinding.inflate(layoutInflater)
    }

    private val recipeFragmentArgs: RecipeFragmentArgs by navArgs()
    private val recipeViewModel: RecipeViewModel by viewModels()

    private val ingredientsAdapter = IngredientsAdapter()
    private val methodAdapter = MethodAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI(recipeFragmentArgs.recipeId)
    }

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables", "NotifyDataSetChanged")
    private fun initUI(recipeId: Int) {
        recipeViewModel.uiState.observe(viewLifecycleOwner) { recipeState ->
            with(binding) {
                Glide.with(this@RecipeFragment)
                    .load(recipeState.recipeImageUrl)
                    .error(R.drawable.img_error)
                    .placeholder(R.drawable.img_placeholder)
                    .into(ivIngredientRecipeImage)

                ivIngredientRecipeImage.contentDescription =
                    "${context?.getString(R.string.content_description_image)} ${recipeState.recipe?.title}"

                setFavoriteIconState(recipeState.isFavorite)
                ibIngredientFavoriteButton.setOnClickListener {
                    recipeViewModel.onFavoritesClicked(recipeId)
                    setFavoriteIconState(recipeState.isFavorite)
                }

                tvIngredientRecipeHeader.text = recipeState.recipe?.title
                tvPortionText.text =
                    "${context?.getString(R.string.title_portion_count)} ${sbPortionCountSeekBar.progress}"

                rvIngredients.addItemDecorationWithoutLastItem()
                rvMethod.addItemDecorationWithoutLastItem()

                ingredientsAdapter.dataSet = recipeState.recipe?.ingredients ?: emptyList()
                val recyclerViewIngredients: RecyclerView = binding.rvIngredients
                recyclerViewIngredients.adapter = ingredientsAdapter

                methodAdapter.dataSet = recipeState.recipe?.method ?: emptyList()
                val recyclerViewMethod: RecyclerView = binding.rvMethod
                recyclerViewMethod.adapter = methodAdapter

                binding.sbPortionCountSeekBar.setOnSeekBarChangeListener(
                    PortionSeekBarListener { portionsCount ->
                        ingredientsAdapter.updateIngredients(portionsCount)
                        binding.tvPortionText.text =
                            "${context?.getString(R.string.title_portion_count)} $portionsCount"
                    }
                )
            }
        }
        recipeViewModel.loadRecipe(recipeId)
    }

    private fun setFavoriteIconState(isFavorite: Boolean) =
        if (isFavorite) binding.ibIngredientFavoriteButton.setImageResource(R.drawable.ic_heart)
        else binding.ibIngredientFavoriteButton.setImageResource(R.drawable.ic_heart_empty)

}

fun RecyclerView.addItemDecorationWithoutLastItem() {
    if (layoutManager !is LinearLayoutManager) return
    addItemDecoration(DividerItemDecorator(context))
}

class PortionSeekBarListener(
    val onChangeIngredients: (Int) -> Unit,
) : OnSeekBarChangeListener {

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        onChangeIngredients(progress)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}
    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
}
