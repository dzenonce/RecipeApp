package com.example.recipeapp.ui.compose.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.example.recipeapp.MAX_LINES_1
import com.example.recipeapp.MAX_LINES_3
import com.example.recipeapp.R
import com.example.recipeapp.model.Ingredient
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.ui.compose.theme.BlueColor
import com.example.recipeapp.ui.compose.theme.LightStateBlueColor
import com.example.recipeapp.ui.compose.theme.LightWhiteGreyColor
import com.example.recipeapp.ui.compose.theme.PurpleColor
import com.example.recipeapp.ui.compose.theme.StyleMontserratAlternatesDarkBurgundy20
import com.example.recipeapp.ui.compose.theme.StyleMontserratAlternatesPurple20
import com.example.recipeapp.ui.compose.theme.StyleMontserratGrey14
import com.example.recipeapp.ui.compose.theme.StyleMontserratLiteGrey16
import com.example.recipeapp.ui.compose.theme.WhiteBlueColor
import com.example.recipeapp.ui.xmlUi.recipes.recipe.RecipeUiState
import com.example.recipeapp.ui.xmlUi.recipes.recipe.RecipeViewModel

@Composable
fun RecipeScreen(
    onFavoriteButtonClicked: (Boolean) -> Unit,
    recipeId: Int,
    recipeViewModel: RecipeViewModel,
) {
    val recipeUiState by recipeViewModel.uiState.observeAsState(RecipeUiState())
    recipeViewModel.loadRecipe(recipeId)

    RecipeScreenView(
        onClick = onFavoriteButtonClicked,
        recipe = recipeUiState.recipe,
        isFavorite = recipeUiState.isFavorite,
        imageUrl = recipeUiState.recipeImageUrl,
    )
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreenView(
    onClick: (Boolean) -> Unit,
    recipe: Recipe?,
    isFavorite: Boolean,
    imageUrl: String,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .background(WhiteBlueColor)
            .verticalScroll(scrollState)
            .padding(bottom = 58.dp)
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            GlideImage(
                model = imageUrl,
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

            val isFavoriteState = remember { mutableStateOf(isFavorite) }

            IconButton(
                onClick = {
                    isFavoriteState.value = !isFavoriteState.value
                    onClick(!isFavorite)
                },
                modifier = Modifier
                    .padding(end = 16.dp, top = 16.dp)
                    .align(Alignment.TopEnd)
            ) {
                if (isFavoriteState.value)
                    Image(
                        painter = painterResource(id = R.drawable.ic_heart),
                        contentDescription = stringResource(id = R.string.content_description_image),
                        modifier = Modifier
                            .height(40.dp)
                            .width(40.dp)
                    )
                else
                    Image(
                        painter = painterResource(id = R.drawable.ic_heart_empty),
                        contentDescription = stringResource(id = R.string.content_description_image),
                        modifier = Modifier
                            .height(40.dp)
                            .width(40.dp)
                    )
            }
        }

        Text(
            style = StyleMontserratAlternatesDarkBurgundy20,
            text = stringResource(id = R.string.title_ingredients).uppercase(),
            modifier = Modifier
                .padding(
                    bottom = dimensionResource(id = R.dimen.margin_small_6),
                    top = dimensionResource(id = R.dimen.margin_normal_16),
                    start = dimensionResource(id = R.dimen.margin_normal_16),
                    end = dimensionResource(id = R.dimen.margin_normal_16),
                )
        )
        Column(
            modifier = Modifier
                .padding(
                ),
            verticalArrangement = Arrangement.Top
        ) {

            var sliderPosition by remember { mutableFloatStateOf(1f) }
            Text(
                style = StyleMontserratLiteGrey16,
                text = "${stringResource(id = R.string.title_portion_count)} ${sliderPosition.toInt()}",
                modifier = Modifier
                    .padding(
                        start = dimensionResource(id = R.dimen.margin_normal_16),
                        end = dimensionResource(id = R.dimen.margin_normal_16),
                        bottom = dimensionResource(id = R.dimen.margin_small_6),
                    )
            )

            Slider(
                value = sliderPosition,
                onValueChange = { sliderPosition = it },
                valueRange = 1f..5f,
                modifier = Modifier
                    .padding(
                        bottom = dimensionResource(id = R.dimen.margin_normal_8),
                    )
                    .fillMaxWidth(),
                track = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.margin_small_4)))
                            .background(LightStateBlueColor)
                            .height(16.dp)
                    )
                },
                thumb = {
                    Box(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp)
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.margin_small_2)))
                            .background(BlueColor)
                            .height(dimensionResource(id = R.dimen.size_thumb_30))
                            .width(dimensionResource(id = R.dimen.size_thumb_8))
                    )
                },
            )

            // Ingredients
            Column(
                modifier = Modifier
                    .padding(
                        bottom = dimensionResource(id = R.dimen.margin_normal_16),
                        top = dimensionResource(id = R.dimen.margin_small_4),
                        start = dimensionResource(id = R.dimen.margin_normal_16),
                        end = dimensionResource(id = R.dimen.margin_normal_16),
                    )
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.margin_normal_8)))
                    .background(Color.White)
            ) {
                recipe?.ingredients?.forEach { ingredient ->
                    Row {
                        // TODO Style, padding для текста от линий
                        Text(
                            style = StyleMontserratGrey14,
                            text = ingredient.description.uppercase(),
                            maxLines = MAX_LINES_3,
                            modifier = Modifier
                                .padding(
                                    start = dimensionResource(id = R.dimen.margin_normal_12),
                                    top = dimensionResource(id = R.dimen.margin_normal_8),
                                    bottom = dimensionResource(id = R.dimen.margin_normal_8),
                                )
                                .weight(1f)
                                .wrapContentWidth(Alignment.Start)
                        )
                        val quantity = getCurrentIngredientQuantity(
                            quantity = ingredient.quantity,
                            position = sliderPosition.toInt(),
                        )
                        Text(
                            style = StyleMontserratGrey14,
                            text = "$quantity ${ingredient.unitOfMeasure}".uppercase(),
                            maxLines = MAX_LINES_3,
                            modifier = Modifier
                                .padding(
                                    end = dimensionResource(id = R.dimen.margin_normal_12),
                                    top = dimensionResource(id = R.dimen.margin_normal_8),
                                    bottom = dimensionResource(id = R.dimen.margin_normal_8),
                                )
                                .weight(1f)
                                .wrapContentWidth(Alignment.End)
                        )
                    }
                    HorizontalDivider(
                        modifier = Modifier
                            .padding(horizontal = dimensionResource(id = R.dimen.margin_normal_12)),
                        color = LightWhiteGreyColor,
                        thickness = 1.dp,
                    )
                }
            }

            // Method
            Column {
                Text(
                    style = StyleMontserratAlternatesDarkBurgundy20,
                    text = stringResource(id = R.string.title_cooking_method).uppercase(),
                    modifier = Modifier
                        .padding(
                            bottom = dimensionResource(id = R.dimen.margin_normal_16),
                            start = dimensionResource(id = R.dimen.margin_normal_16)
                        ),
                )
                Column(
                    modifier = Modifier
                        .padding(
                            start = dimensionResource(id = R.dimen.margin_normal_16),
                            end = dimensionResource(id = R.dimen.margin_normal_16),
                        )
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.margin_normal_8)))
                        .background(Color.White)
                        .fillMaxWidth()
                ) {
                    recipe?.method?.forEachIndexed { index, method ->
                        Row {
                            Text(
                                style = StyleMontserratGrey14,
                                text = "${index + 1}. $method",
                                maxLines = 5,
                                modifier = Modifier
                                    .padding(
                                        start = dimensionResource(id = R.dimen.margin_normal_12),
                                        end = dimensionResource(id = R.dimen.margin_normal_12),
                                        top = dimensionResource(id = R.dimen.margin_normal_8),
                                        bottom = dimensionResource(id = R.dimen.margin_normal_8),
                                    )
                            )
                        }
                        HorizontalDivider(
                            modifier = Modifier
                                .padding(horizontal = dimensionResource(id = R.dimen.margin_normal_12)),
                            color = LightWhiteGreyColor,
                            thickness = 1.dp,
                        )
                    }
                }
            }
        }

    }
}

@Composable
@Preview
fun RecipeScreenViewPreview() {
    RecipeScreenView(
        onClick = { },
        recipe = Recipe(
            id = 1,
            categoryId = 1,
            isFavorite = false,
            title = "Чизбургер",
            ingredients = listOf(
                Ingredient(
                    quantity = "10",
                    unitOfMeasure = "грамм",
                    description = "Помидор",
                ),
                Ingredient(
                    quantity = "10",
                    unitOfMeasure = "грамм",
                    description = "Помидор",
                ),
                Ingredient(
                    quantity = "10",
                    unitOfMeasure = "грамм",
                    description = "Помидор",
                ),
                Ingredient(
                    quantity = "10",
                    unitOfMeasure = "грамм",
                    description = "Помидор",
                ),
            ),
            method = listOf(
                "asd", "asd12e", "asd", "asd12e", "asd", "asd12e",
            ),
            imageUrl = "burger.png",
        ),
        isFavorite = false,
        imageUrl = "burger.png",
    )
}

private fun getCurrentIngredientQuantity(quantity: String, position: Int): String {
    if (quantity.toDoubleOrNull() == null) return quantity
    quantity.toDouble().let {
        val currentQuantity = it * position
        return if (currentQuantity % 1 == 0.0) currentQuantity.toInt().toString()
        else String.format("%.1f", currentQuantity)
    }
}
