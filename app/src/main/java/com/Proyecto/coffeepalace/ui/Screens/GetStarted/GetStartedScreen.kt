package com.Proyecto.coffeepalace.ui.Screens.GetStarted

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.Proyecto.coffeepalace.R
import com.Proyecto.coffeepalace.ui.components.GetStartedLogo
import com.Proyecto.coffeepalace.ui.theme.Brown
import com.Proyecto.coffeepalace.ui.theme.LightGray

@Composable
fun GetStartedScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(LightGray)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GetStartedLogo(
            image = painterResource(R.drawable.logo)
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Text(
            text = "Your coffee, your",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                letterSpacing = 0.5.sp
            ),
            color = Brown,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(6.dp)
        )
        Text(
            text = "style, let's go for",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 32.sp,
                letterSpacing = 0.5.sp
            ),
            color = Brown,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(6.dp)
        )
        Text(
            text = "that energy!",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 24.sp,
                letterSpacing = 0.5.sp
            ),
            color = Brown,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(8.dp)
        )
    }
}