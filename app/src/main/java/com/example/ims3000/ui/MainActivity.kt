package com.example.ims3000.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.bumptech.glide.RequestManager
import com.example.ims3000.R
import com.example.ims3000.databinding.ActivityMainBinding
import com.example.ims3000.ui.fragments.MapFragment
import com.example.ims3000.ui.fragments.MowerStatusFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private lateinit var binding: ActivityMainBinding

@AndroidEntryPoint
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
        val mapFragment = MapFragment()
        //start with this fragment
        makeCurrentFragment(mowerStatusFragment)

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId){
                R.id.navigation_status -> makeCurrentFragment(mowerStatusFragment)
                R.id.navigation_map -> makeCurrentFragment(mapFragment)
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