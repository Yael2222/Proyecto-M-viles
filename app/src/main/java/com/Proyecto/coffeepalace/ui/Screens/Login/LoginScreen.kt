package com.Proyecto.coffeepalace.ui.Screens.Login

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import com.Proyecto.coffeepalace.Data.LoginViewModelFactory
import com.Proyecto.coffeepalace.ui.components.SocialButton
import com.google.android.gms.auth.api.signin.GoogleSignIn


@Composable
fun LoginScreen(
    onNavigateToForgotPassword: () -> Unit = {},
    onNavigateToSignUp: () -> Unit = {},
    onLoginSuccess: () -> Unit = {}
) {
    val context = LocalContext.current
    val viewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(context))
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val isPasswordVisible by viewModel.isPasswordVisible.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            viewModel.handleGoogleSignInResult(task, onLoginSuccess)
        } else {
            println("Google Sign-In cancelado o fallido. Código: ${result.resultCode}")
            viewModel.setErrorMessage("Inicio de sesión con Google cancelado.") // Llama a la función del ViewModel
        }
    }


    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(paddingValues)
            // .hideKeyboardOnTap(), // <-- Elimina esta línea si hideKeyboardOnTap no existe
            , // Deja esta coma si eliminas la línea anterior
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Asumiendo que TitleSection, EmailField, PasswordField están definidos localmente
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

            SocialLoginSection( // Asumiendo que SocialLoginSection está definido localmente
                onGoogleSignInClick = {
                    val signInIntent = viewModel.getGoogleSignInIntent()
                    signInIntent?.let { googleSignInLauncher.launch(it) }
                },
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.height(24.dp))

            CreateAccountSection (onNavigateToSignUp)
        }
    }
}

// Re-coloco aquí las funciones Composable auxiliares si no están en otro archivo
// y eran las que daban problemas de "no existe".

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

// **SocialButton y SocialLoginSection necesitan definirse o crearse**
// Aquí asumiré que SocialLoginSection (que usa SocialButton) se define aquí también.
// Y si SocialButton no existe, lo crearemos en un nuevo archivo genérico de componentes.

@Composable
fun SocialLoginSection(onGoogleSignInClick: () -> Unit, enabled: Boolean) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Si SocialButton no existe, puedes definirlo aquí o en un archivo compartido
            SocialButton(
                assetName = "icon_google.png",
                onClick = onGoogleSignInClick,
                enabled = enabled
            )
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