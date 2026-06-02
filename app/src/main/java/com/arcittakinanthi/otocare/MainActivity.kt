package com.arcittakinanthi.otocare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.arcittakinanthi.otocare.navigation.NavGraph
import com.arcittakinanthi.otocare.screen.MainViewModel
import com.arcittakinanthi.otocare.ui.theme.OtoCareTheme
import com.arcittakinanthi.otocare.util.ViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = ViewModelFactory(applicationContext)
        val viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        setContent {
            OtoCareTheme {
                val navController = rememberNavController()

                NavGraph(
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }
    }
}