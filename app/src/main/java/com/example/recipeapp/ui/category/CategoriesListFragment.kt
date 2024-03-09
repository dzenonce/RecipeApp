package com.example.recipeapp.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentListCategoriesBinding
import com.example.recipeapp.ui.ARG_CATEGORY_ID
import com.example.recipeapp.ui.SCREEN_CATEGORIES
import com.example.recipeapp.ui.recipes.recipesList.RecipesListFragment

class CategoriesListFragment : Fragment() {

    private val binding: FragmentListCategoriesBinding by lazy {
        FragmentListCategoriesBinding.inflate(layoutInflater)
    }

    private val categoriesViewModel: CategoriesViewModel by viewModels()
    private val categoryListAdapter = CategoriesListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        categoriesViewModel.uiState.observe(viewLifecycleOwner) { categoriesListState ->
            with(binding) {
                // TODO contentDescription
                ivCategoriesScreenHeader.setImageDrawable(categoriesListState.screenHeaderImage)

                val recyclerView: RecyclerView = rvCategories
                categoryListAdapter.dataSet = categoriesListState.categoriesList
                recyclerView.adapter = categoryListAdapter

                categoryListAdapter.setOnItemClickListener(
                    object : CategoriesListAdapter.OnItemClickListener {
                        override fun onItemClick(categoryId: Int) {
                            openRecipesByCategoryId(getBundle(categoryId))
                        }
                    }
                )
            }
        }
        categoriesViewModel.loadCategories(SCREEN_CATEGORIES)
    }

    private fun openRecipesByCategoryId(bundle: Bundle) {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<RecipesListFragment>(R.id.frgMainFragmentContainer, args = bundle)
            addToBackStack(null)
        }
    }

    private fun getBundle(categoryId: Int) = bundleOf(ARG_CATEGORY_ID to categoryId)

}