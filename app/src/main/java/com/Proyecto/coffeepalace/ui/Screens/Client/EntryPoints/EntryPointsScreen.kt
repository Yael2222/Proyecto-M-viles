package com.Proyecto.coffeepalace.ui.Screens.Client.EntryPoints

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext


@Composable
fun EntryPointsScreen(
    modifier: Modifier = Modifier,
    isFirstLaunch: Boolean = false,
    navigateToGetStarted: () -> Unit = {},
    navigateToHomePage: () -> Unit = {}
) {
    val context = LocalContext.current
    var showOnboarding by remember { mutableStateOf(isFirstLaunch) }

    LaunchedEffect(showOnboarding) {
        if (showOnboarding)
            navigateToGetStarted()
        else
            navigateToHomePage()
    }
}

