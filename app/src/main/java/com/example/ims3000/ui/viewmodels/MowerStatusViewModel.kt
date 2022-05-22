package com.example.ims3000.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ims3000.api.ApiRepository
import com.example.ims3000.api.ApiResponse
import com.example.ims3000.api.util.Resource
import com.example.ims3000.data.remote.Mower
import com.example.ims3000.data.remote.MowerStatus
import com.example.ims3000.data.remote.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MowerStatusViewModel @Inject constructor(private val apiRepository: ApiRepository): ViewModel() {

    val getAllUsers: MutableLiveData<Resource<List<User>>> = MutableLiveData()
    fun getAllUsers() = viewModelScope.launch(Dispatchers.IO) {
        getAllUsers.postValue(Resource.Loading())
        try {
            val apiResult = apiRepository.getAllUsers()
            getAllUsers.postValue(apiResult)
        } catch (e: Exception) {
            getAllUsers.postValue(Resource.Error(e.message.toString()))
        }
    }

    val getAllMowers: MutableLiveData<Resource<List<Mower>>> = MutableLiveData()
    fun getAllMowers() = viewModelScope.launch(Dispatchers.IO) {
        getAllMowers.postValue(Resource.Loading())
        try {
            val apiResult = apiRepository.getAllMowers()
            getAllMowers.postValue(apiResult)
        } catch (e: Exception) {
            getAllMowers.postValue(Resource.Error(e.message.toString()))
        }
    }

    val getMowerById: MutableLiveData<Resource<Mower>> = MutableLiveData()
    fun getMowerById(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        getMowerById.postValue(Resource.Loading())
        try {
            val apiResult = apiRepository.getMowerById(id)
            getMowerById.postValue(apiResult)
        } catch (e: Exception) {
            getMowerById.postValue(Resource.Error(e.message.toString()))
        }
    }

    /*
    *
    *  This function establishes the Http request to the backend which allows us to update the status of the mower.
    *
    *  This satisfies LLNR3.
    *
     */

    val mowerStatus: MutableLiveData<Resource<MowerStatus>> = MutableLiveData()
    fun updateMowerStatusById(id: Int, status: MowerStatus) = viewModelScope.launch(Dispatchers.IO) {
        mowerStatus.postValue(Resource.Loading())
        try {
            apiRepository.updateMowerStatusById(id, status)
        } catch (e: Exception) {
            mowerStatus.postValue(Resource.Error(e.message.toString()))
        }
    }

}