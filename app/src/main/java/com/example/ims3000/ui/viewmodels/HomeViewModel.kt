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
class HomeViewModel @Inject constructor(private val apiRepository: ApiRepository): ViewModel() {

    val getText: MutableLiveData<Resource<ApiResponse>> = MutableLiveData()

    fun getText() = viewModelScope.launch(Dispatchers.IO) {
        getText.postValue(Resource.Loading())
        try {
            val apiResult = apiRepository.getText(1)
            getText.postValue(apiResult)
        } catch (e: Exception) {
            getText.postValue(Resource.Error(e.message.toString()))
        }
    }

}