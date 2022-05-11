package com.example.ims3000.api

import com.example.ims3000.data.entities.mockApiData
import com.example.ims3000.data.remote.Mower
import com.example.ims3000.data.remote.MowerStatus
import com.example.ims3000.data.remote.User
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {
    // TODO
    @GET("todos/{userId}")
    suspend fun getTodo(@Path("userId") userId: Int): Response<ApiResponse>

    @GET("users")
    suspend fun getAllUsers(): Response<List<User>>

    @GET("mowers")
    suspend fun getAllMowers(): Response<List<Mower>>

    @GET("mowers/{id}")
    suspend fun getMowerById(@Path("id") id: Int): Response<Mower>

    @POST("mowers/{id}")
    suspend fun updateMowerStatusById(
        @Path("id") id: Int,
        @Body status: MowerStatus): Response<MowerStatus>
}