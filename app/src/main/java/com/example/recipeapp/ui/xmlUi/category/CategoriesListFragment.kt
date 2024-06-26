package com.example.recipeapp.ui.xmlUi.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.databinding.FragmentListCategoriesBinding
import com.example.recipeapp.model.Category
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesListFragment : Fragment() {

    private val binding: FragmentListCategoriesBinding by lazy {
        FragmentListCategoriesBinding.inflate(layoutInflater)
    }
    private val navController by lazy { this.findNavController() }

    private val categoriesListViewModel: CategoriesListViewModel by viewModels()
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
        categoriesListViewModel.uiState.observe(viewLifecycleOwner) { categoriesListState ->

            val recyclerView: RecyclerView = binding.rvCategories
            categoryListAdapter.dataSet = categoriesListState.categoriesList
            recyclerView.adapter = categoryListAdapter

            categoryListAdapter.setOnItemClickListener(
                object : CategoriesListAdapter.OnItemClickListener {
                    override fun onItemClick(categoryId: Int) {
                        openRecipesByCategoryId(
                            categoryList = categoriesListState.categoriesList,
                            categoryId = categoryId,
                        )
                    }
                }
            )
        }
        categoriesListViewModel.loadCategories()
    }

    private fun openRecipesByCategoryId(categoryList: List<Category>, categoryId: Int) =
        navController.navigate(
            CategoriesListFragmentDirections
                .actionCategoriesListFragmentToRecipesListFragment(
                    try {
                        categoryList.find { it.id == categoryId }
                    } catch (e: Error) {
                        throw IllegalArgumentException("openRecipesByCategoryId must not have empty category")
                    }
                )
        )

}
