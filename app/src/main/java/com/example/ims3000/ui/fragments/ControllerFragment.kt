package com.example.ims3000.ui.fragments


import android.Manifest
import android.R.attr.data
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.bluetooth.le.BluetoothLeScanner
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ims3000.R
import com.example.ims3000.databinding.FragmentControllerBinding
import com.example.ims3000.ui.viewmodels.ControllerViewModel
import com.example.ims3000.ui.viewmodels.TrackingUtility
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.internal.Contexts.getApplication
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*


@AndroidEntryPoint
class ControllerFragment : Fragment(R.layout.fragment_controller), EasyPermissions.PermissionCallbacks{

    private var _binding: FragmentControllerBinding? = null
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        requestPermissions()

        _binding = FragmentControllerBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setUpBluetoothManager()

        val forwardButton = view?.findViewById<Button>(R.id.forward_button)
        val backwardButton = view?.findViewById<Button>(R.id.backward_button)
        val turnRightButton = view?.findViewById<Button>(R.id.turn_right_button)
        val turnLeftButton = view?.findViewById<Button>(R.id.turn_left_button)

        //val controllerViewModel = ViewModelProvider(this)[ControllerViewModel::class.java]

        initializeSocket()


       forwardButton?.setOnTouchListener(OnTouchListener { v, event ->
           if (event.action == MotionEvent.ACTION_DOWN){
               write(1)
               Log.d("Pressed", "Button pressed")
           }
           else if (event.action == MotionEvent.ACTION_UP) {
           Log.d("Released", "Button released")
           write(0)
            }
           false
       })

        backwardButton?.setOnTouchListener(OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN){
                write(2)
                Log.d("Pressed", "Button pressed")
            }
            else if (event.action == MotionEvent.ACTION_UP) {
                Log.d("Released", "Button released")
                write(0)
            }
            false
        })

        turnRightButton?.setOnTouchListener(OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN){
                write(3)
                Log.d("Pressed", "Button pressed")
            }
            else if (event.action == MotionEvent.ACTION_UP) {
                Log.d("Released", "Button released")
                write(0)
            }
            false
        })

        turnLeftButton?.setOnTouchListener(OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN){
                write(4)
                Log.d("Pressed", "Button pressed")
            }
            else if (event.action == MotionEvent.ACTION_UP) {
                Log.d("Released", "Button released")
                write(0)
            }
            false
        })


    }

    companion object {
        internal const val REQUEST_CODE_LOCATION_PERMISSION = 0
        private const val REQUEST_ENABLE_BT = 1
        private val myUUID = UUID.fromString("94f39d29-7d6d-437d-973b-fba39e49d4ee")
        private var inputStream : InputStream? = null
        private var outputStream : OutputStream? = null
        private val btDevice : BluetoothDevice? = null
        private var btSocket : BluetoothSocket? = null
        private var btManager: BluetoothManager? = null
        private var btAdapter: BluetoothAdapter? = null
    }

    private fun setUpBluetoothManager() {
        btManager = context?.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        btAdapter = btManager!!.adapter
        if (btAdapter != null && !btAdapter!!.isEnabled) {
            val enableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT)
        }
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
                REQUEST_CODE_LOCATION_PERMISSION,
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
                REQUEST_CODE_LOCATION_PERMISSION,
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

    @RequiresApi(Build.VERSION_CODES.S)
    fun initializeSocket(){
        try {


            Log.e("WTF", "we are here" )
            if (ActivityCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED
            ) {
                Manifest.permission.ACCESS_COARSE_LOCATION
                Manifest.permission.ACCESS_FINE_LOCATION
                Manifest.permission.BLUETOOTH_CONNECT
                Manifest.permission.BLUETOOTH_ADMIN
                Manifest.permission.BLUETOOTH_ADVERTISE
                Manifest.permission.BLUETOOTH

                return
            }
            btSocket = btDevice?.createRfcommSocketToServiceRecord(myUUID)

        } catch (e: IOException) {
            //Error
            Log.e("Socket Error", e.toString())
        }

        try {
            btSocket?.connect()
        }
        catch (connEx: IOException) {
            try {
                btSocket?.close()
            } catch (closeException: IOException) {
                Log.e("socket closed", closeException.toString())
            }
        }

        if (btSocket != null && btSocket!!.isConnected) {
            //Socket is connected, now we can obtain our IO streams
            Log.e("LOL", "we are connected")
            inputStream = btSocket?.inputStream
            outputStream = btSocket?.outputStream
        }

        //try {
        //    inputStream = btSocket?.inputStream
        //    outputStream = btSocket?.outputStream
        //} catch (e: IOException) {
        //    //Error
        //}

    }

    open fun write(bytes: Int?) {
        try {
            outputStream?.write(bytes!!)
        } catch (e: IOException) {
            //Error
        }
    }

}