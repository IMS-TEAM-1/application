package com.example.ims3000.ui

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.RequestManager
import com.example.ims3000.R
import com.example.ims3000.databinding.ActivityMainBinding
import com.example.ims3000.ui.fragments.ControllerFragment
import com.example.ims3000.ui.fragments.MapFragment
import com.example.ims3000.ui.fragments.MowerStatusFragment
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        private val MACADRESS = "E4:5F:01:92:B8:BE"
        internal const val REQUEST_CODE_LOCATION_PERMISSION = 0
        private const val REQUEST_ENABLE_BT = 1
        private val myUUID = UUID.fromString("94f39d29-7d6d-437d-973b-fba39e49d4ee")
    }

    @Inject
    lateinit var glide: RequestManager
    var btConnectThread: BtThread? = null
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val mowerStatusFragment = MowerStatusFragment()
        val mapFragment = MapFragment()
        val controllerFragment = ControllerFragment()

        //start with mowerStatusFragment
        makeCurrentFragment(mowerStatusFragment)

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_status -> makeCurrentFragment(mowerStatusFragment)
                R.id.navigation_map -> makeCurrentFragment(mapFragment)
                R.id.navigation_control -> makeCurrentFragment(controllerFragment)
            }
            true
        }
        val btManager: BluetoothManager = getSystemService(BluetoothManager::class.java)
        val btAdapter: BluetoothAdapter? = btManager.adapter
        if(btAdapter==null){
            // Prevent btAdapter being null.
        }
        else{
            if (!btAdapter.isEnabled){
                val  btOn = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(btOn, REQUEST_ENABLE_BT)
            }
            else{
                if (btConnectThread !=null && btConnectThread!!.isConnected()){
                    return
                }
                val btDevice = btAdapter.getRemoteDevice(MACADRESS)
                btConnectThread = BtThread(btDevice)
                btConnectThread!!.run(btAdapter)
            }
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout, fragment)
            commit()
        }

    fun writeToMower(data: Char){

        if (btConnectThread!=null && btConnectThread!!.isConnected()){
            btConnectThread!!.write(data.code)
        }
        else{
            Log.e("error", "Socket closed")
        }
    }

    @SuppressLint("MissingPermission")
    inner class BtThread(btDevice: BluetoothDevice): Thread(){
        lateinit var inputStream : InputStream
        lateinit var outputStream : OutputStream

        private val btSocket: BluetoothSocket? by lazy(LazyThreadSafetyMode.NONE){
            btDevice.createInsecureRfcommSocketToServiceRecord(myUUID)
        }

        fun isConnected():Boolean{
            return this::outputStream.isInitialized
        }

         fun run(btAdapter: BluetoothAdapter){
            try {
                Log.e("WTF", "we are here" )
                btAdapter.cancelDiscovery()

                btSocket.let { socket->
                    if (socket!=null){
                        Log.e("Connection","Connection to socket")
                        socket.connect()

                        if (socket.isConnected){
                            inputStream = socket.inputStream
                            outputStream = socket.outputStream
                        }
                    }

                }

            } catch (e: IOException) {
                //Error
                Log.e("Socket Error", e.toString())
            }
        }
        fun cancel() {
            try {
                btSocket?.close()
            } catch (error: IOException) {
                Log.e("ERROR", error.toString())
            }
        }

         fun write(bytes: Int?) {
            try {
                outputStream?.write(bytes!!)
            } catch (e: IOException) {
                //Error
            }
        }
    }

}