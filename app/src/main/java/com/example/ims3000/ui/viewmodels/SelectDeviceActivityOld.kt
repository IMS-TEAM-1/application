package com.example.ims3000.ui.viewmodels

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.ims3000.R
import com.example.ims3000.databinding.ActivityMainBinding
import com.example.ims3000.ui.fragments.ControllerFragment
import java.util.*
import kotlin.collections.ArrayList


class SelectDeviceActivityOld : AppCompatActivity() {

    private var m_bluetoothAdapter: BluetoothAdapter? = null
    private lateinit var m_pairedDevices: Set<BluetoothDevice>
    private val REQUEST_ENABLE_BLUETOOTH = 1
    private var isBound = false

    companion object {
        var myService: BluetoothService? = null
        var m_myUUID: UUID = UUID.fromString("94f39d29-7d6d-437d-973b-fba39e49d4ee")
        var m_bluetoothSocket: BluetoothSocket? = null
        lateinit var m_progress: ProgressDialog
        lateinit var m_bluetoothAdapter: BluetoothAdapter
        var m_isConnected: Boolean = false
        lateinit var m_address: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_device_layout)
        // Make sure bluetooth is enabled before loading the rest of the UI
        m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (m_bluetoothAdapter == null ) {
            return
        }
        if(!m_bluetoothAdapter!!.isEnabled) {
            val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BLUETOOTH)
        }

        // Bind to the bluetooth service
        val intent = Intent(this, BluetoothService::class.java)
        bindService(intent, myConnection, Context.BIND_AUTO_CREATE)

        // Set up UI onClickListener for the SelectDeviceActivity::Refresh button
        val refreshButton = findViewById<Button>(R.id.select_device_refresh)
        refreshButton.setOnClickListener{ pairedDeviceList() }
    }

    private val myConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as BluetoothService.MyLocalBinder
            myService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }
    }

    private fun pairedDeviceList() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        m_pairedDevices = m_bluetoothAdapter!!.bondedDevices
        val list : ArrayList<BluetoothDevice> = ArrayList()
        val nameList : ArrayList<String> = ArrayList()

        if (!m_pairedDevices.isEmpty()) {
            for (device: BluetoothDevice in m_pairedDevices) {
                list.add(device)
                nameList.add(device.name)
                Log.i("device", ""+device)
                Log.i("-->name: ", ""+device.name)
            }
        } else {
        }
        val deviceList = findViewById<ListView>(R.id.select_device_list)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, nameList)
        deviceList.adapter = adapter
        deviceList.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val device: BluetoothDevice = list[position]
            m_address = device.address
            // Tell service to go connect to device
            myService?.connectToDevice(m_address)
            // Start AsyncTask to wait for service to complete
            WaitForConnect(this).execute()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ENABLE_BLUETOOTH) {
            if (resultCode == Activity.RESULT_OK) {
                if (m_bluetoothAdapter!!.isEnabled) {

                } else {

                }
            } else if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }

    private class WaitForConnect(c: Context) : AsyncTask<Void, Void, String>() {
        private var connectSuccess: Boolean = true
        private val context: Context

        init {
            this.context = c
        }

        override fun onPreExecute() {
            super.onPreExecute()
            m_progress = ProgressDialog.show(context, "Bluetooth", "Connecting...")
        }

        override fun doInBackground(vararg p0: Void?): String? {
            while ( true ) {
                if (false == myService?.getConnectStatus()) {
                    Thread.sleep(250)
                } else {
                    return null
                }
            }
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            m_progress.dismiss()

            if ( false == myService?.getConnected()) {
                Log.i("data", "couldn't connect")
                Toast.makeText(this.context, "Failed to connect!", Toast.LENGTH_LONG).show()
            } else {
                m_isConnected = true
                Log.i("data", "Connected!")
                Toast.makeText(this.context, "Connected!", Toast.LENGTH_LONG).show()
                context.startActivity(Intent(context, ControllerFragment::class.java))
            }
        }
    }
}