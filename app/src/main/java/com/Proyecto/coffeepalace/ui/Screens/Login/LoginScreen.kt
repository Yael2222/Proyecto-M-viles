package com.Proyecto.coffeepalace.ui.Screens.Login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import com.Proyecto.coffeepalace.ui.components.SocialButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.collectAsState
import com.Proyecto.coffeepalace.ui.utils.hideKeyboardOnTap
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onNavigateToForgotPassword: () -> Unit = {},
    onNavigateToSignUp: () -> Unit = {},
    onLoginSuccess: () -> Unit = {}
) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val isPasswordVisible by viewModel.isPasswordVisible.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(paddingValues)
                .hideKeyboardOnTap(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleSection()

            Spacer(modifier = Modifier.height(32.dp))

            EmailField(
                email = email,
                onEmailChange = viewModel::onEmailChange,
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.height(16.dp))

            PasswordField(
                password = password,
                onPasswordChange = viewModel::onPasswordChange,
                isPasswordVisible = isPasswordVisible,
                onToggleVisibility = viewModel::onTogglePasswordVisibility,
                enabled = !isLoading
            )

            val error = errorMessage
            if (!error.isNullOrEmpty()) {
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = "Forgot Password?",
                    color = Color(0xFFB55B00),
                    fontSize = 12.sp,
                    modifier = Modifier.clickable { onNavigateToForgotPassword() }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            LoginButton(
                onClick = { viewModel.onLoginClick(onLoginSuccess) },
                enabled = !isLoading,
                isLoading = isLoading
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text("- OR Continue with -", color = Color.Gray, fontSize = 12.sp)

            Spacer(modifier = Modifier.height(12.dp))

            SocialLoginSection()

            Spacer(modifier = Modifier.height(24.dp))

            CreateAccountSection (onNavigateToSignUp)
        }
    }
}

@Composable
fun TitleSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome", fontSize = 50.sp, fontWeight = FontWeight.Bold)
        Text("Back!", fontSize = 50.sp, fontWeight = FontWeight.Bold)
    }
}


@Composable
fun EmailField(email: String, onEmailChange: (String) -> Unit, enabled: Boolean) {
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        label = { Text("Username or Email") },
        leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Email Icon") },
        singleLine = true,
        enabled = enabled,
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color(0xFFB55B00),
            focusedLabelColor = Color(0xFFB55B00),
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        )
    )
}

@Composable
fun PasswordField(
    password: String,
    onPasswordChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onToggleVisibility: () -> Unit,
    enabled: Boolean
) {
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text("Password") },
        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Password Icon") },
        trailingIcon = {
            IconButton(onClick = onToggleVisibility) {
                Icon(
                    imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = if (isPasswordVisible) "Hide Password" else "Show Password"
                )
            }
        },
        singleLine = true,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        enabled = enabled,
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color(0xFFB55B00),
            focusedLabelColor = Color(0xFFB55B00),
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        )
    )
}

@Composable
fun LoginButton(onClick: () -> Unit, enabled: Boolean, isLoading: Boolean) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            contentColor = Color.White,
            disabledContentColor = Color.White.copy(alpha = 0.38f)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.size(24.dp),
                strokeWidth = 2.dp
            )
        } else {
            Text("Login", fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun SocialLoginSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SocialButton(assetName = "icon_google.png")
        }
    }
}


@Composable
fun CreateAccountSection(onNavigateToSignUp: () -> Unit) {
    Row {
        Text("Create An Account ")
        Text(
            text = "Sign Up",
            color = Color(0xFFB55B00),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable { onNavigateToSignUp() }
        )
    }
}
