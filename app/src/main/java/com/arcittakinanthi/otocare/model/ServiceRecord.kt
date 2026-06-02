package com.arcittakinanthi.otocare.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "service_record")
data class ServiceRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val vehicleName: String,
    val plateNumber: String,
    val serviceType: String,
    val lastServiceDate: String,
    val intervalMonth: Int
)