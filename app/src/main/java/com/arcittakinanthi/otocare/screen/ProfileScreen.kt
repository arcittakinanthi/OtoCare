package com.arcittakinanthi.otocare.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = null,
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Baiq Izza Aziza",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "izza@otocare.com"
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