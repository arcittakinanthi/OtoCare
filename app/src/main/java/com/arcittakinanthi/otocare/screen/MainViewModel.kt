package com.arcittakinanthi.otocare.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arcittakinanthi.otocare.database.ServiceDao
import com.arcittakinanthi.otocare.model.ServiceRecord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val dao: ServiceDao) : ViewModel() {

    val data = dao.getAllService().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    suspend fun getService(id: Long): ServiceRecord? {
        return dao.getServiceById(id)
    }

    fun insert(
        vehicleName: String,
        plateNumber: String,
        serviceType: String,
        lastServiceDate: String,
        intervalMonth: Int
    ) {
        val record = ServiceRecord(
            vehicleName = vehicleName,
            plateNumber = plateNumber,
            serviceType = serviceType,
            lastServiceDate = lastServiceDate,
            intervalMonth = intervalMonth
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(record)
        }
    }

    fun update(
        id: Long,
        vehicleName: String,
        plateNumber: String,
        serviceType: String,
        lastServiceDate: String,
        intervalMonth: Int
    ) {
        val record = ServiceRecord(
            id = id,
            vehicleName = vehicleName,
            plateNumber = plateNumber,
            serviceType = serviceType,
            lastServiceDate = lastServiceDate,
            intervalMonth = intervalMonth
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.update(record)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }
}