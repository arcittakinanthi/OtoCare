package com.arcittakinanthi.otocare.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.arcittakinanthi.otocare.auth.AuthManager
import com.arcittakinanthi.otocare.util.SettingsDataStore
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    onLogout: () -> Unit
) {

    val context = LocalContext.current

    val settings =
        SettingsDataStore(context)

    val scope =
        rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (AuthManager.getUserName().isNotBlank())
                AuthManager.getUserName()
            else
                "Pengguna OtoCare",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (AuthManager.getEmail().isNotBlank())
                AuthManager.getEmail()
            else
                "user@otocare.com"
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {

                scope.launch {

                    settings.saveLogin(false)

                    AuthManager.logout()

                    onLogout()
                }
            }
        ) {
            Text("Logout")
        }
    }
}