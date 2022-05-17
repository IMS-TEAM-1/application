package com.example.ims3000.ui.viewmodels

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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


open class ControllerViewModel(application: Application) : AndroidViewModel(application) {

    // should contain interactions

    private val myUUID = UUID.fromString("94f39d29-7d6d-437d-973b-fba39e49d4ee")
    private var inputStream : InputStream? = null
    private var outputStream : OutputStream? = null
    private val btDevice : BluetoothDevice? = null




    @RequiresApi(Build.VERSION_CODES.S)
    open fun initializeSocket(){
        try {

            if (ActivityCompat.checkSelfPermission(getApplication(),
                    Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED
            ) {
                Manifest.permission.BLUETOOTH_CONNECT
                Manifest.permission.ACCESS_COARSE_LOCATION
                Manifest.permission.ACCESS_FINE_LOCATION
                Manifest.permission.ACCESS_BACKGROUND_LOCATION

                return
            }
            Log.e("WTF", "we are here" )
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