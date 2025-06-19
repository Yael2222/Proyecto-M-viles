package com.Proyecto.coffeepalace.ui.Screens.Forgot

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel = viewModel(),
    navController: NavHostController,
    //onNavigateBack: () -> Unit
) {
    val email = viewModel.email
    val errorMessage = viewModel.errorMessage.value
    val successMessage = viewModel.successMessage.value

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleSection()

            Spacer(modifier = Modifier.height(24.dp))

            EmailField(
                value = email,
                onValueChange = viewModel::onEmailChange
            )

            if (!errorMessage.isNullOrEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .fillMaxWidth()
                )
            }

            if (!successMessage.isNullOrEmpty()) {
                Text(
                    text = successMessage,
                    color = Color(0xFF4CAF50),
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .fillMaxWidth()
                )
            }

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
                    // Solo navega si no hay error
                    /*if (viewModel.errorMessage.value == null) {
                        onNavigateBack()
                    }*/
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4B2B20)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Submit", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun TitleSection() {
    Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start)
    {
        Text("Forgot", fontSize = 50.sp, fontWeight = FontWeight.Bold)
        Text("password?", fontSize = 50.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun EmailField(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text("Enter your email address") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Email icon for password reset"
            )
        },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color(0xFFB55B00),
            focusedLabelColor = Color(0xFFB55B00),
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        )
    )
}



