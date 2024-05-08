package com.example.recipeapp.ui.compose.screens


import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipeapp.API_RECIPE_IMAGE_URL
import com.example.recipeapp.MAX_LINES_1
import com.example.recipeapp.R
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.ui.compose.components.NavButton
import com.example.recipeapp.ui.compose.components.RecipeCard
import com.example.recipeapp.ui.compose.navigation.Screen
import com.example.recipeapp.ui.compose.theme.PurpleColor
import com.example.recipeapp.ui.compose.theme.StyleMenuHeaderTextPurple20
import com.example.recipeapp.ui.compose.theme.WhiteBlueColor
import com.example.recipeapp.ui.xmlUi.recipes.favorites.FavoritesUiState
import com.example.recipeapp.ui.xmlUi.recipes.favorites.FavoritesViewModel

@Composable
fun FavoriteScreen(
    navigateTo: (Screen) -> Unit,
    favoritesViewModel: FavoritesViewModel
) {
    val favoritesUiState: FavoritesUiState by favoritesViewModel.uiState.observeAsState(
        FavoritesUiState()
    )
    favoritesViewModel.loadFavorites()

    FavoriteView(
        navigateTo = navigateTo,
        favoritesRecipesList = favoritesUiState.favoritesRecipesList,
    )
}

@Composable
fun FavoriteView(
    navigateTo: (Screen) -> Unit,
    favoritesRecipesList: List<Recipe>,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Image(
                painter = painterResource(id = R.drawable.bcg_categories),
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
            )
            Text(
                text = stringResource(id = R.string.title_favorite).uppercase(),
                color = PurpleColor,
                style = StyleMenuHeaderTextPurple20,
                maxLines = MAX_LINES_1,
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
                columns = GridCells.Fixed(1),
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

                items(favoritesRecipesList) { recipe ->
                    RecipeCard(
                        title = recipe.title,
                        imageUrl = "$API_RECIPE_IMAGE_URL/${recipe.imageUrl}",
                    )
                }
            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            ) { NavButton(navigateTo) }
        }
    }
}

@Preview
@Composable
fun FavoriteViewPreview() {
    val recipesList = List(10) {
        Recipe(
            1,
            1,
            false,
            "Бургеры",
            emptyList(),
            emptyList(),
            "burger.png",
        )
    }

    FavoriteView(
        navigateTo = { },
        favoritesRecipesList = recipesList,
    )
}