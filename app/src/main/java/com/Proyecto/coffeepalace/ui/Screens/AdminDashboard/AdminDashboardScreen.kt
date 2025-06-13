package com.Proyecto.coffeepalace.ui.Screens.AdminDashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.Proyecto.coffeepalace.Data.Model.DashboardItem
import com.Proyecto.coffeepalace.R
import com.Proyecto.coffeepalace.ui.theme.BackgroundColor
import com.Proyecto.coffeepalace.ui.theme.CoffeeBrown
import com.Proyecto.coffeepalace.ui.theme.LightCoffeeBrown
import com.Proyecto.coffeepalace.ui.theme.TextWhite

@Composable
fun AdminDashboardScreen(
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel = viewModel(),
    onItemClick: (String) -> Unit
) {
    val dashboardItems by viewModel.dashboardItems.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BackgroundColor
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            HeaderSection()

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
                thickness = 1.dp,
                color = Color.Gray
            )

            NavigationOptionsList(
                items = dashboardItems,
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp,
                end = 24.dp,
                top = 40.dp,
                bottom = 16.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo The Coffee Palace",
            modifier = Modifier
                .size(64.dp)
                .padding(end = 16.dp)
        )


        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "The Coffee Palace",
                fontSize = 28.sp,
                style = MaterialTheme.typography.headlineLarge,
                color = CoffeeBrown,
                modifier = Modifier.padding(bottom = 2.dp)
            )
            Text(
                text = "Hello Administrator",
                fontSize = 18.sp,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.DarkGray
            )
        }
    }
}

@Composable
fun NavigationOptionsList(
    items: List<DashboardItem>,
    onItemClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        items.forEach { item ->
            NavigationCard(item = item) {
                onItemClick(item.route)
            }
        }
    }
}

@Composable
fun NavigationCard(item: DashboardItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = CoffeeBrown,
            contentColor = TextWhite
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            item.icon?.let { iconVector ->
                Icon(
                    imageVector = iconVector,
                    contentDescription = item.title,
                    modifier = Modifier.size(24.dp),
                    tint = TextWhite
                )
                Spacer(modifier = Modifier.width(16.dp))
            }

            Text(
                text = item.title,
                fontSize = 20.sp
            )
        }
    }
}
