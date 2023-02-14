package com.example.recipeapp.ui.recipes.recipesList

import com.example.recipeapp.data.STUB
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.ui.ARG_CATEGORY_ID
import com.example.recipeapp.ui.ARG_CATEGORY_IMAGE_URL
import com.example.recipeapp.ui.ARG_CATEGORY_NAME
import com.example.recipeapp.ui.ARG_RECIPE
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentRecipesListBinding
import com.example.recipeapp.ui.recipes.recipe.RecipeFragment
import java.io.InputStream

class RecipesListFragment : Fragment() {

    private val binding: FragmentRecipesListBinding by lazy {
        FragmentRecipesListBinding.inflate(layoutInflater)
    }

    private var categoryId: Int? = null
    private var categoryName: String? = null
    private var categoryImageUrl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            categoryId = it.getInt(ARG_CATEGORY_ID)
            categoryName = it.getString(ARG_CATEGORY_NAME)
            categoryImageUrl = it.getString(ARG_CATEGORY_IMAGE_URL)
        }
        Log.d(
            "!!!",
            "RecipeFragment \ncategoryId: $categoryId, \ncategoryName: $categoryName, \ncategoryImageUrl: $categoryImageUrl"
        )

        initRecipeListScreenHeader()
        initRecycler()
    }

    private fun initRecipeListScreenHeader() {
        try {
            val inputStream: InputStream? = this.context?.assets?.open(categoryImageUrl.toString())
            val drawable: Drawable? = Drawable.createFromStream(inputStream, null)
            binding.ivRecipesListImage.setImageDrawable(drawable)
        } catch (e: Error) {
            Log.e("assets error", e.stackTraceToString())
        }
        binding.tvRecipesListHeaderName.text = categoryName
    }

    private fun initRecycler() {
        val recipeList = STUB.getRecipesByCategoryId(categoryId)
        val recipeListAdapter = RecipesListAdapter(
            dataSet = recipeList,
        )
        val recyclerView: RecyclerView = binding.rvRecipes
        recyclerView.adapter = recipeListAdapter

        recipeListAdapter.setOnRecipeClickListener(
            object : RecipesListAdapter.OnRecipeClickListener {
                override fun onRecipeClick(recipeId: Int) {
                    openRecipeByRecipeId(getBundle(recipeId))
                }
            }
        )
    }

    private fun openRecipeByRecipeId(bundle: Bundle) {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<RecipeFragment>(R.id.frgMainFragmentContainer, args = bundle)
            addToBackStack(null)
        }
    }

    private fun getBundle(recipeId: Int) =
        STUB.getRecipeByRecipeId(recipeId).let { recipe ->
            bundleOf().apply { this.putParcelable(ARG_RECIPE, recipe) }
        }

}