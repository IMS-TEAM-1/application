package com.example.ims3000.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.ims3000.R
import com.example.ims3000.data.remote.MowerStatus
import com.example.ims3000.databinding.FragmentControllerBinding
import com.example.ims3000.ui.MainActivity
import com.example.ims3000.ui.viewmodels.ControllerViewModel
import com.example.ims3000.ui.TrackingUtility
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.EasyPermissions


@AndroidEntryPoint
class ControllerFragment : Fragment(R.layout.fragment_controller),EasyPermissions.PermissionCallbacks {

    companion object {
        private const val mowerId = 1
        private val STANDBY = MowerStatus("STANDBY")
        private val MANUAL = MowerStatus("MANUAL")

        private val FORWARD = Char(1)
        private val BACKWARD = Char(3)
        private val RIGHT = Char(2)
        private val LEFT = Char(4)
        private val STOP = Char(0)
    }

    lateinit var viewModel: ControllerViewModel
    private var _binding: FragmentControllerBinding? = null
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): ConstraintLayout {
        requestPermissions()

        _binding = FragmentControllerBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED
        ) {
            Manifest.permission.ACCESS_COARSE_LOCATION
            Manifest.permission.ACCESS_FINE_LOCATION
            Manifest.permission.BLUETOOTH_CONNECT
            Manifest.permission.BLUETOOTH_ADMIN
            Manifest.permission.BLUETOOTH_ADVERTISE
            Manifest.permission.BLUETOOTH
        }

        binding.forwardButton.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                (activity as MainActivity?)?.writeToMower(FORWARD)
            }
            else if (event.action == MotionEvent.ACTION_UP) {
                (activity as MainActivity?)?.writeToMower(STOP)
            }
            false
        }

        binding.backwardButton.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                (activity as MainActivity?)?.writeToMower(BACKWARD)
            }
            else if (event.action == MotionEvent.ACTION_UP) {
                (activity as MainActivity?)?.writeToMower(STOP)
            }
            false
        }

        binding.turnRightButton.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                (activity as MainActivity?)?.writeToMower(RIGHT)
            }
            else if (event.action == MotionEvent.ACTION_UP) {
                (activity as MainActivity?)?.writeToMower(STOP)
            }
            false
        }

        binding.turnLeftButton.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                (activity as MainActivity?)?.writeToMower(LEFT)
            } else if (event.action == MotionEvent.ACTION_UP) {
                (activity as MainActivity?)?.writeToMower(STOP)
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

    @RequiresApi(Build.VERSION_CODES.S)
    private fun requestPermissions() {
        if(TrackingUtility.hasLocationPermissions(requireContext())) {
            return
        }
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept location permissions to use this app.",
                MainActivity.REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.BLUETOOTH_ADVERTISE,
                Manifest.permission.BLUETOOTH,
            )
        } else {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept location permissions to use this app.",
                MainActivity.REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.BLUETOOTH_ADVERTISE,
                Manifest.permission.BLUETOOTH

            )
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            //AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermissions()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

}