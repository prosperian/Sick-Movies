package com.dip.sickmovies.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NowPlaying() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Yellow),
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier.padding(1.dp),
                elevation = 16.dp,
                shape = CircleShape
            ) {
                Image(
                    modifier = Modifier.padding(25.dp),
                    imageVector = Icons.Default.Person,
                    contentDescription = "Up Coming",
                    colorFilter = ColorFilter.lighting(
                        Color.Blue, add = Color.Blue
                    )
                )
            }
            Spacer(modifier = Modifier.padding(6.dp))
            Text(
                text = "Up Coming Screen",
                fontSize = 22.sp,
                color = Color.Black
            )

        }
    }
}