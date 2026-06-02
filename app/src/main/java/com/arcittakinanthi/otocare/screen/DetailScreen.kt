package com.arcittakinanthi.otocare.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arcittakinanthi.otocare.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    id: Long,
    viewModel: MainViewModel,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val isEdit = id != 0L

    var vehicleName by remember { mutableStateOf("") }
    var plateNumber by remember { mutableStateOf("") }
    var serviceType by remember { mutableStateOf("") }
    var lastServiceDate by remember { mutableStateOf("") }
    var intervalMonth by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }
    var openDialog by remember { mutableStateOf(false) }

    val serviceOptions = listOf(
        "Ganti Oli",
        "Servis Rutin",
        "Ganti Ban",
        "Cek Rem",
        "Lainnya"
    )

    LaunchedEffect(id) {
        if (isEdit) {
            val data = viewModel.getService(id)
            data?.let {
                vehicleName = it.vehicleName
                plateNumber = it.plateNumber
                serviceType = it.serviceType
                lastServiceDate = it.lastServiceDate
                intervalMonth = it.intervalMonth.toString()
            }
        }
    }

    DisplayAlertDialog(
        openDialog = openDialog,
        onDismissRequest = {
            openDialog = false
        },
        onConfirmation = {
            viewModel.delete(id)
            openDialog = false
            Toast.makeText(
                context,
                "Data servis berhasil dihapus.",
                Toast.LENGTH_SHORT
            ).show()
            onBackClick()
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isEdit) {
                            stringResource(R.string.ubah_servis)
                        } else {
                            stringResource(R.string.tambah_servis)
                        }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            val interval = intervalMonth.toIntOrNull()

                            if (
                                vehicleName.isBlank() ||
                                plateNumber.isBlank() ||
                                serviceType.isBlank() ||
                                lastServiceDate.isBlank() ||
                                interval == null
                            ) {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.data_kosong),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                if (isEdit) {
                                    viewModel.update(
                                        id = id,
                                        vehicleName = vehicleName,
                                        plateNumber = plateNumber,
                                        serviceType = serviceType,
                                        lastServiceDate = lastServiceDate,
                                        intervalMonth = interval
                                    )

                                    Toast.makeText(
                                        context,
                                        "Data servis berhasil diubah.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    viewModel.insert(
                                        vehicleName = vehicleName,
                                        plateNumber = plateNumber,
                                        serviceType = serviceType,
                                        lastServiceDate = lastServiceDate,
                                        intervalMonth = interval
                                    )

                                    Toast.makeText(
                                        context,
                                        "Data servis berhasil disimpan.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                onBackClick()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = stringResource(R.string.simpan)
                        )
                    }

                    if (isEdit) {
                        IconButton(onClick = { expanded = true }) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = stringResource(R.string.hapus)
                            )
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                text = {
                                    Text(text = stringResource(R.string.hapus))
                                },
                                onClick = {
                                    expanded = false
                                    openDialog = true
                                }
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        FormService(
            vehicleName = vehicleName,
            onVehicleNameChange = { vehicleName = it },
            plateNumber = plateNumber,
            onPlateNumberChange = { plateNumber = it },
            serviceType = serviceType,
            onServiceTypeChange = { serviceType = it },
            lastServiceDate = lastServiceDate,
            onLastServiceDateChange = { lastServiceDate = it },
            intervalMonth = intervalMonth,
            onIntervalMonthChange = { intervalMonth = it },
            serviceOptions = serviceOptions,
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        )
    }
}

@Composable
fun FormService(
    vehicleName: String,
    onVehicleNameChange: (String) -> Unit,
    plateNumber: String,
    onPlateNumberChange: (String) -> Unit,
    serviceType: String,
    onServiceTypeChange: (String) -> Unit,
    lastServiceDate: String,
    onLastServiceDateChange: (String) -> Unit,
    intervalMonth: String,
    onIntervalMonthChange: (String) -> Unit,
    serviceOptions: List<String>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = vehicleName,
            onValueChange = onVehicleNameChange,
            label = { Text(text = stringResource(R.string.nama_kendaraan)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = plateNumber,
            onValueChange = onPlateNumberChange,
            label = { Text(text = stringResource(R.string.nomor_polisi)) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        Text(
            text = stringResource(R.string.jenis_servis),
            modifier = Modifier.padding(top = 16.dp)
        )

        serviceOptions.forEach { text ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = serviceType == text,
                    onClick = { onServiceTypeChange(text) }
                )

                Text(text = text)
            }
        }

        OutlinedTextField(
            value = lastServiceDate,
            onValueChange = onLastServiceDateChange,
            label = { Text(text = stringResource(R.string.tanggal_servis)) },
            placeholder = { Text(text = stringResource(R.string.contoh_tanggal)) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        OutlinedTextField(
            value = intervalMonth,
            onValueChange = onIntervalMonthChange,
            label = { Text(text = stringResource(R.string.interval_bulan)) },
            placeholder = { Text(text = stringResource(R.string.contoh_interval)) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
    }
}