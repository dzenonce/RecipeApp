package com.example.recipeapp.fragment

import android.annotation.SuppressLint
import android.content.Context
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
import com.example.recipeapp.PREFERENCE_RECIPE_IDS_SET_KEY
import com.example.recipeapp.R
import com.example.recipeapp.adapter.IngredientsAdapter
import com.example.recipeapp.adapter.MethodAdapter
import com.example.recipeapp.databinding.FragmentRecipeBinding
import com.example.recipeapp.model.DividerItemDecorator
import com.example.recipeapp.model.Recipe
import java.io.InputStream

fun RecyclerView.addItemDecorationWithoutLastItem() {
    if (layoutManager !is LinearLayoutManager) return
    addItemDecoration(DividerItemDecorator(context))
}

class RecipeFragment : Fragment() {

    private val binding: FragmentRecipeBinding
            by lazy { FragmentRecipeBinding.inflate(layoutInflater) }

    private val recipe: Recipe by lazy { initRecipe() }

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

            if (getFavorites().contains(recipe.id.toString())) setColoredFavoriteIcon()
            else setUncoloredFavoriteIcon()

            ibIngredientLikeButton.setOnClickListener {
                checkRecipeInFavoriteAndChangeState(getFavorites())
            }

            tvIngredientRecipeHeader.text = recipe.title
            tvPortionText.text =
                "${context?.getString(R.string.title_portion_count)} ${sbPortionCountSeekBar.progress}"

            rvIngredients.addItemDecorationWithoutLastItem()
            rvMethod.addItemDecorationWithoutLastItem()
        }
    }

    private fun setUncoloredFavoriteIcon() =
        binding.ibIngredientLikeButton.setImageResource(R.drawable.ic_heart_empty)

    private fun setColoredFavoriteIcon() =
        binding.ibIngredientLikeButton.setImageResource(R.drawable.ic_heart)

    private fun saveFavorites(collectionRecipeIds: Set<String>?) {
        val sharedPrefs = activity?.getSharedPreferences(
            com.example.recipeapp.PREFERENCE_FILE_KEY,
            Context.MODE_PRIVATE,
        ) ?: return
        with(sharedPrefs.edit()) {
            clear()
            putStringSet(
                PREFERENCE_RECIPE_IDS_SET_KEY,
                collectionRecipeIds,
            )
            apply()
        }
    }

    private fun getFavorites(): MutableSet<String> {
        val sharedPreference = activity?.getSharedPreferences(
            com.example.recipeapp.PREFERENCE_FILE_KEY,
            Context.MODE_PRIVATE,
        )
        return sharedPreference?.getStringSet(
            PREFERENCE_RECIPE_IDS_SET_KEY,
            null,
        ) ?: mutableSetOf()
    }

    private fun checkRecipeInFavoriteAndChangeState(collectionRecipeIds: MutableSet<String>) {
        if (collectionRecipeIds.contains(recipe.id.toString())) {
            val favoriteIdsMinusElement = collectionRecipeIds.minus(recipe.id.toString())
            saveFavorites(favoriteIdsMinusElement)
            setUncoloredFavoriteIcon()
        } else {
            collectionRecipeIds.add(recipe.id.toString())
            saveFavorites(collectionRecipeIds)
            setColoredFavoriteIcon()
        }
    }
}