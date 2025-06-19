package com.Proyecto.coffeepalace.ui.Screens.Client.ConfirmationPayment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.Proyecto.coffeepalace.ui.components.CustomButton
import com.Proyecto.coffeepalace.ui.components.HomeTitle
import com.Proyecto.coffeepalace.ui.components.InputFieldEmail
import com.Proyecto.coffeepalace.ui.theme.LightGray200

@Composable
fun ConfirmationPaymentScreen(
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .background(LightGray200)
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        item {
            Text(
                text = "Paypal email address",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            InputFieldEmail()
            Spacer(modifier = Modifier.height(45.dp))
            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing " +
                        "elit, sed do eiusmod tempor incididunt ut labore et " +
                        "dolore magna aliqua. Ut enim ad minim veniam," +
                        "quis nostrud exercitation ullamco laboris nisi ut aliquip " +
                        "ex ea commodo consequat. Duis aute irure dolor in " +
                        "reprehenderit in voluptate velit esse cillum dolore eu " +
                        "fugiat nulla pariatur. Excepteur sint occaecat " +
                        "cupidatat non proident, sunt in culpa qui officia " +
                        "deserunt mollit anim id est laborum. ",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            HomeTitle(
                title = "Term and Conditions",
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.fillMaxSize()
            )
            Spacer(modifier = Modifier.height(6.dp))
            CustomButton(text = "Confirm Payment", onClick = {}, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}