package com.example.ims3000.api

import com.example.ims3000.data.entities.mockApiData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    // TODO
    @GET("todos/{userId}")
    suspend fun getTodo(@Path("userId") userId: Int): Response<mockApiData>

}