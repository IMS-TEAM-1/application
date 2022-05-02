package com.example.ims3000.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ims3000.api.ApiRepository
import com.example.ims3000.api.ApiResponse
import com.example.ims3000.api.util.Resource
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
}