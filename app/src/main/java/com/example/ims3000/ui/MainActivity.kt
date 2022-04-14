package com.example.ims3000.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.RequestManager
import com.example.ims3000.R
import com.example.ims3000.api.RetrofitInstance
import com.example.ims3000.data.entities.mockApiData
import com.example.ims3000.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

private lateinit var binding: ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var glide: RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
/*        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.apiCallButtonTest.setOnClickListener {
            mockApiCall()
        }
*/
    }
/*
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
            }
        }
    }
*/
}