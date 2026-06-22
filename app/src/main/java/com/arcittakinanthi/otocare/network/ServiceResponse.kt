package com.arcittakinanthi.otocare.network

data class ServiceResponse(
    val id: String,
    val vehicleName: String,
    val plateNumber: String,
    val serviceType: String,
    val lastServiceDate: String,
    val intervalMonth: Int,
    val imageUrl: String
)