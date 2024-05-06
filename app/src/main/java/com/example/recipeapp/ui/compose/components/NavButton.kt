package com.example.recipeapp.ui.compose.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.recipeapp.R
import com.example.recipeapp.ui.compose.screens.Screen
import com.example.recipeapp.ui.compose.screens.ScreenViewModel
import com.example.recipeapp.ui.compose.theme.BlueColor
import com.example.recipeapp.ui.compose.theme.RedColor
import com.example.recipeapp.ui.compose.theme.StyleTitleTextWhite14

@Composable
fun NavButton(
//    screenViewModel: ScreenViewModel
) {

    Row(
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth()
    ) {

        // TODO вынести в стиль (отдельный компонент)
        Button(
            onClick = { },//screenViewModel.navigateTo(Screen.RecipesList) },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(BlueColor),
            modifier = Modifier
                .weight(1F)
                .padding(start = 16.dp, end = 2.dp)
        ) {
            Text(
                text = stringResource(id = R.string.title_categories).uppercase(),
                style = StyleTitleTextWhite14,
            )
        }

        Button(
            onClick = { },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(RedColor),
            modifier = Modifier
                .weight(1F)
                .padding(start = 2.dp, end = 16.dp)
        ) {
            Text(
                stringResource(id = R.string.title_favorite).uppercase(),
                style = StyleTitleTextWhite14
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_heart_empty),
                contentDescription = stringResource(id = R.string.content_description_image),
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }

}
