package com.Proyecto.coffeepalace.ui.Screens.Profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.Proyecto.coffeepalace.R
import com.Proyecto.coffeepalace.ui.components.CustomButton
import com.Proyecto.coffeepalace.ui.components.EditableProfileImage
import com.Proyecto.coffeepalace.ui.components.HomeTitle
import com.Proyecto.coffeepalace.ui.components.InputFieldEmail
import com.Proyecto.coffeepalace.ui.components.InputFieldNumber
import com.Proyecto.coffeepalace.ui.components.InputFieldPassword
import com.Proyecto.coffeepalace.ui.components.InputFieldText
import com.Proyecto.coffeepalace.ui.theme.LightGray

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(LightGray),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            EditableProfileImage(
                image = painterResource(R.drawable.profile_icon),
                onEditClick = {}
            )
            Spacer(modifier = Modifier.padding(8.dp))
        }
        item {
            HomeTitle(title = "Personal Details", color = Color.Black, fontStyle = MaterialTheme.typography.bodyLarge)
            InputFieldEmail()
            InputFieldPassword()
            HomeTitle(title = "Address Details", color = Color.Black, fontStyle = MaterialTheme.typography.bodyLarge)
            InputFieldText(label = "Address", placeholder = "Enter your address")
            InputFieldNumber()
            Spacer(modifier = Modifier.padding(8.dp))
            CustomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = "Save",
                onClick = {})
        }
    }
}