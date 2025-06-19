package com.Proyecto.coffeepalace.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.Proyecto.coffeepalace.ui.theme.Brown
import com.Proyecto.coffeepalace.ui.theme.LightGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NextScreenAppBar(
    modifier: Modifier = Modifier,
    onNextClick: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = "Get Started",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Brown
                )
            )
        },
        actions = {
            IconButton(onClick = onNextClick) {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Continuar",
                    tint = Brown
                )
            }
        },
        navigationIcon = {},
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = LightGray,
            titleContentColor = Brown,
            actionIconContentColor = Brown
        )
    )
}