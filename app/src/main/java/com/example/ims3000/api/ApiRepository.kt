package com.example.ims3000.api

import android.util.Log
import com.example.ims3000.api.util.Resource
import com.example.ims3000.data.remote.*
import kotlinx.coroutines.awaitAll
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

    /*
    *
    *  This function establishes the Http request to the backend which allows us to update the status of the mower.
    *
    *  This satisfies LLNR3.
    *
     */

    suspend fun updateMowerStatusById(id: Int, status: MowerStatus): Resource<MowerStatus> {
        return mowerStatusUpdateResponse(apiInterface.updateMowerStatusById(id, status))
    }
    private fun mowerStatusUpdateResponse(response: Response<MowerStatus>): Resource<MowerStatus> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    /*
    *
    *  This function is used to fetch location data.
    *
    *
    *  This satisfies LLNR6.
    *
     */

    suspend fun getMowerLocationById(id: Int): Resource<List<MowerLocation>> {
        return getMowerLocationResponse(apiInterface.getMowerLocationById(id))
    }
    private fun getMowerLocationResponse(response: Response<List<MowerLocation>>): Resource<List<MowerLocation>> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    // Backup if BT fails.
    /*
    suspend fun updateMowerDirectionById(id: Int, status: MowerDirection): Resource<MowerDirection> {
        return mowerDirectionUpdateResponse(apiInterface.updateMowerDirectionById(id, status))
    }
    private fun mowerDirectionUpdateResponse(response: Response<MowerDirection>): Resource<MowerDirection> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
    */

}