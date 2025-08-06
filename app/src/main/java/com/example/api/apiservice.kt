package com.example.api


import com.example.api.MedicineApi
import com.example.model.Medicine
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body

interface ApiService {

    @GET("medicines")
    fun getMedicines(): Call<List<Medicine>>

    @POST("addMedicine")
    fun addMedicine(@Body medicine: Medicine): Call<Medicine>
}