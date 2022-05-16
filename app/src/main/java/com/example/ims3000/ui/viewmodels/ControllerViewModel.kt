package com.example.ims3000.ui.viewmodels

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.ims3000.api.ApiRepository
import com.example.ims3000.ui.fragments.ControllerFragment
import com.example.ims3000.ui.fragments.ControllerFragment.Companion.btSocket
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*
import javax.inject.Inject


class ControllerViewModel(application: Application) : AndroidViewModel(application) {

    // should contain interactions

    private val myUUID = UUID.fromString("94f39d29-7d6d-437d-973b-fba39e49d4ee")
    private var inputStream : InputStream? = null
    private var outputStream : OutputStream? = null
    private val btDevice : BluetoothDevice? = null


    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            ControllerFragment.PERMISSION_REQUEST_COARSE_LOCATION -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    println("coarse location permission granted")
                } else {
                    val builder = AlertDialog.Builder(getApplication())
                    builder.setTitle("Functionality limited")
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover BLE beacons")
                    builder.setPositiveButton(android.R.string.ok, null)
                    builder.setOnDismissListener { }
                    builder.show()
                }
                return
            }
        }
    }

    @Override
    fun onActivityResult(requestCode: Int, resultCode: Int) {
        if (requestCode == ControllerFragment.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                // Bluetooth was enabled
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // Bluetooth was not enabled
            }
        }
    }

    open fun InitializeSocket(){
        try {
            if (ActivityCompat.checkSelfPermission(getApplication(),
                    Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED
            )
                btSocket = btDevice?.createRfcommSocketToServiceRecord(myUUID)

        } catch (e: IOException) {
            //Error
            Log.e("Socket Error", e.toString())
        }

        try {
            btSocket?.connect()
        } catch (connEx: IOException) {
            try {
                btSocket?.close()
            } catch (closeException: IOException) {
                //Error
            }
        }

        if (btSocket != null && btSocket!!.isConnected) {
            //Socket is connected, now we can obtain our IO streams
        }

        try {
            inputStream = btSocket?.inputStream
            outputStream = btSocket?.outputStream
        } catch (e: IOException) {
            //Error
        }

    }

    open fun write(bytes: Int?) {
        try {
            outputStream?.write(bytes!!)
        } catch (e: IOException) {
            //Error
        }
    }
}