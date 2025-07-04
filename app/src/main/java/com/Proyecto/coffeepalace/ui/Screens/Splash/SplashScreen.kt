package com.Proyecto.coffeepalace.ui.Screens.Splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.Proyecto.coffeepalace.ui.navigations.Screens
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    var showLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(2000)
        showLoading = true
        delay(1500)

        navController.navigate(Screens.Home.route) {
            popUpTo("splash_screen") { inclusive = true }
        }
    }

    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data("file:///android_asset/CooffeLogo2.png")
            .build()
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painter,
                contentDescription = "Logo",
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))

            if (showLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(50.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}