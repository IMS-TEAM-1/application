package com.example.ims3000.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ims3000.R
import com.example.ims3000.data.entities.Coordinates
import com.example.ims3000.databinding.FragmentControllerBinding
import com.example.ims3000.ui.viewmodels.ControllerViewModel
import com.example.ims3000.ui.viewmodels.MapViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ControllerFragment : Fragment(R.layout.fragment_controller) {

    lateinit var viewModel: ControllerViewModel
    private var _binding: FragmentControllerBinding? = null
    private val binding get() = _binding!!

    private val mowerId = 1
    private val mowerDirection = mutableListOf<Coordinates>()

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
                //Send direction (forward)
            }
            if (event.action == MotionEvent.ACTION_UP) {
                //Send stop
            }
            false
        }

        binding.backwardButton.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                //Send direction (backward)
            }
            if (event.action == MotionEvent.ACTION_UP) {
                //Send stop
            }
            false
        }

        binding.turnLeftButton.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                //Send direction (left)
            }
            if (event.action == MotionEvent.ACTION_UP) {
                //Send stop
            }
            false
        }

        binding.turnRightButton.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                //Send direction (right)
            }
            if (event.action == MotionEvent.ACTION_UP) {
                //Send stop
            }
            false
        }

        binding.manualStart.setOnClickListener {

        }

        binding.manualStop.setOnClickListener {

        }
    }
}