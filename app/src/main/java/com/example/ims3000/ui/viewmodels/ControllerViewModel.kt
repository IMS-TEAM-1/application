package com.example.ims3000.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.ims3000.api.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ControllerViewModel @Inject constructor(private val apiRepository: ApiRepository): ViewModel() {

    // should contain interactions

}