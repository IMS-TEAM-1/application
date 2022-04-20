package com.example.ims3000.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.RequestManager
import com.example.ims3000.R
import com.example.ims3000.api.RetrofitInstance
import com.example.ims3000.data.entities.mockApiData
import com.example.ims3000.databinding.ActivityMainBinding
import com.example.ims3000.ui.sampledata.MowerStatusFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

private lateinit var binding: ActivityMainBinding

//@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var glide: RequestManager

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

        //start with this fragment
        makeCurrentFragment(mowerStatusFragment)

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId){
                R.id.navigation_status -> makeCurrentFragment(mowerStatusFragment)
                // More views
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout, fragment)
            commit()
        }

}