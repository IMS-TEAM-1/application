package com.example.ims3000.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ims3000.R
import com.example.ims3000.api.util.Resource
import com.example.ims3000.databinding.FragmentMowerStatusBinding
import com.example.ims3000.ui.viewmodels.MowerStatusViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class MowerStatusFragment : Fragment(R.layout.fragment_mower_status) {

    private var _binding: FragmentMowerStatusBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: MowerStatusViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMowerStatusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())?.get(MowerStatusViewModel::class.java)

        binding.startPauseButton.setOnClickListener {
            fetchAllUsersFromViewModel()
            viewModel.getAllUsers.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Success -> response.data?.let { result ->
                        Toast.makeText(context, result[0].username, Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }

    private fun fetchAllUsersFromViewModel() {
        try {
            viewModel.getAllUsers()

        } catch (e: Exception) { }
    }
}