package com.example.recipeapp

import STUB
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    ): View? {

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

        openRecipesByCategoryId(categoryListAdapter)
    }

    private fun openRecipesByCategoryId(categoriesAdapter: CategoriesListAdapter) {
        categoriesAdapter.setOnItemClickListener(
            object : CategoriesListAdapter.OnItemClickListener {
                override fun onItemClick() {
                    parentFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<RecipeListFragment>(R.id.frgMainFragmentContainer)
                        addToBackStack(null)
                    }
                }
            }
        )
    }

}