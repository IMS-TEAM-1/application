package com.example.ims3000.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ims3000.R
import com.example.ims3000.data.entities.Coordinates
import com.example.ims3000.data.remote.MowerDirection
import com.example.ims3000.data.remote.MowerStatus
import com.example.ims3000.databinding.FragmentControllerBinding
import com.example.ims3000.ui.viewmodels.ControllerViewModel
import com.example.ims3000.ui.viewmodels.MapViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ControllerFragment : Fragment(R.layout.fragment_controller) {

    lateinit var viewModel: ControllerViewModel
    private var _binding: FragmentControllerBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val mowerId = 1

        private val directionForward = MowerDirection("FORWARD")
        private val directionBackwards = MowerDirection("BACKWARD")
        private val directionLeft = MowerDirection("LEFT")
        private val directionRight = MowerDirection("RIGHT")
        private val STOP = MowerDirection("STOP")

        private val MANUAL = MowerStatus("MANUAL")
        private val STANDBY = MowerStatus("STANDBY")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentControllerBinding.inflate(inflater, container, false)
        return binding.root

    }
    //We suppress this error because Buttons want to provide accessibility to blind people
    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[ControllerViewModel::class.java]

        binding.forwardButton.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                Log.d("direction", "forward DOWN")
                viewModel.updateMowerDirectionById(mowerId, directionForward)
            }
            if (event.action == MotionEvent.ACTION_UP) {
                Log.d("direction", "forward UP")
                viewModel.updateMowerDirectionById(mowerId, STOP)
            }
            false
        }

        binding.backwardButton.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                viewModel.updateMowerDirectionById(mowerId, directionBackwards)
            }
            if (event.action == MotionEvent.ACTION_UP) {
                viewModel.updateMowerDirectionById(mowerId, STOP)
            }
            false
        }

        binding.turnLeftButton.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                viewModel.updateMowerDirectionById(mowerId, directionLeft)
            }
            if (event.action == MotionEvent.ACTION_UP) {
                viewModel.updateMowerDirectionById(mowerId, STOP)
            }
            false
        }

        binding.turnRightButton.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                viewModel.updateMowerDirectionById(mowerId, directionRight)
            }
            if (event.action == MotionEvent.ACTION_UP) {
                viewModel.updateMowerDirectionById(mowerId, STOP)
            }
            false
        }

        binding.manualStart.setOnClickListener {
            viewModel.updateMowerStatusById(mowerId, MANUAL)
        }

        binding.manualStop.setOnClickListener {
            viewModel.updateMowerStatusById(mowerId, STANDBY)
        }
    }
}