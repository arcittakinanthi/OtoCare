package com.arcittakinanthi.otocare.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.arcittakinanthi.otocare.R
import com.arcittakinanthi.otocare.model.ServiceRecord
import com.arcittakinanthi.otocare.navigation.Screen
import com.arcittakinanthi.otocare.util.SettingsDataStore
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    onAddClick: () -> Unit,
    onEditClick: (Long) -> Unit,
    onProfileClick: () -> Unit
) {
    val data by viewModel.data.collectAsState()
    val status by viewModel.status.collectAsState()
    val apiData by viewModel.apiData.collectAsState()

    val context = LocalContext.current
    val settingsDataStore = remember { SettingsDataStore(context) }
    val showList by settingsDataStore.layoutFlow.collectAsState(initial = true)
    val coroutineScope = rememberCoroutineScope()

    if (status == ApiStatus.LOADING) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

        return
    }

    if (status == ApiStatus.FAILED) {

        ErrorContent(
            onRetry = {
                viewModel.retrieveData()
            }
        )

        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.app_name)) },
                actions = {
                    IconButton(
                        onClick = {
                            onProfileClick()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = null
                        )
                    }
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                settingsDataStore.saveLayout(!showList)
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(
                                id = if (showList) {
                                    R.drawable.outline_grid_view_24
                                } else {
                                    R.drawable.baseline_view_list_24
                                }
                            ),
                            contentDescription = stringResource(
                                if (showList) R.string.grid else R.string.list
                            )
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.tambah_servis)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            HeaderOtoCare()

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Data API: ${apiData.size}"
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (data.isEmpty()) {
                EmptyContent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            } else {
                if (showList) {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(data) { record ->
                            ServiceListItem(
                                record = record,
                                onClick = { onEditClick(record.id) }
                            )
                        }
                    }
                } else {
                    LazyVerticalStaggeredGrid(
                        modifier = Modifier.weight(1f),
                        columns = StaggeredGridCells.Fixed(2),
                        verticalItemSpacing = 8.dp,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(data) { record ->
                            ServiceGridItem(
                                record = record,
                                onClick = { onEditClick(record.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HeaderOtoCare() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_otocare),
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier.size(90.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(R.string.app_description),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun EmptyContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Belum ada riwayat servis.",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Tekan tombol + untuk menambahkan data servis kendaraan.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ServiceListItem(
    record: ServiceRecord,
    onClick: () -> Unit
) {
    val nextDate = hitungTanggalBerikutnya(record.lastServiceDate, record.intervalMonth)
    val remainingDays = hitungSisaHari(record.lastServiceDate, record.intervalMonth)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = record.vehicleName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = record.plateNumber,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Jenis servis: ${record.serviceType}",
                style = MaterialTheme.typography.bodySmall
            )

            Text(
                text = "Terakhir servis: ${record.lastServiceDate}",
                style = MaterialTheme.typography.bodySmall
            )

            Text(
                text = "Servis berikutnya: $nextDate",
                style = MaterialTheme.typography.bodySmall
            )

            Text(
                text = remainingDays,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun ServiceGridItem(
    record: ServiceRecord,
    onClick: () -> Unit
) {
    val nextDate = hitungTanggalBerikutnya(record.lastServiceDate, record.intervalMonth)
    val remainingDays = hitungSisaHari(record.lastServiceDate, record.intervalMonth)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = record.vehicleName,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = record.plateNumber,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = record.serviceType,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = "Next: $nextDate",
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = remainingDays,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

fun hitungTanggalBerikutnya(
    tanggalTerakhir: String,
    intervalBulan: Int
): String {
    return try {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val tanggal = LocalDate.parse(tanggalTerakhir, formatter)
        tanggal.plusMonths(intervalBulan.toLong()).format(formatter)
    } catch (_: Exception) {
        "-"
    }
}

fun hitungSisaHari(
    tanggalTerakhir: String,
    intervalBulan: Int
): String {
    return try {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val tanggal = LocalDate.parse(tanggalTerakhir, formatter)
        val tanggalBerikutnya = tanggal.plusMonths(intervalBulan.toLong())
        val hari = ChronoUnit.DAYS.between(LocalDate.now(), tanggalBerikutnya)

        if (hari < 0) {
            "Sudah lewat ${kotlin.math.abs(hari)} hari"
        } else {
            "$hari hari lagi"
        }
    } catch (_: Exception) {
        "Tanggal tidak valid"
    }
}

@Composable
fun ErrorContent(
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Gagal memuat data dari server"
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Button(
            onClick = onRetry
        ) {
            Text("Coba Lagi")
        }
    }
}