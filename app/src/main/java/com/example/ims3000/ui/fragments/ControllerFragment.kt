package com.example.ims3000.ui.fragments


import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.ims3000.R
import com.example.ims3000.databinding.FragmentControllerBinding
import com.example.ims3000.ui.viewmodels.BluetoothService
import com.example.ims3000.ui.viewmodels.TrackingUtility
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import android.content.ServiceConnection
import android.os.IBinder
import com.example.ims3000.ui.viewmodels.SelectDeviceActivity


@AndroidEntryPoint
class ControllerFragment : Fragment(R.layout.fragment_controller), EasyPermissions.PermissionCallbacks{
    private var isBound = false
    private var _binding: FragmentControllerBinding? = null
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {


        requestPermissions()

        val intent = Intent(activity, BluetoothService::class.java)
        activity?.bindService(intent, myConnection, Context.BIND_AUTO_CREATE)

        Log.e("WTF",myService?.getConnectStatus().toString())

        _binding = FragmentControllerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setUpBluetoothManager()

        val forwardButton = view?.findViewById<Button>(R.id.forward_button)
        val backwardButton = view?.findViewById<Button>(R.id.backward_button)
        val turnRightButton = view?.findViewById<Button>(R.id.turn_right_button)
        val turnLeftButton = view?.findViewById<Button>(R.id.turn_left_button)
        val deviceListButton = view?.findViewById<Button>(R.id.device_list_button)

        //val controllerViewModel = ViewModelProvider(this)[ControllerViewModel::class.java]

        deviceListButton?.setOnClickListener(){
            val intent = Intent(context, SelectDeviceActivity::class.java)
            startActivity(intent)
        }



       forwardButton?.setOnTouchListener(OnTouchListener { v, event ->
           if (event.action == MotionEvent.ACTION_DOWN){
               sendCommand(1)
               Log.d("Pressed", "Button pressed")
           }
           else if (event.action == MotionEvent.ACTION_UP) {
           Log.d("Released", "Button released")
           sendCommand(0)
            }
           false
       })

        backwardButton?.setOnTouchListener(OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN){
                sendCommand(2)
                Log.d("Pressed", "Button pressed")
            }
            else if (event.action == MotionEvent.ACTION_UP) {
                Log.d("Released", "Button released")
                sendCommand(0)
            }
            false
        })

        turnRightButton?.setOnTouchListener(OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN){
                sendCommand(3)
                Log.d("Pressed", "Button pressed")
            }
            else if (event.action == MotionEvent.ACTION_UP) {
                Log.d("Released", "Button released")
                sendCommand(0)
            }
            false
        })

        turnLeftButton?.setOnTouchListener(OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN){
                sendCommand(4)
                Log.d("Pressed", "Button pressed")
            }
            else if (event.action == MotionEvent.ACTION_UP) {
                Log.d("Released", "Button released")
                sendCommand(0)
            }
            false
        })


    }

    companion object {
        internal const val REQUEST_CODE_LOCATION_PERMISSION = 0
        private const val REQUEST_ENABLE_BT = 1
        private var btManager: BluetoothManager? = null
        private var btAdapter: BluetoothAdapter? = null
        var myService: BluetoothService? = null
        var m_bluetoothSocket: BluetoothSocket? = null
        var m_isConnected: Boolean = false
    }

    private fun setUpBluetoothManager() {
        btManager = requireContext().getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
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
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH,
            )
        } else {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept location permissions to use this app.",
                REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.BLUETOOTH_CONNECT,
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

    private fun sendCommand(cmd: Int) {
        Log.e("WTF","send cmd")
        myService?.sendCommand(cmd)
    }
    private val myConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as BluetoothService.MyLocalBinder
            myService = binder.getService()
            isBound = true
            Log.e("Con","My connection")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }
    }

    private fun disconnect() {
        myService?.disconnect()
        activity?.unbindService(myConnection)
        activity?.finish()
    }
}