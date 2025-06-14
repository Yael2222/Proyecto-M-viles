package com.Proyecto.coffeepalace.ui.Screens.Forgot

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    val email = viewModel.email

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "Forgot",
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "password?",
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = viewModel::onEmailChange,
            placeholder = { Text("Enter your email address") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Email, contentDescription = "Email icon")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "* We will send you a message to set or reset your new password",
            fontSize = 12.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.submitResetRequest()
                onNavigateBack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4B2B20)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Submit",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
