package com.example.ims3000.ui.fragments

import android.Manifest
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ims3000.R
import com.example.ims3000.databinding.FragmentControllerBinding
import com.example.ims3000.ui.viewmodels.ControllerViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ControllerFragment : Fragment(R.layout.fragment_controller) {

    private var _binding: FragmentControllerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentControllerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val forwardButton = view?.findViewById<Button>(R.id.forward_button)
        val backwardButton = view?.findViewById<Button>(R.id.backward_button)
        val turnRightButton = view?.findViewById<Button>(R.id.turn_right_button)
        val turnLeftButton = view?.findViewById<Button>(R.id.turn_left_button)

        val controllerViewModel = ViewModelProvider(this)[ControllerViewModel::class.java]
        controllerViewModel.onActivityResult(0, 0)
        controllerViewModel.InitializeSocket()

        //forwardButton?.setOnClickListener {
        //    Toast.makeText(context, "Forward", Toast.LENGTH_SHORT).show()
        //    controllerViewModel.write(1)
        //}

       forwardButton?.setOnTouchListener(OnTouchListener { v, event ->
           if (event.action == MotionEvent.ACTION_DOWN){
               controllerViewModel.write(1)
               Log.d("Pressed", "Button pressed")
           }
           else if (event.action == MotionEvent.ACTION_UP) {
           Log.d("Released", "Button released")
           controllerViewModel.write(0)
            }
           false
       })

        backwardButton?.setOnTouchListener(OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN){
                controllerViewModel.write(2)
                Log.d("Pressed", "Button pressed")
            }
            else if (event.action == MotionEvent.ACTION_UP) {
                Log.d("Released", "Button released")
                controllerViewModel.write(0)
            }
            false
        })

        turnRightButton?.setOnTouchListener(OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN){
                controllerViewModel.write(3)
                Log.d("Pressed", "Button pressed")
            }
            else if (event.action == MotionEvent.ACTION_UP) {
                Log.d("Released", "Button released")
                controllerViewModel.write(0)
            }
            false
        })

        turnLeftButton?.setOnTouchListener(OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN){
                controllerViewModel.write(4)
                Log.d("Pressed", "Button pressed")
            }
            else if (event.action == MotionEvent.ACTION_UP) {
                Log.d("Released", "Button released")
                controllerViewModel.write(0)
            }
            false
        })


    }



    companion object {
        const val REQUEST_ENABLE_BT = 1
        internal const val PERMISSION_REQUEST_COARSE_LOCATION = 1
        var btSocket : BluetoothSocket? = null
    }
}