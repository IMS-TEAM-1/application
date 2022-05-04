package com.example.ims3000.ui.fragments

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.bluetooth.le.BluetoothLeScanner
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ims3000.R
import com.example.ims3000.databinding.FragmentControllerBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*


@AndroidEntryPoint
open class ControllerFragment : Fragment(R.layout.fragment_controller) {

    //lateinit var viewModel: HomeViewModel
    private var _binding: FragmentControllerBinding? = null
    private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    private val myUUID = UUID.fromString("94f39d29-7d6d-437d-973b-fba39e49d4ee")
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        if (!btAdapter?.isEnabled!!) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        }

        val discoverableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,
            DISCOVERABLE_DURATION)
        startActivityForResult(discoverableIntent, REQUEST_DISCOVERABLE_BT)

        _binding = FragmentControllerBinding.inflate(inflater, container, false)
        return binding.root

    }
    companion object {
        private const val REQUEST_ENABLE_BT = 1
        private const val REQUEST_DISCOVERABLE_BT = 2
        private const val DISCOVERABLE_DURATION = 120
        private const val PERMISSION_REQUEST_COARSE_LOCATION = 1
        private var btManager: BluetoothManager? = null
        private var btAdapter: BluetoothAdapter? = null
        private var btSocket : BluetoothSocket? = null
        private var inputStream : InputStream? = null
        private var outputStream : OutputStream? = null
    }


    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                // Bluetooth was enabled
            } else if (resultCode == RESULT_CANCELED) {
                // Bluetooth was not enabled
            }
        }
    }
    private fun InitializeSocket(btDevice : BluetoothDevice){
        try {
            btSocket = btDevice.createRfcommSocketToServiceRecord(myUUID);
        } catch (IOException e) {
            //Error
        }

        try {
            btSocket.connect()
        } catch (connEx: IOException) {
            try {
                btSocket.close()
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
    open fun write(bytes: ByteArray?) {
        try {
            outputStream?.write(bytes)
        } catch (e: IOException) {
            //Error
        }
    }


}