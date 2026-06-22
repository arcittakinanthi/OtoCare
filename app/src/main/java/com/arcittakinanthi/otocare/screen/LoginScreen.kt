package com.arcittakinanthi.otocare.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.arcittakinanthi.otocare.util.SettingsDataStore
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit
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

        Text("OtoCare")

        Button(
            onClick = {

                scope.launch {

                    settings.saveLogin(true)

                    onLoginClick()
                }
            }
        ) {
            Text("Login")
        }
    }
}