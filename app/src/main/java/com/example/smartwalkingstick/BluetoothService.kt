package com.example.smartwalkingstick

import android.Manifest
import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import android.util.Log
import androidx.core.app.ActivityCompat
import java.io.IOException
import java.util.*

class BluetoothService : Service() {

    private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    private var bluetoothSocket: BluetoothSocket? = null

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        connectToDevice("00:11:22:33:44:55") // Replace with the actual device address
        return START_STICKY
    }

    private fun connectToDevice(address: String) {
        val device: BluetoothDevice? = bluetoothAdapter?.getRemoteDevice(address)
        try {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            bluetoothSocket = device?.createRfcommSocketToServiceRecord(UUID.randomUUID())
            bluetoothSocket?.connect()
            Log.d("BluetoothService", "Connected to device")
        } catch (e: IOException) {
            Log.e("BluetoothService", "Connection failed", e)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bluetoothSocket?.close()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}