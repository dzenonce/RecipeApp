package com.example.recipeapp.ui.compose.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.recipeapp.R
import com.example.recipeapp.ui.compose.theme.StyleTitleTextGrey12
import com.example.recipeapp.ui.compose.theme.StyleTitleTextPurple14
import com.example.recipeapp.ui.compose.theme.WhiteBlueColor

@OptIn(ExperimentalGlideComposeApi::class)
@Preview
@Composable
fun CategoriesCard(
    title: String = "Бургеры",
    description: String = "Вкуснейшие",
    imageUrl: String = "burger.png",
) {

    ElevatedCard(
        onClick = { },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .heightIn(min = 230.dp, max = 240.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        colors = CardDefaults.cardColors(containerColor = WhiteBlueColor),
    ) {
        GlideImage(
            model = imageUrl,
            contentDescription = stringResource(id = R.string.content_description_image),
            modifier = Modifier
                .height(130.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
            loading = placeholder(R.drawable.img_placeholder),
        )
        Text(
            text = title.uppercase(),
            style = StyleTitleTextPurple14,
            modifier = Modifier
                .padding(8.dp)
        )
        Text(
            text = description,
            style = StyleTitleTextGrey12,
            modifier = Modifier
                .padding(start = 8.dp, bottom = 8.dp)
        )
    }
}
