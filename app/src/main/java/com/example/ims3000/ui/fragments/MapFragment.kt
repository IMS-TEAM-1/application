package com.example.ims3000.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ims3000.R
import com.example.ims3000.api.util.Resource
import com.example.ims3000.databinding.FragmentMapBinding
import com.example.ims3000.ui.viewmodels.MapViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class MapFragment : Fragment(R.layout.fragment_map) {

    lateinit var viewModel: MapViewModel
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())?.get(MapViewModel::class.java)

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
            val baseText = binding.apidatatext.text.toString()
        } catch (e: Exception) { }
    }
}