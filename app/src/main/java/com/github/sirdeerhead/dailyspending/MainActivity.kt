package com.github.sirdeerhead.dailyspending

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.sirdeerhead.dailyspending.databinding.ActivityMainBinding
import com.github.sirdeerhead.dailyspending.nav.*

// Bottom Tab Bar Example Fragments Android Studio Kotlin Tutorial
// TODO: https://www.youtube.com/watch?v=obYxPd2ot7Q
// Bottom Sheet Dialog Android Studio Kotlin Example Tutorial - ViewModel
// TODO: https://www.youtube.com/watch?v=RzjCMa4GBD4
// Room Database Android Studio Kotlin Example Tutorial
// TODO: https://www.youtube.com/watch?v=-LNg-K7SncM
// Schedule Local Notifications Android Studio Kotlin Tutorial
// TODO: https://www.youtube.com/watch?v=_Z2S63O-1HE


class MainActivity : AppCompatActivity() {

    //Adding binding class
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Initializing binding view
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // First it's opening 'Home' screen
        replaceFragment(Home())

        // Selecting 'Home' as first selected in menu
        binding.bottomNavigationView.selectedItemId = R.id.home
        // Switching between fragments in Navigation
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.stats -> replaceFragment(Stats())
                R.id.home -> replaceFragment(Home())
                R.id.history -> replaceFragment(History())

                else ->{}
            }
            true
        }

    }

    // method to replace FrameLayout with Fragments
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout_navigation, fragment)
        fragmentTransaction.commit()
    }
}