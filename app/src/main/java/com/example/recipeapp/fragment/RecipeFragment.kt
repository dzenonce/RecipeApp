package com.example.recipeapp.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recipeapp.ARG_RECIPE
import com.example.recipeapp.databinding.FragmentRecipeBinding
import com.example.recipeapp.model.Recipe

class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipeBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Recipe Fragment Binding must not be null")

    private var recipe: Recipe? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            recipe =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                    it.getParcelable(ARG_RECIPE, Recipe::class.java)
                else
                    it.getParcelable(ARG_RECIPE)
        }
        with(binding) {
            this.tvRecipeIdentifier.text = recipe?.title
        }
    }

}