package com.example.recipeapp.ui.category

import com.example.recipeapp.data.STUB
import android.os.Bundle
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
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentListCategoriesBinding
import com.example.recipeapp.model.Category
import com.example.recipeapp.ui.recipes.recipesList.RecipesListFragment

class CategoriesListFragment : Fragment() {

    private val binding: FragmentListCategoriesBinding by lazy {
        FragmentListCategoriesBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    private fun initRecycler() {
        val categories = STUB.getCategories()
        val categoryListAdapter = CategoriesListAdapter(
            dataSet = categories,
        )
        val recyclerView: RecyclerView = binding.rvCategories
        recyclerView.adapter = categoryListAdapter

        categoryListAdapter.setOnItemClickListener(
            object : CategoriesListAdapter.OnItemClickListener {
                override fun onItemClick(categoryId: Int) {
                    openRecipesByCategoryId(
                        getBundle(
                            categories = categories,
                            categoryId = categoryId,
                        )
                    )
                }
            }
        )
    }

    private fun openRecipesByCategoryId(bundle: Bundle) {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<RecipesListFragment>(R.id.frgMainFragmentContainer, args = bundle)
            addToBackStack(null)
        }
    }

    private fun getBundle(categories: List<Category>, categoryId: Int) =
        categories.find { it.id == categoryId }.let { category ->
            bundleOf(
                ARG_CATEGORY_ID to category?.id,
                ARG_CATEGORY_NAME to category?.title,
                ARG_CATEGORY_IMAGE_URL to category?.imageUrl,
            )
        }

}