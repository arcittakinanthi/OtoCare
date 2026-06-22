package com.arcittakinanthi.otocare.network

import retrofit2.http.GET

interface ApiService {

    @GET("service")
    suspend fun getServices(): List<ServiceResponse>
}