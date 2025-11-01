package com.example.myapplication.ui.view

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun WeatherCard(
    title: String,
    value: String,
    @DrawableRes iconRes: Int,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterVertically),
        modifier = Modifier
            .size(120.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.Gray)
            .padding(12.dp)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = "Icon",
            modifier = Modifier.size(20.dp)
        )

        Text(
            title,
            fontSize = 12.sp,
            color = Color.Gray
        )

        Text(
            value,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun WeatherCardPreview() {
    WeatherCard(
        title = "CLOUDS",
        value = "8%",
        iconRes = R.drawable.cloud_icon
    )
}