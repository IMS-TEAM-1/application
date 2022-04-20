package com.example.ims3000.ui.sampledata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.example.ims3000.R
import com.example.ims3000.databinding.ActivityNavigationMenuBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class NavigationMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_menu)
        val binding = ActivityNavigationMenuBinding.inflate(layoutInflater)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(binding.root)


        val mowerStatusFragment = MowerStatusFragment()
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)


        makeCurrentFragment(mowerStatusFragment)

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId){
                R.id.navigation_status -> makeCurrentFragment(mowerStatusFragment)


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