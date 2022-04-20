package com.example.ims3000.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.ims3000.R
import com.example.ims3000.api.ApiResponse
import com.example.ims3000.api.RetrofitInstance
import com.example.ims3000.api.util.Resource
import com.example.ims3000.data.entities.mockApiData
import com.example.ims3000.databinding.FragmentControllerBinding
import com.example.ims3000.databinding.FragmentHomeBinding
import com.example.ims3000.databinding.FragmentMapBinding
import com.example.ims3000.ui.viewmodels.HomeViewModel
import com.example.ims3000.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

@AndroidEntryPoint
class ControllerFragment : Fragment(R.layout.fragment_controller) {

    //lateinit var viewModel: HomeViewModel
    private var _binding: FragmentControllerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentControllerBinding.inflate(inflater, container, false)
        return binding.root

    }

}