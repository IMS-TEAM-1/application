package com.example.ims3000.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ims3000.api.ApiRepository
import com.example.ims3000.api.util.Resource
import com.example.ims3000.data.remote.MowerDirection
import com.example.ims3000.data.remote.MowerStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ControllerViewModel @Inject constructor(private val apiRepository: ApiRepository): ViewModel() {

    val mowerDirection: MutableLiveData<Resource<MowerDirection>> = MutableLiveData()
    fun updateMowerDirectionById(id: Int, status: MowerDirection) = viewModelScope.launch(Dispatchers.IO) {
        mowerDirection.postValue(Resource.Loading())
        try {
            apiRepository.updateMowerDirectionById(id, status)
        } catch (e: Exception) {
            mowerDirection.postValue(Resource.Error(e.message.toString()))
        }
    }

}