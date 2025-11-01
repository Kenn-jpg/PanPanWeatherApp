package com.example.myapplication.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.ui.viewmodel.WeatherViewModel
import kotlin.math.roundToInt
import kotlinx.coroutines.launch

@Composable
fun MainView(modifier: Modifier = Modifier) {
    var inputCityName by remember { mutableStateOf("") }
    val viewModel: WeatherViewModel = viewModel()
    val weatherState by viewModel.weather.collectAsState()
    val weatherIconUrl by viewModel.weatherIconUrl.collectAsState()
    val dateFormatted by viewModel.dateFormatted.collectAsState()
    val timeFormatted by viewModel.timeFormatted.collectAsState()
    val weatherDetails by viewModel.weatherDetails.collectAsState()
    val sunriseTime by viewModel.sunriseTime.collectAsState()
    val sunsetTime by viewModel.sunsetTime.collectAsState()

    val keyboardController = LocalSoftwareKeyboardController.current
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.background),
                contentScale = ContentScale.Crop
            )
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        ) {
            OutlinedTextField(
                value = inputCityName,
                onValueChange = { inputCityName = it },
                modifier = Modifier
                    .weight(1f),
                shape = RoundedCornerShape(16.dp),
                placeholder = { Text("Enter city name...", color = Color.Gray) },

                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        tint = Color.Gray
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.Gray,
                    cursorColor = Color.Gray,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        if (inputCityName.isNotBlank()) {
                            viewModel.loadWeather(inputCityName)
                            coroutineScope.launch {
                                listState.animateScrollToItem(0)
                            }
                            keyboardController?.hide()
                        }
                    }
                )

            )

            Spacer(modifier = Modifier.padding(horizontal = 4.dp))

            Button(
                onClick = {
                    if (inputCityName.isNotBlank()) {
                        viewModel.loadWeather(inputCityName)
                        coroutineScope.launch{
                            listState.animateScrollToItem(0)
                        }
                        keyboardController?.hide()
                    }
                },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White.copy(alpha = 0.10f)
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = Color.White.copy(alpha = 0.25f)
                ),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 16.dp)

            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    "Search"
                )
            }
        }

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item {
                if (weatherState.errorMessage != null) {
                    ErrorView(weatherState.errorMessage)
                } else if (weatherState.cityName.isBlank()) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search",
                            tint = Color.Gray,
                            modifier = Modifier.size(60.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            "Search for a city to get started",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.Gray
                        )
                    }
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(
                            80.dp,
                            alignment = Alignment.CenterVertically
                        ),
                        modifier = Modifier
                            .padding(20.dp)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(
                                    8.dp,
                                    alignment = Alignment.CenterHorizontally
                                ),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.LocationOn,
                                    contentDescription = "Location",
                                    tint = Color.White
                                )

                                Text(
                                    weatherState.cityName,
                                    fontSize = 24.sp,
                                    color = Color.White
                                )
                            }
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(
                                    8.dp,
                                    alignment = Alignment.CenterVertically
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    dateFormatted,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 28.sp,
                                    color = Color.White
                                )
                                Text(
                                    "Updated as of $timeFormatted",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    color = Color.Gray
                                )
                            }
                        }

                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(
                                    8.dp,
                                    alignment = Alignment.CenterVertically
                                )
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(weatherIconUrl),
                                    contentDescription = "Icon",
                                    modifier = Modifier.size(40.dp)
                                )
                                Text(
                                    weatherState.weatherCondition,
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    "${weatherState.weatherTemperature.roundToInt()}°C",
                                    fontSize = 40.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White

                                )
                            }

                            Image(
                                painter = painterResource(
                                    when (weatherState.weatherCondition.lowercase()) {
                                        "clear" -> R.drawable.panda_clear_icon
                                        "rain" -> R.drawable.panda_rain_icon
                                        else -> R.drawable.panda_cloud_icon
                                    }
                                ),
                                contentDescription = "Icon",
                                modifier = Modifier
                                    .size(120.dp)
                            )
                        }

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3),
                            horizontalArrangement = Arrangement.spacedBy(20.dp),
                            verticalArrangement = Arrangement.spacedBy(20.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp),
                            userScrollEnabled = false
                        ) {
                            items(weatherDetails.size) { index ->
                                val detail = weatherDetails[index]
                                WeatherCard(
                                    title = detail.first,
                                    value = detail.second,
                                    iconRes = detail.third
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            SunCard(
                                title = "SUNRISE",
                                value = sunriseTime,
                                iconRes = R.drawable.sunrise_icon
                            )
                            SunCard(
                                title = "SUNSET",
                                value = sunsetTime,
                                iconRes = R.drawable.sunset_icon
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun MainViewPreview() {
    MainView()
}