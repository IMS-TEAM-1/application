package com.example.ims3000.api

import com.example.ims3000.api.util.Resource
import com.example.ims3000.data.remote.Mower
import com.example.ims3000.data.remote.User
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

    //IMS API
    suspend fun getAllUsers(): Resource<List<User>> {
        return usersResponseToResource(apiInterface.getAllUsers())
    }
    private fun usersResponseToResource(response: Response<List<User>>): Resource<List<User>> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    suspend fun getAllMowers(): Resource<List<Mower>> {
        return mowersResponseToResource(apiInterface.getAllMowers())
    }
    private fun mowersResponseToResource(response: Response<List<Mower>>): Resource<List<Mower>> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    suspend fun getMowerById(id: Int): Resource<Mower> {
        return mowerResponseToResource(apiInterface.getMowerById(id))
    }
    private fun mowerResponseToResource(response: Response<Mower>): Resource<Mower> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}