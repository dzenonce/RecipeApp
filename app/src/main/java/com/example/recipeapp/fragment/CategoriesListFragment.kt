package com.example.recipeapp.fragment

import STUB
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.ARG_CATEGORY_ID
import com.example.recipeapp.ARG_CATEGORY_IMAGE_URL
import com.example.recipeapp.ARG_CATEGORY_NAME
import com.example.recipeapp.R
import com.example.recipeapp.adapter.CategoriesListAdapter
import com.example.recipeapp.databinding.FragmentListCategoriesBinding

class CategoriesListFragment : Fragment() {

    private var _binding: FragmentListCategoriesBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Fragment CategoryListBinding must not be null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentListCategoriesBinding.inflate(layoutInflater)

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
            fragment = this
        )
        val recyclerView: RecyclerView = binding.rvCategories
        recyclerView.adapter = categoryListAdapter

        categoryListAdapter.setOnItemClickListener(
            object : CategoriesListAdapter.OnItemClickListener {
                override fun onItemClick(categoryId: Int) {
                    val category = categories[categoryId]
                    val bundle = bundleOf(
                        ARG_CATEGORY_ID to category.id,
                        ARG_CATEGORY_NAME to category.title,
                        ARG_CATEGORY_IMAGE_URL to category.imageUrl,
                    )
                    openRecipesByCategoryId(bundle)
                }
            }
        )
    }

    private fun openRecipesByCategoryId(bundle: Bundle) {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<RecipeListFragment>(R.id.frgMainFragmentContainer, args = bundle)
            addToBackStack(null)
        }
    }
}