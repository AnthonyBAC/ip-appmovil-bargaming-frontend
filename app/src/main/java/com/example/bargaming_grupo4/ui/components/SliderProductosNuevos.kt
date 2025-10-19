package com.example.bargaming_grupo4.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.bargaming_grupo4.R

@Composable
fun SliderProductosNuevos(
) {
    val imagenes = listOf(
        R.drawable.ryzen5600,
        R.drawable.i9_14900kf,
        R.drawable.pc_gamer
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        LazyRow(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(imagenes.size) { index ->
                Image(
                    painter = painterResource(imagenes[index]),
                    contentDescription = "${index + 1}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(280.dp)
                        .height(140.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }
        }
    }
}