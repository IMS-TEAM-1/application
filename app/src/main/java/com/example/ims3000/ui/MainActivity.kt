package com.example.ims3000.ui

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import com.bumptech.glide.RequestManager
import com.example.ims3000.R
import com.example.ims3000.databinding.ActivityMainBinding
import com.example.ims3000.ui.fragments.ControllerFragment
import com.example.ims3000.ui.fragments.MapFragment
import com.example.ims3000.ui.fragments.MowerStatusFragment
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.EasyPermissions
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    @Inject
    lateinit var glide: RequestManager
    var btConnectThread: BtThread? = null


    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestPermissions()
        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED
        ) {
            Manifest.permission.ACCESS_COARSE_LOCATION
            Manifest.permission.ACCESS_FINE_LOCATION
            Manifest.permission.BLUETOOTH_CONNECT
            Manifest.permission.BLUETOOTH_SCAN
            Manifest.permission.BLUETOOTH_ADMIN
            Manifest.permission.BLUETOOTH_ADVERTISE
            Manifest.permission.BLUETOOTH
        }

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

    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout, fragment)
            commit()
        }

    companion object {
        private val MACADRESS = "E4:5F:01:92:B8:BE"
        internal const val REQUEST_CODE_LOCATION_PERMISSION = 0
        private const val REQUEST_ENABLE_BT = 1
        private val myUUID = UUID.fromString("94f39d29-7d6d-437d-973b-fba39e49d4ee")
    }

    @SuppressLint("MissingPermission")
    fun startManual(){
        val btManager:BluetoothManager = getSystemService(BluetoothManager::class.java)
        val btAdapter:BluetoothAdapter = btManager.adapter
        if(btAdapter==null){
            // Prevent btAdapter being null.
        }
        else{
            if (!btAdapter.isEnabled){
                val  btOn = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(btOn, REQUEST_ENABLE_BT)
            }
            else{
                if (btConnectThread !=null && btConnectThread!!.btSocket?.isConnected == true){
                    Log.e("connect", "we are connected RETURN")

                }else{
                    val btDevice = btAdapter.getRemoteDevice(MACADRESS)
                    btConnectThread = BtThread(btDevice)
                    btConnectThread!!.run(btAdapter)
                }

            }
        }
    }
    fun writeToMower(data: Char){

        if (btConnectThread!=null && btConnectThread!!.isConnected()){
            btConnectThread!!.write(data.code)
        }
        else{
            Log.e("error", "Socket closed")
        }
    }
    fun stopManual(){
        try {
            if(btConnectThread?.btSocket == null){
                Log.e("Socket","PreventCrash")
            }else{
                btConnectThread!!.cancel()
                Log.e("Socket","Closed")
            }
        }
        catch (e:Exception){
            //Error
        }



    }

    @SuppressLint("MissingPermission")
    inner class BtThread(btDevice: BluetoothDevice): Thread(){
        lateinit var inputStream : InputStream
        lateinit var outputStream : OutputStream

        val btSocket: BluetoothSocket? by lazy(LazyThreadSafetyMode.NONE){
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

                Log.e("Socket Error", e.toString())
            }
        }
        fun cancel() {
            try {
                if (btSocket != null){
                    btSocket!!.close()
                }
                else{
                    Log.e("btSocket","btSocket is null")
                }

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

    @RequiresApi(Build.VERSION_CODES.S)
    private fun requestPermissions() {
        if(TrackingUtility.hasLocationPermissions(this)) {
            return
        }
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept location permissions to use this app.",
                MainActivity.REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.BLUETOOTH_SCAN,
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