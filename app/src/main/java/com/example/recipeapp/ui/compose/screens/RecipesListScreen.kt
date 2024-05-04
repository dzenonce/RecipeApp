package com.example.recipeapp.ui.compose.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.recipeapp.API_RECIPE_IMAGE_URL
import com.example.recipeapp.R
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.ui.compose.components.NavButton
import com.example.recipeapp.ui.compose.components.RecipeCard
import com.example.recipeapp.ui.compose.theme.PurpleColor
import com.example.recipeapp.ui.compose.theme.StyleMenuHeaderTextPurple20
import com.example.recipeapp.ui.compose.theme.WhiteBlueColor
import com.example.recipeapp.ui.xmlUi.recipes.recipesList.RecipesListUiState
import com.example.recipeapp.ui.xmlUi.recipes.recipesList.RecipesListViewModel

@Composable
fun RecipesListScreen(
    recipesListViewModel: RecipesListViewModel
) {
    val recipesUiState by recipesListViewModel.uiState.observeAsState(RecipesListUiState())
    RecipesListView(
        recipesList = recipesUiState.recipeList,
        categoryName = recipesUiState.categoryName,
        categoryImageUrl = recipesUiState.categoryImageUrl
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RecipesListView(
    recipesList: List<Recipe>,
    categoryName: String,
    categoryImageUrl: String,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            GlideImage(
                model = "$API_RECIPE_IMAGE_URL/$categoryImageUrl",
                contentDescription = stringResource(id = R.string.content_description_image),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(WhiteBlueColor)
                    .height(224.dp)
                    .clip(
                        RoundedCornerShape(
                            bottomStart = 10.dp,
                            bottomEnd = 10.dp,
                        )
                    ),
                contentScale = ContentScale.FillWidth,
                loading = placeholder(R.drawable.img_placeholder),
                failure = placeholder(R.drawable.img_error)
            )
            Text(
                text = categoryName.uppercase(),
                color = PurpleColor,
                style = StyleMenuHeaderTextPurple20,
                modifier = Modifier
                    .padding(16.dp)
                    .background(
                        color = colorResource(id = R.color.white_blue_color),
                        shape = RoundedCornerShape(
                            dimensionResource(id = R.dimen.margin_normal_8),
                        )
                    )
                    .padding(10.dp)
                    .align(Alignment.BottomStart)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(WhiteBlueColor)
        ) {
            // Recipe Cards

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(
                    start = 8.dp,
                    top = 8.dp,
                    end = 8.dp,
                    bottom = 52.dp
                ),
                modifier = Modifier
                    .background(WhiteBlueColor)
                    .fillMaxSize()
            ) {

                items(recipesList) { recipe ->
                    RecipeCard(
                        title = recipe.title,
                        imageUrl = "$API_RECIPE_IMAGE_URL/${recipe.imageUrl}",
                    )
                }
            }

            Row(
                modifier = Modifier.align(Alignment.BottomCenter)
            ) { NavButton() }
        }
    }
}

@Preview
@Composable
fun RecipesListViewPreview() {
    RecipesListView(
        recipesList = List(10) {
            Recipe(
                1,
                1,
                false,
                "123",
                emptyList(),
                emptyList(),
                "burger.png",
            )
        },
        categoryName = "Бургеры",
        categoryImageUrl = "burgers.png"
    )
}