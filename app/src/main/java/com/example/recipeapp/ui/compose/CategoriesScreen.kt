package com.example.recipeapp.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipeapp.R
import com.example.recipeapp.ui.compose.theme.Blue
import com.example.recipeapp.ui.compose.theme.Red
import com.example.recipeapp.ui.compose.theme.StyleMenuHeaderText20
import com.example.recipeapp.ui.compose.theme.StyleTitleText14
import com.example.recipeapp.ui.compose.theme.WhiteBlue

@Preview
@Composable

fun CategoriesScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.bcg_categories),
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
                contentDescription = stringResource(id = R.string.content_description_image),
            )
            Text(
                text = stringResource(id = R.string.title_categories).uppercase(),
                style = StyleMenuHeaderText20,
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
                .background(WhiteBlue)
        ) {
            Text(
                text = "Empty Categories List",
                modifier = Modifier
                    .align(Alignment.Center),
            )

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp)
                    .fillMaxWidth()
            ) {
                // TODO вынести в стиль
                Button(
                    onClick = { },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(Blue),
                    modifier = Modifier
                        .weight(1F)
                        .padding(start = 16.dp, end = 2.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.title_categories).uppercase(),
                        style = StyleTitleText14,
                    )
                }

                Button(
                    onClick = { },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(Red),
                    modifier = Modifier
                        .weight(1F)
                        .padding(start = 2.dp, end = 16.dp)
                ) {
                    Text(
                        stringResource(id = R.string.title_favorite).uppercase(),
                        style = StyleTitleText14
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_heart_empty),
                        contentDescription = stringResource(id = R.string.content_description_image),
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
            }
        }
    }
}