package com.arcittakinanthi.otocare.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arcittakinanthi.otocare.auth.AuthManager

@Composable
fun ProfileScreen(
    onLogout: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = AuthManager.getUserName(),
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = AuthManager.getEmail()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                AuthManager.logout()
                onLogout()
            }
        ) {
            Text("Logout")
        }
    }
}