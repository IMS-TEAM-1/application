package com.example.ims3000.api

import com.example.ims3000.data.remote.*
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
        @Body status: MowerStatus
    ): Response<MowerStatus>

    @GET("mowers/{id}/locations")
    suspend fun getMowerLocationById(@Path("id") id: Int): Response<List<MowerLocation>>

    @POST("mowers/{id}/direction")
    suspend fun updateMowerDirectionById(
        @Path("id") id: Int,
        @Body status: MowerDirection
    ): Response<MowerDirection>

}