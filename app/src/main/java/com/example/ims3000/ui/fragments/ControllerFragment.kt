package com.example.ims3000.ui.fragments


import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.ims3000.R
import com.example.ims3000.databinding.FragmentControllerBinding
import com.example.ims3000.ui.MainActivity
import com.example.ims3000.ui.viewmodels.TrackingUtility
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


@AndroidEntryPoint
class ControllerFragment : Fragment(R.layout.fragment_controller),EasyPermissions.PermissionCallbacks {

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Log.e("ETF","(((HELLO CREATION)))")


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

        Log.e("ETF","(((BTN mejkz)))")

        val forwardButton = view?.findViewById<Button>(R.id.forward_button)
        val backwardButton = view?.findViewById<Button>(R.id.backward_button)
        val turnRightButton = view?.findViewById<Button>(R.id.turn_right_button)
        val turnLeftButton = view?.findViewById<Button>(R.id.turn_left_button)
        Log.e("ETF",forwardButton.toString())
        //val controllerViewModel = ViewModelProvider(this)[ControllerViewModel::class.java]



        forwardButton?.setOnTouchListener(OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                (activity as MainActivity?)?.writeToMower('1')
                Log.d("Pressed", "Button pressed")
            }
            else if (event.action == MotionEvent.ACTION_UP) {
                Log.d("Released", "Button released")
                (activity as MainActivity?)?.writeToMower('0')
            }
            false
        })

        backwardButton?.setOnTouchListener(OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                (activity as MainActivity?)?.writeToMower('3')
                Log.d("Pressed", "Button pressed")
            }
            else if (event.action == MotionEvent.ACTION_UP) {
                Log.d("Released", "Button released")
                (activity as MainActivity?)?.writeToMower('0')
            }
            false
        })

        turnRightButton?.setOnTouchListener(OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                (activity as MainActivity?)?.writeToMower('2')
                Log.d("Pressed", "Button pressed")
            }
            else if (event.action == MotionEvent.ACTION_UP) {
                Log.d("Released", "Button released")
                (activity as MainActivity?)?.writeToMower('0')
            }
            false
        })

        turnLeftButton?.setOnTouchListener(OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                (activity as MainActivity?)?.writeToMower('4')
                Log.d("Pressed", "Button pressed")
            } else if (event.action == MotionEvent.ACTION_UP) {
                Log.d("Released", "Button released")
                (activity as MainActivity?)?.writeToMower('0')
            }
            false
        })


    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun requestPermissions() {
        if(TrackingUtility.hasLocationPermissions(requireContext())) {
            return
        }
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
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
            AppSettingsDialog.Builder(this).build().show()
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