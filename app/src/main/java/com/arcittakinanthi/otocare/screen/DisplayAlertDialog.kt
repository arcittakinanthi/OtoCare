package com.arcittakinanthi.otocare.screen

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.arcittakinanthi.otocare.R

@Composable
fun DisplayAlertDialog(
    openDialog: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    if (openDialog) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            text = {
                Text(text = stringResource(R.string.konfirmasi_hapus))
            },
            confirmButton = {
                Button(onClick = onConfirmation) {
                    Text(text = stringResource(R.string.hapus))
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissRequest) {
                    Text(text = stringResource(R.string.batal))
                }
            }
        )
    }
}