package com.example.ims3000.api

import com.example.ims3000.api.util.Resource
import retrofit2.Response
import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiInterface: ApiInterface) {
    // TODO
    // Handle the response of the API and serialize the data thanks the next function
    suspend fun getText(user: Int): Resource<ApiResponse> {
        return responseToResource(apiInterface.getTodo(user))
    }

    private fun responseToResource(response: Response<ApiResponse>): Resource<ApiResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}