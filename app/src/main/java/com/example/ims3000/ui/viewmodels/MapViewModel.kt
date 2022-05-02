package com.example.ims3000.ui.viewmodels

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.view.View
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
class MapViewModel @Inject constructor(private val apiRepository: ApiRepository): ViewModel() {

}