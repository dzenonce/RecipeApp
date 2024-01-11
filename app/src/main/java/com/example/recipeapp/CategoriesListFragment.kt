package com.example.recipeapp

import STUB
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
        val categoryListAdapter = CategoriesListAdapter(
            dataSet = STUB.getCategories(),
            fragment = this
        )
        val recyclerView: RecyclerView = binding.rvCategories
        recyclerView.adapter = categoryListAdapter

        categoryListAdapter.setOnItemClickListener(
            object : CategoriesListAdapter.OnItemClickListener {
                override fun onItemClick(categoryId: Int) {
                    val bundle = getBundle(
                        categoryListAdapter = categoryListAdapter,
                        categoryId = categoryId
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

    private fun getBundle(categoryListAdapter: CategoriesListAdapter, categoryId: Int): Bundle {
        val categoryName: String = categoryListAdapter.dataSet[categoryId].title
        val categoryImageUrl: String = categoryListAdapter.dataSet[categoryId].imageUrl
        Log.d("!!!", "Выбраны рецепты: $categoryName")
        return bundleOf(
            ARG_CATEGORY_ID to categoryId,
            ARG_CATEGORY_NAME to categoryName,
            ARG_CATEGORY_IMAGE_URL to categoryImageUrl,
        )
    }
}

const val ARG_CATEGORY_ID: String = "category_id"
const val ARG_CATEGORY_NAME: String = "category_name"
const val ARG_CATEGORY_IMAGE_URL: String = "category_image_url"