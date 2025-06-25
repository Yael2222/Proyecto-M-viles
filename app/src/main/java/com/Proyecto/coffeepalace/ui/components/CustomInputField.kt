package com.Proyecto.coffeepalace.ui.components

import android.util.Patterns
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class InputType {
    TEXT,
    EMAIL,
    PASSWORD,
    NUMBER,
    PHONE
}

@Composable
fun CustomInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    inputType: InputType = InputType.TEXT,
    placeholder: String = "",
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    isError: Boolean = false,
    errorMessage: String? = null,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    horizontalPadding: Dp = 16.dp,
    verticalPadding: Dp = 8.dp,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(modifier = modifier.padding(horizontal = horizontalPadding)) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            placeholder = { Text(placeholder) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = verticalPadding),
            singleLine = singleLine,
            enabled = enabled,
            readOnly = readOnly,
            isError = isError,
            visualTransformation = when (inputType) {
                InputType.PASSWORD -> if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
                else -> VisualTransformation.None
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = when (inputType) {
                    InputType.EMAIL -> KeyboardType.Email
                    InputType.NUMBER -> KeyboardType.Number
                    InputType.PHONE -> KeyboardType.Phone
                    else -> KeyboardType.Text
                },
                autoCorrect = inputType != InputType.PASSWORD
            ),
            leadingIcon = leadingIcon?.let {
                { Icon(imageVector = it, contentDescription = null) }
            },
            trailingIcon = {
                when {
                    inputType == InputType.PASSWORD -> {
                        val icon =
                            if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = icon,
                                contentDescription = "Toggle password visibility"
                            )
                        }
                    }

                    trailingIcon != null && onTrailingIconClick != null -> {
                        IconButton(onClick = onTrailingIconClick) {
                            Icon(imageVector = trailingIcon, contentDescription = null)
                        }
                    }

                    else -> Unit
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                errorBorderColor = Color.Red,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                errorTextColor = Color.Red,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                errorLabelColor = Color.Red,
                focusedPlaceholderColor = Color.Black,
                unfocusedPlaceholderColor = Color.Black,
                errorPlaceholderColor = Color.Red,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                errorContainerColor = Color.White
            ),
            keyboardActions = keyboardActions,
            maxLines = maxLines
        )

        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 16.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun InputFieldEmail(
    email: String,
    updateEmail: (String) -> Unit
) {
    CustomInputField(
        value = email,
        onValueChange = updateEmail,
        label = "Email Address",
        inputType = InputType.EMAIL,
        placeholder = "ejemplo@dominio.com",
        isError = email.isNotBlank() && !Patterns.EMAIL_ADDRESS.matcher(email).matches(),
        errorMessage = if (email.isNotBlank() && !Patterns.EMAIL_ADDRESS.matcher(email).matches())
            "Email no válido" else null
    )
}

@Composable
fun InputFieldPassword(
    password: String,
    updatePassword: (String) -> Unit
) {

    CustomInputField(
        value = password,
        onValueChange = updatePassword,
        label = "Password",
        inputType = InputType.PASSWORD,
        placeholder = "Mínimo 8 caracteres"
    )
}

@Composable
fun InputFieldText(
    label: String,
    placeholder: String,
    text: String,
    updateText: (String) -> Unit
) {

    CustomInputField(
        value = text,
        onValueChange = updateText,
        label = label,
        inputType = InputType.TEXT,
        placeholder = placeholder
    )
}

@Composable
fun InputFieldNumber(
    number: String,
    updateNumber: (String) -> Unit
) {

    CustomInputField(
        value = number,
        onValueChange = updateNumber,
        label = "Cell Phone",
        inputType = InputType.PHONE,
        placeholder = "12345678",
    )
}