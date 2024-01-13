package com.example.recipeapp.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recipeapp.ARG_CATEGORY_ID
import com.example.recipeapp.ARG_CATEGORY_IMAGE_URL
import com.example.recipeapp.ARG_CATEGORY_NAME
import com.example.recipeapp.R

class RecipeListFragment : Fragment() {

    private var categoryId: Int? = null
    private var categoryName: String? = null
    private var categoryImageUrl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryId = arguments?.getInt(ARG_CATEGORY_ID)
        categoryName = arguments?.getString(ARG_CATEGORY_NAME)
        categoryImageUrl = arguments?.getString(ARG_CATEGORY_IMAGE_URL)

        Log.d("!!!", "RecipeFragment \ncategoryId: $categoryId, \ncategoryName: $categoryName, \ncategoryImageUrl: $categoryImageUrl")
    }

}