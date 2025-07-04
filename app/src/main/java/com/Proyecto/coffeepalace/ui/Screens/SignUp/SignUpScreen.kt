package com.Proyecto.coffeepalace.ui.Screens.SignUp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.ArrowBack // <-- Importación necesaria
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import com.Proyecto.coffeepalace.ui.Screens.SignUp.SignUpViewModelFactory
import com.Proyecto.coffeepalace.ui.components.SocialButton
import androidx.compose.ui.text.style.TextAlign // Importar para centrar texto
import androidx.navigation.NavController // <-- Importación necesaria
import com.Proyecto.coffeepalace.ui.navigations.Screens // <-- Importación necesaria

@OptIn(ExperimentalMaterial3Api::class) // <-- Añade esta anotación
@Composable
fun SignUpScreen(
    navController: NavController, // <-- Acepta NavController como parámetro
    viewModel: SignUpViewModel = viewModel(factory = SignUpViewModelFactory()),
    onNavigateToLogin: () -> Unit = {},
    onBackToLogin: () -> Boolean // Aunque no se usa directamente en este composable, se mantiene si es parte de la API
) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
    val name by viewModel.name.collectAsState()
    val isPasswordVisible by viewModel.isPasswordVisible.collectAsState()
    val isConfirmPasswordVisible by viewModel.isConfirmPasswordVisible.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val successMessage by viewModel.successMessage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // Definimos algunos colores de la paleta de café
    val CoffeeDark = Color(0xFF4A2C20) // Marrón oscuro, casi negro
    val CoffeeMedium = Color(0xFF8B4513) // Marrón café medio
    val CoffeeLight = Color(0xFFC8A47E) // Color crema/latte
    val CoffeeAccent = Color(0xFFB55B00) // Naranja quemado/caramelo (ya lo usabas)

    LaunchedEffect(successMessage) {
        if (!successMessage.isNullOrEmpty() && successMessage == "Registro exitoso. ¡Revisa tu correo para confirmar!") {
            onNavigateToLogin()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { // <-- Añade el TopAppBar aquí
            TopAppBar(
                title = { /* No hay título si solo quieres la flecha */ },
                navigationIcon = {
                    IconButton(onClick = {
                        // Navega al home_screen y borra el SignUp de la pila
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
                .padding(paddingValues) // Importante aplicar el padding del Scaffold
                .padding(horizontal = 24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleSection(darkColor = CoffeeDark) // Pasa el color oscuro al título

            Spacer(modifier = Modifier.height(32.dp))

            NameField(
                value = name,
                onValueChange = viewModel::onNameChange,
                focusedColor = CoffeeAccent,
                unfocusedColor = CoffeeMedium // Color para el borde cuando no está enfocado
            )
            Spacer(modifier = Modifier.height(16.dp))

            EmailField(
                value = email,
                onValueChange = viewModel::onEmailChange,
                focusedColor = CoffeeAccent,
                unfocusedColor = CoffeeMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            PasswordField(
                value = password,
                onValueChange = viewModel::onPasswordChange,
                isVisible = isPasswordVisible,
                onToggleVisibility = viewModel::togglePasswordVisibility,
                label = "Contraseña",
                focusedColor = CoffeeAccent,
                unfocusedColor = CoffeeMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            PasswordField(
                value = confirmPassword,
                onValueChange = viewModel::onConfirmPasswordChange,
                isVisible = isConfirmPasswordVisible,
                onToggleVisibility = viewModel::toggleConfirmPasswordVisibility,
                label = "Confirma la contraseña",
                focusedColor = CoffeeAccent,
                unfocusedColor = CoffeeMedium
            )

            if (!errorMessage.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = errorMessage ?: "",
                    color = MaterialTheme.colorScheme.error, // Rojo por defecto para errores
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center, // Centra el texto de error
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (!successMessage.isNullOrEmpty() && successMessage != "Registro exitoso. ¡Revisa tu correo para confirmar!") {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = successMessage ?: "",
                    color = Color(0xFF4CAF50), // Verde para éxito
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center, // Centra el texto de éxito
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.onSignUpClick() },
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = CoffeeDark, // Botón con color café oscuro
                    contentColor = CoffeeLight // Texto del botón con color crema
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = CoffeeLight, // Indicador de carga color crema
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Crea una cuenta", fontWeight = FontWeight.SemiBold,color = Color(0xFFFFE0B2))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))


            Row {
                Text(
                    text = "¿Ya tienes una cuenta? ",
                    color = CoffeeMedium // Texto normal en café medio
                )
                Text(
                    text = "Iniciar sesión",
                    color = CoffeeAccent, // Texto de enlace en color acento (caramelo)
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onNavigateToLogin() }
                )
            }
        }
    }
}

// ... (El resto de tus Composable como TitleSection, NameField, EmailField, PasswordField permanecen iguales)
// Asegúrate de que los composables auxiliares estén definidos fuera de SignUpScreen
// si ya los tienes en un archivo separado, no es necesario duplicarlos aquí.
@Composable
fun TitleSection(darkColor: Color) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Crea una", fontSize = 50.sp, fontWeight = FontWeight.Bold, color = darkColor)
        Text("cuenta", fontSize = 50.sp, fontWeight = FontWeight.Bold, color = darkColor)
    }
}

@Composable
fun NameField(value: String, onValueChange: (String) -> Unit, focusedColor: Color, unfocusedColor: Color) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Nombre", color = unfocusedColor) }, // Color del label
        leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Name Icon", tint = unfocusedColor) }, // Color del icono
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = focusedColor,
            focusedLabelColor = focusedColor,
            focusedLeadingIconColor = focusedColor, // Icono cuando está enfocado
            unfocusedIndicatorColor = unfocusedColor,
            unfocusedLabelColor = unfocusedColor,
            unfocusedLeadingIconColor = unfocusedColor,
            focusedContainerColor = Color.White, // Fondo blanco para el campo
            unfocusedContainerColor = Color.White,
            cursorColor = focusedColor // Color del cursor
        )
    )
}

@Composable
fun EmailField(value: String, onValueChange: (String) -> Unit, focusedColor: Color, unfocusedColor: Color) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Correo Electrónico", color = unfocusedColor) }, // Cambiado a "Correo Electrónico"
        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = unfocusedColor) }, // Cambiado a icono de email
        singleLine = true,
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
    value: String,
    onValueChange: (String) -> Unit,
    isVisible: Boolean,
    onToggleVisibility: () -> Unit,
    label: String,
    focusedColor: Color,
    unfocusedColor: Color,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = unfocusedColor) },
        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = unfocusedColor) },
        trailingIcon = {
            IconButton(onClick = onToggleVisibility) {
                Icon(
                    imageVector = if (isVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = null,
                    tint = unfocusedColor // Color del icono de visibilidad
                )
            }
        },
        singleLine = true,
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
        modifier = modifier,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = focusedColor,
            focusedLabelColor = focusedColor,
            focusedLeadingIconColor = focusedColor,
            unfocusedIndicatorColor = unfocusedColor,
            unfocusedLabelColor = unfocusedColor,
            unfocusedLeadingIconColor = unfocusedColor,
            focusedTrailingIconColor = focusedColor, // Icono de visibilidad cuando enfocado
            unfocusedTrailingIconColor = unfocusedColor, // Icono de visibilidad cuando no enfocado
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            cursorColor = focusedColor
        )
    )
}