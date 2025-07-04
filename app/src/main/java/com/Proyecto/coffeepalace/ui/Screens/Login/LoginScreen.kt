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
import androidx.compose.material.icons.automirrored.filled.ArrowBack // <-- Importación necesaria
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import com.Proyecto.coffeepalace.Data.LoginViewModelFactory
import com.Proyecto.coffeepalace.ui.components.SocialButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.navigation.NavController
import com.Proyecto.coffeepalace.ui.navigations.Screens
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material3.ExperimentalMaterial3Api // <-- Importación necesaria
import androidx.compose.material3.TopAppBar // <-- Importación necesaria

@OptIn(ExperimentalMaterial3Api::class) // <-- Añade esta anotación
@Composable
fun LoginScreen(
    navController: NavController,
    onNavigateToForgotPassword: () -> Unit = {},
    onNavigateToSignUp: () -> Unit = {}
) {
    val context = LocalContext.current
    val viewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(context))
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val isPasswordVisible by viewModel.isPasswordVisible.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val CoffeeDark = Color(0xFF4A2C20)
    val CoffeeMedium = Color(0xFF8B4513)
    val CoffeeLight = Color(0xFFC8A47E)
    val CoffeeAccent = Color(0xFFB55B00)

    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            viewModel.handleGoogleSignInResult(task) { userRole ->
                if (userRole == "vendedor") {
                    navController.navigate(Screens.HomeSeller.route) {
                        popUpTo(Screens.Login.route) { inclusive = true }
                    }
                } else {
                    navController.navigate(Screens.User_Settings.route) {
                        popUpTo(Screens.Login.route) { inclusive = true }
                    }
                }
            }
        } else {
            println("Google Sign-In cancelado o fallido. Código: ${result.resultCode}")
            viewModel.setErrorMessage("Inicio de sesión con Google cancelado.")
        }
    }


    Scaffold(
        topBar = { // <-- Añade el TopAppBar aquí
            TopAppBar(
                title = { /* No hay título aquí si solo quieres la flecha */ },
                navigationIcon = {
                    IconButton(onClick = {
                        // Navega al home_screen y borra el Login de la pila
                        navController.navigate(Screens.Home.route) { // <-- Usa la ruta correcta para tu Home de usuario
                            popUpTo(Screens.Home.route) { inclusive = true } // Esto limpia la pila hasta el Home
                            launchSingleTop = true // Evita múltiples copias si ya estás en Home
                        }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver a Inicio")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(paddingValues), // Importante aplicar el padding del Scaffold
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleSection(darkColor = CoffeeDark)

            Spacer(modifier = Modifier.height(32.dp))

            EmailField(
                email = email,
                onEmailChange = viewModel::onEmailChange,
                enabled = !isLoading,
                focusedColor = CoffeeAccent,
                unfocusedColor = CoffeeMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            PasswordField(
                password = password,
                onPasswordChange = viewModel::onPasswordChange,
                isPasswordVisible = isPasswordVisible,
                onToggleVisibility = viewModel::onTogglePasswordVisibility,
                enabled = !isLoading,
                focusedColor = CoffeeAccent,
                unfocusedColor = CoffeeMedium
            )

            val error = errorMessage
            if (!error.isNullOrEmpty()) {
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp).fillMaxWidth()
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = "¿Olvidaste tu contraseña?",
                    color = CoffeeAccent,
                    fontSize = 12.sp,
                    modifier = Modifier.clickable { onNavigateToForgotPassword() }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            LoginButton(
                onClick = {
                    viewModel.onLoginClick { userRole ->
                        if (userRole == "vendedor") {
                            navController.navigate(Screens.HomeSeller.route) {
                                popUpTo(Screens.Login.route) { inclusive = true }
                            }
                        } else {
                            navController.navigate(Screens.User_Settings.route) {
                                popUpTo(Screens.Login.route) { inclusive = true }
                            }
                        }
                    }
                },
                enabled = !isLoading,
                isLoading = isLoading,
                buttonColor = CoffeeDark,
                textColor = CoffeeLight,
                loadingColor = CoffeeLight
            )

            Spacer(modifier = Modifier.height(24.dp))


            CreateAccountSection (
                onNavigateToSignUp,
                regularTextColor = CoffeeMedium,
                linkTextColor = CoffeeAccent
            )
        }
    }
}

// ... (El resto de tus Composable como TitleSection, EmailField, PasswordField, LoginButton, CreateAccountSection permanecen iguales)

@Composable
fun TitleSection(darkColor: Color) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Bienvenido", fontSize = 50.sp, fontWeight = FontWeight.Bold, color = darkColor)
        Text("de vuelta!", fontSize = 50.sp, fontWeight = FontWeight.Bold, color = darkColor)
    }
}


@Composable
fun EmailField(email: String, onEmailChange: (String) -> Unit, enabled: Boolean, focusedColor: Color, unfocusedColor: Color) {
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        label = { Text("Correo Electrónico", color = unfocusedColor) },
        leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email Icon", tint = unfocusedColor) },
        singleLine = true,
        enabled = enabled,
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = focusedColor,
            focusedLabelColor = focusedColor,
            focusedLeadingIconColor = focusedColor,
            unfocusedIndicatorColor = unfocusedColor,
            unfocusedLabelColor = unfocusedColor,
            unfocusedLeadingIconColor = unfocusedColor,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            cursorColor = focusedColor
        )
    )
}

@Composable
fun PasswordField(
    password: String,
    onPasswordChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onToggleVisibility: () -> Unit,
    enabled: Boolean,
    focusedColor: Color,
    unfocusedColor: Color
) {
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text("Contraseña", color = unfocusedColor) },
        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Password Icon", tint = unfocusedColor) },
        trailingIcon = {
            IconButton(onClick = onToggleVisibility) {
                Icon(
                    imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = if (isPasswordVisible) "Ocultar Contraseña" else "Mostrar Contraseña",
                    tint = unfocusedColor
                )
            }
        },
        singleLine = true,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        enabled = enabled,
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = focusedColor,
            focusedLabelColor = focusedColor,
            focusedLeadingIconColor = focusedColor,
            unfocusedIndicatorColor = unfocusedColor,
            unfocusedLabelColor = unfocusedColor,
            unfocusedLeadingIconColor = unfocusedColor,
            focusedTrailingIconColor = focusedColor,
            unfocusedTrailingIconColor = unfocusedColor,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            cursorColor = focusedColor
        )
    )
}

@Composable
fun LoginButton(onClick: () -> Unit, enabled: Boolean, isLoading: Boolean, buttonColor: Color, textColor: Color, loadingColor: Color) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
            disabledContainerColor = buttonColor.copy(alpha = 0.5f),
            contentColor = textColor,
            disabledContentColor = textColor.copy(alpha = 0.5f)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = loadingColor,
                modifier = Modifier.size(24.dp),
                strokeWidth = 2.dp
            )
        } else {
            Text("Iniciar sesión", fontWeight = FontWeight.SemiBold, color = Color.White)
        }
    }
}


@Composable
fun CreateAccountSection(onNavigateToSignUp: () -> Unit, regularTextColor: Color, linkTextColor: Color) {
    Row {
        Text("¿No tienes una cuenta? ", color = regularTextColor)
        Text(
            text = "Regístrate",
            color = linkTextColor,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable { onNavigateToSignUp() }
        )
    }
}