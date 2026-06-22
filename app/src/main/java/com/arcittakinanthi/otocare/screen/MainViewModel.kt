package com.arcittakinanthi.otocare.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arcittakinanthi.otocare.database.ServiceDao
import com.arcittakinanthi.otocare.model.ServiceRecord
import com.arcittakinanthi.otocare.network.OtoCareApi
import com.arcittakinanthi.otocare.network.ServiceResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val dao: ServiceDao
) : ViewModel() {

    val data = dao.getAllService().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    private val _status =
        MutableStateFlow(ApiStatus.LOADING)

    val status =
        _status.asStateFlow()

    private val _apiData =
        MutableStateFlow<List<ServiceResponse>>(emptyList())

    val apiData =
        _apiData.asStateFlow()

    init {
        retrieveData()
    }

    fun retrieveData() {

        viewModelScope.launch {

            _status.value =
                ApiStatus.LOADING

            try {

                _apiData.value =
                    OtoCareApi.service.getServices()

                _status.value =
                    ApiStatus.SUCCESS

            } catch (e: Exception) {

                _status.value =
                    ApiStatus.FAILED

                e.printStackTrace()
            }
        }
    }

    suspend fun getService(id: Long): ServiceRecord? {
        return dao.getServiceById(id)
    }

    fun insert(
        vehicleName: String,
        plateNumber: String,
        serviceType: String,
        lastServiceDate: String,
        intervalMonth: Int,
        imageUri: String
    ) {

        val record = ServiceRecord(
            vehicleName = vehicleName,
            plateNumber = plateNumber,
            serviceType = serviceType,
            lastServiceDate = lastServiceDate,
            intervalMonth = intervalMonth,
            imageUri = imageUri
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
        intervalMonth: Int,
        imageUri: String
    ) {

        val record = ServiceRecord(
            id = id,
            vehicleName = vehicleName,
            plateNumber = plateNumber,
            serviceType = serviceType,
            lastServiceDate = lastServiceDate,
            intervalMonth = intervalMonth,
            imageUri = imageUri
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