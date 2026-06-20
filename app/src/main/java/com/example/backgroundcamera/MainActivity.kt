package com.example.backgroundcamera

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val CAMERA_PERMISSION_CODE = 100
    private lateinit var statusText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusText = findViewById(R.id.status_text)
        val startBtn = findViewById<Button>(R.id.start_button)
        val stopBtn = findViewById<Button>(R.id.stop_button)

        startBtn.setOnClickListener { checkAndStartCamera() }
        stopBtn.setOnClickListener { stopCamera() }

        updateUI()
    }

    private fun checkAndStartCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
        } else {
            startCameraService()
        }
    }

    private fun startCameraService() {
        val serviceIntent = Intent(this, CameraService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent)
        } else {
            startService(serviceIntent)
        }
        statusText.text = "Status: Camera Running"
        statusText.setTextColor(ContextCompat.getColor(this, android.R.color.holo_green_dark))
        Toast.makeText(this, "Camera service started", Toast.LENGTH_SHORT).show()
    }

    private fun stopCamera() {
        stopService(Intent(this, CameraService::class.java))
        statusText.text = "Status: Stopped"
        statusText.setTextColor(ContextCompat.getColor(this, android.R.color.darker_gray))
        Toast.makeText(this, "Camera service stopped", Toast.LENGTH_SHORT).show()
    }

    private fun updateUI() {
        val hasPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        statusText.text = if (hasPermission) "Status: Ready" else "Status: Permission Required"
        statusText.setTextColor(if (hasPermission) ContextCompat.getColor(this, android.R.color.holo_blue_dark) else ContextCompat.getColor(this, android.R.color.holo_orange_dark))
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startCameraService()
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
        }
        updateUI()
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }
}
