package com.arcittakinanthi.otocare.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.arcittakinanthi.otocare.model.ServiceRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceDao {

    @Insert
    suspend fun insert(record: ServiceRecord)

    @Update
    suspend fun update(record: ServiceRecord)

    @Query("SELECT * FROM service_record ORDER BY id DESC")
    fun getAllService(): Flow<List<ServiceRecord>>

    @Query("SELECT * FROM service_record WHERE id = :id")
    suspend fun getServiceById(id: Long): ServiceRecord?

    @Query("DELETE FROM service_record WHERE id = :id")
    suspend fun deleteById(id: Long)
}