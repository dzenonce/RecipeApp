package com.example.recipeapp.ui.compose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.material.icons.twotone.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.recipeapp.API_RECIPE_IMAGE_URL
import com.example.recipeapp.R
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.ui.compose.components.NavButton
import com.example.recipeapp.ui.compose.navigation.Screen
import com.example.recipeapp.ui.compose.theme.BlueColor
import com.example.recipeapp.ui.compose.theme.LightStateBlueColor
import com.example.recipeapp.ui.compose.theme.PurpleColor
import com.example.recipeapp.ui.compose.theme.StyleMontserratAlternatesPurple20
import com.example.recipeapp.ui.compose.theme.StyleMontserratLiteGrey16
import com.example.recipeapp.ui.xmlUi.recipes.recipe.RecipeUiState
import com.example.recipeapp.ui.xmlUi.recipes.recipe.RecipeViewModel

@Composable
fun RecipeScreen(
    navigateTo: (Screen) -> Unit,
    recipeId: Int,
    recipeViewModel: RecipeViewModel,
) {
    val recipeUiState by recipeViewModel.uiState.observeAsState(RecipeUiState())
    recipeViewModel.loadRecipe(recipeId)
    RecipeScreenView(
        navigateTo = navigateTo,
        recipe = recipeUiState.recipe,
        isFavorite = recipeUiState.isFavorite,
        imageUrl = recipeUiState.recipeImageUrl,
    )
}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreenView(
    navigateTo: (Screen) -> Unit,
    recipe: Recipe?,
    isFavorite: Boolean,
    imageUrl: String,
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
                model = "$API_RECIPE_IMAGE_URL/$imageUrl",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(224.dp)
                    .clip(
                        RoundedCornerShape(
                            bottomStart = 10.dp,
                            bottomEnd = 10.dp,
                        )
                    ),
                contentDescription = stringResource(id = R.string.content_description_image),
                contentScale = ContentScale.FillWidth,
                loading = placeholder(R.drawable.img_placeholder),
                failure = placeholder(R.drawable.img_error),
                )
            Text(
                text = "${recipe?.title}",
                color = PurpleColor,
                style = StyleMontserratAlternatesPurple20,
                maxLines = 1,
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
            IconButton(
                onClick = { /*TODO ADD To Favorite*/ },
                modifier = Modifier
                    .padding(end = 16.dp, top = 16.dp)
                    .align(Alignment.TopEnd)
            ) {
                Image(
//                    imageVector = Icons.TwoTone.FavoriteBorder,
                    painter = painterResource(id = R.drawable.ic_heart),
                    contentDescription = stringResource(id = R.string.content_description_image),
//                    tint = Color.White,
                    modifier = Modifier
                        .height(40.dp)
                        .width(40.dp)
                )
            }
        }

        // Ingredients // counter подключаемый
        Column(
            verticalArrangement = Arrangement.Top
        ) {

            var sliderPosition by remember { mutableStateOf(1f) }

            Text(
                style = StyleMontserratAlternatesPurple20,
                text = stringResource(id = R.string.title_ingredients)
            )

            Text(
                style = StyleMontserratLiteGrey16,
                text = "${stringResource(id = R.string.title_portion_count)} ${sliderPosition.toInt()}"
            )
            Slider(
                thumb = {
                    Image(
                        image = painterResource(id = R.drawable.ic_launcher_background),"contentDescription")
                },
                onValueChange = { sliderPosition = it },

            )
//            Slider(
//                value = sliderPosition,
//                onValueChange = { sliderPosition = it },
//                valueRange = 1f..5f,
//                thumb = SliderDefaults.Thumb(),
//                colors = SliderDefaults.colors(
//                    thumbColor = BlueColor,
//                    inactiveTickColor = LightStateBlueColor,
//                    activeTrackColor = LightStateBlueColor,
//                    disabledThumbColor = LightStateBlueColor,
//                ),

            )
            // List
//            LazyColumn {
//                // Add a single item
//                item {
//                    Text(text = "First item")
//                }
//
//                // Add 5 items
//                items(5) { index ->
//                    Text(text = "Item: $index")
//                }
//
//                // Add another single item
//                item {
//                    Text(text = "Last item")
//                }
//            }

        }

        // Cook method
        Column {

        }

        Row(
            modifier = Modifier
//                .align(Alignment.BottomCenter)
        ) {
            NavButton(navigateTo = navigateTo)
        }
    }
}


@Composable
@Preview
fun RecipeScreenViewPreview() {
    RecipeScreenView(
        navigateTo = { },
        recipe = Recipe(
            id = 1,
            categoryId = 1,
            isFavorite = false,
            title = "Чизбургер",
            ingredients = emptyList(),
            method = emptyList(),
            imageUrl = "burger.png",
        ),
        isFavorite = false,
        imageUrl = "burger.png",
    )
}