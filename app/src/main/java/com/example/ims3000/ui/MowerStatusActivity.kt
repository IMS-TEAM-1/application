package com.example.ims3000.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ProgressBar
import com.example.ims3000.R
import kotlin.concurrent.fixedRateTimer

class MowerStatusActivity : AppCompatActivity() {
    private var batteryLevel = 0
    private val progress_bar: ProgressBar = findViewById(R.id.progress_bar)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mower_status)
        fixedRateTimer("updateBatteryLevel", false, 0L, 60 * 1000){
            updateProgressBar()
        }
    }

    private fun updateProgressBar() {
        progress_bar.progress = batteryLevel
    }
}