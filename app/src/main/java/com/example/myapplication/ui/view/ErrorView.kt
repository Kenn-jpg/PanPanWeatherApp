package com.example.myapplication.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ErrorView(errorMessage: String?) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Filled.Warning,
            contentDescription = "Warning",
            tint = Color.Red,
            modifier = Modifier.size(40.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            "Oops! Something went wrong.",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ErrorViewPreview(){
    ErrorView(errorMessage = "HTTP 404 Not Found")
}