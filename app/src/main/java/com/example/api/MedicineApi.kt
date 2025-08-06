package com.example.api

import retrofit2.Call
import retrofit2.http.*

// -------------------- DATA CLASSES --------------------

// AUTH
data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val token: String
)

// Signup uses 'name' as backend expects
data class SignupRequest(
    val name: String,
    val email: String,
    val password: String
)

// MEDICINE
data class MedicineRequest(
    val name: String,
    val expiryDate: String,
    val quantity: Int,
    val status: String // e.g., "good", "expired", "returned"
)

// make fields nullable to avoid crashes if backend returns slightly different shape
data class MedicineResponse(
    val _id: String?,
    val name: String?,
    val expiryDate: String?,
    val quantity: Int?,
    val status: String?
)

// --------- Align with ActionFragment ---------
// ActionFragment constructs: StatusUpdateRequest(medicineId = batchNo, newStatus = status)
data class StatusUpdateRequest(
    val medicineId: String,
    val newStatus: String // "returned" or "discounted"
)

// --------- Stats response used in DashboardFragment/PieChart ---------
data class StatusUpdateResponse(
    val totalStock: Int,
    val nearExpiry: Int,
    val expired: Int,
    val returned: Boolean
)

// -------------------- RETROFIT INTERFACE --------------------

// NOTE: BASE_URL in RetrofitClient should be like "http://10.0.2.2:5000/" for emulator
interface MedicineApi {

    // AUTH (server mounts auth at /api/login)
    @Headers("Content-Type: application/json")
    @POST("api/login/register")
    fun register(
        @Body request: SignupRequest
    ): Call<LoginResponse>

    @Headers("Content-Type: application/json")
    @POST("api/login/login")
    fun loginUser(
        @Body request: LoginRequest
    ): Call<LoginResponse>

    // MEDICINE endpoints
    @Headers("Content-Type: application/json")
    @POST("api/medicine/add")
    fun addMedicine(
        @Header("Authorization") token: String,
        @Body request: MedicineRequest
    ): Call<MedicineResponse>

    // Stats for dashboard (use this for PieChart)
    @GET("api/medicine/stats")
    fun getStats(
        @Header("Authorization") token: String
    ): Call<StatusUpdateResponse>

    // markStatus now uses medicineId & newStatus to match your ActionFragment
    @Headers("Content-Type: application/json")
    @POST("api/medicine/mark-status")
    fun markStatus(
        @Header("Authorization") token: String,
        @Body request: StatusUpdateRequest
    ): Call<MedicineResponse>

    @GET("api/medicine/all")
    fun getAllMedicines(
        @Header("Authorization") token: String
    ): Call<List<MedicineResponse>>
}