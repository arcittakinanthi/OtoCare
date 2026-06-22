package com.arcittakinanthi.otocare.network

import retrofit2.http.GET
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @GET("service")
    suspend fun getServices(): List<ServiceResponse>

    @POST("service")
    suspend fun addService(
        @Body service: ServiceResponse
    ): ServiceResponse
}