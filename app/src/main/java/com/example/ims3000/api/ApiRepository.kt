package com.example.ims3000.api

import com.example.ims3000.api.util.Resource
import retrofit2.Response
import javax.inject.Inject

class ApiRepository @Inject constructor(private val ApiInterface: ApiInterface) {
    // TODO
    // Handle the response of the API and serialize the data thanks the next function

    private fun responseToResource(response: Response<ApiResponse>): Resource<ApiResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}