package com.example.recipeapp.ui.compose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipeapp.API_RECIPE_IMAGE_URL
import com.example.recipeapp.MAX_LINES_1
import com.example.recipeapp.R
import com.example.recipeapp.model.Category
import com.example.recipeapp.ui.compose.components.CategoryCard
import com.example.recipeapp.ui.compose.theme.PurpleColor
import com.example.recipeapp.ui.compose.theme.StyleMontserratAlternatesPurple20
import com.example.recipeapp.ui.compose.theme.WhiteBlueColor
import com.example.recipeapp.ui.xmlUi.category.CategoriesListViewModel
import com.example.recipeapp.ui.xmlUi.category.CategoriesUiState


@Composable
fun CategoriesScreen(
    onCategoryCardClicked: (Int) -> Unit = { },
    categoriesListViewModel: CategoriesListViewModel,
) {
    val categoryUiState by categoriesListViewModel.categoriesListUiState.observeAsState(CategoriesUiState())
    categoriesListViewModel.loadCategories()
    CategoriesView(
        onCategoryCardClicked = onCategoryCardClicked,
        categoriesList = categoryUiState.categoriesList,
    )
}

@Composable
fun CategoriesView(
    onCategoryCardClicked: (Int) -> Unit,
    categoriesList: List<Category>,
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
                modifier = Modifier
                    .fillMaxWidth()
                    .height(224.dp)
                    .clip(
                        RoundedCornerShape(
                            bottomStart = 10.dp,
                            bottomEnd = 10.dp,
                        )
                    ),
                contentScale = ContentScale.FillWidth,
                contentDescription = stringResource(id = R.string.content_description_image),
            )
            Text(
                text = stringResource(id = R.string.title_categories).uppercase(),
                color = PurpleColor,
                style = StyleMontserratAlternatesPurple20,
                maxLines = MAX_LINES_1,
                overflow = TextOverflow.Ellipsis,
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
            // Categories Cards

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
                items(categoriesList) { category ->
                    CategoryCard(
                        onClick = onCategoryCardClicked,
                        categoryId = category.id,
                        title = category.title,
                        description = category.description,
                        imageUrl = "$API_RECIPE_IMAGE_URL/${category.imageUrl}",
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CategoriesViewPreview() {
    CategoriesView(
        onCategoryCardClicked = { },
        categoriesList = listOf(
            Category(
                0,
                "Десерты",
                "Сладкие",
                "res/drawable/23уцфв"
            )
        ) + List(10) {
            Category(1, "Бургеры", "Вкусные", "res/drawable/burger.png")
        }
    )
}