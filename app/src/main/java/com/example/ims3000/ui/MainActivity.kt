package com.example.ims3000.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
<<<<<<< Updated upstream
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
=======
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.bumptech.glide.RequestManager
>>>>>>> Stashed changes
import com.example.ims3000.R
import com.example.ims3000.databinding.ActivityMainBinding
<<<<<<< Updated upstream
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
=======
import com.example.ims3000.ui.sampledata.MowerStatusFragment
import com.example.ims3000.ui.sampledata.RemoteControllerFragment
import javax.inject.Inject
>>>>>>> Stashed changes

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
<<<<<<< Updated upstream
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.apiCallButtonTest.setOnClickListener {
            mockApiCall()
        }
=======
        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(binding.root)



        val mowerStatusFragment = MowerStatusFragment()
        val remoteControllerFragment = RemoteControllerFragment()

>>>>>>> Stashed changes

    }

<<<<<<< Updated upstream
    private fun mockApiCall() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.getTodo(1)
                if (response.isSuccessful) {
                    val todo = response.body() as mockApiData
                    val baseText = binding.apidatatext.text.toString()
                    val apiText = todo.title
                    lifecycleScope.launch(Dispatchers.Main) {
                        binding.apidatatext.text = baseText + " " + apiText
                    }
                }
            } catch (e: Exception) {
                Log.d("debug", "error")
=======
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId){
                R.id.navigation_status -> makeCurrentFragment(mowerStatusFragment)
                R.id.navigation_control -> makeCurrentFragment(remoteControllerFragment)
                // More views
>>>>>>> Stashed changes
            }
        }
    }
}