package com.example.ims3000.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ims3000.R
import com.example.ims3000.api.util.Resource
import com.example.ims3000.data.remote.MowerStatus
import com.example.ims3000.databinding.FragmentMowerStatusBinding
import com.example.ims3000.ui.viewmodels.MowerStatusViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class MowerStatusFragment : Fragment(R.layout.fragment_mower_status) {

    private var _binding: FragmentMowerStatusBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: MowerStatusViewModel

    companion object {
        const val mowerDefaultName = "Mower's Name"
        const val mowerId = 2
        val AUTONOMOUS = MowerStatus("AUTONOMOUS")
        val STANDBY = MowerStatus("STANDBY")
        val MANUAL = MowerStatus("MANUAL")
        val DIAGNOSTIC = MowerStatus("DIAGNOSTIC")
    }

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

        if (binding.mowerName.text == mowerDefaultName) {
            fetchMowerFromViewModelById(mowerId)
            viewModel.getMowerById.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Success -> response.data?.let { result ->
                        binding.mowerName.text = result.name
                    }
                }
            }
        }

        binding.startPauseButton.setOnClickListener {
            viewModel.updateMowerStatusById(mowerId, AUTONOMOUS)
        }

        binding.parkButton.setOnClickListener {
            viewModel.updateMowerStatusById(mowerId, STANDBY)
        }

        binding.parkButton.setOnClickListener {
            viewModel.updateMowerStatusById(mowerId, DIAGNOSTIC)
        }

    }

    private fun fetchAllUsersFromViewModel() {
        try {
            viewModel.getAllUsers()

        } catch (e: Exception) { }
    }

    private fun fetchMoverNameFromViewModel() {
        try {
            viewModel.getAllMowers()
        } catch (e: Exception) {}
    }

    private fun fetchMowerFromViewModelById(id: Int) {
        try {
            viewModel.getMowerById(id)
        } catch (e: Exception) {}
    }

}