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
import com.example.ims3000.databinding.FragmentHomeBinding
import com.example.ims3000.ui.viewmodels.HomeViewModel
import com.example.ims3000.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())?.get(HomeViewModel::class.java)

        binding.apiCallButtonTest.setOnClickListener {
            mockApiCall()
        }

        viewModel.getText.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> response.data?.let { apiResponse ->
                    binding.apidatatext.text = apiResponse?.title.toString()
                }
            }

        }
    }

    private fun mockApiCall() {
        try {
            viewModel.getText()
//            val baseText = binding.apidatatext.text.toString()
            //binding.

        } catch (e: Exception) {
        }

/*        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.getTodo(1)
                if (response.isSuccessful) {
                    val todo = response.body() as mockApiData
                    val baseText = binding.apidatatext.text.toString()
                    val apiText = todo.title
                    lifecycleScope.launch(Dispatchers.Main) {
                        binding.apidatatext.text = baseText + " " + apiText
                    }
                }
            } catch (e: Exception) {
                Log.d("debug", "error")
            }
        }*/
    }

//    private fun initRe

}