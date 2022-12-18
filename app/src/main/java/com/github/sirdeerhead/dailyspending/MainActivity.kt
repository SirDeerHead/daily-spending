package com.github.sirdeerhead.dailyspending

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.sirdeerhead.dailyspending.databinding.ActivityMainBinding
import com.github.sirdeerhead.dailyspending.nav.history.History
import com.github.sirdeerhead.dailyspending.nav.home.Home
import com.github.sirdeerhead.dailyspending.nav.settings.Settings
import com.github.sirdeerhead.dailyspending.nav.stats.Stats
import dagger.hilt.android.AndroidEntryPoint

// Export room database to csv
// TODO: https://medium.com/@tonia.tkachuk/how-to-export-room-database-to-csv-file-a51fec589618
// Schedule Local Notifications Android Studio Kotlin Tutorial
// TODO: https://www.youtube.com/watch?v=_Z2S63O-1HE

@AndroidEntryPoint
class MainActivity : AppCompatActivity()  {

    //Adding binding class
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Initializing binding view
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // First it's opening 'Home' screen
        replaceFragment(Home())

        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // Selecting 'Home' as first selected in menu
        binding.bottomNavigationView.selectedItemId = R.id.home
        // Switching between fragments in Navigation
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.stats -> replaceFragment(Stats())
                R.id.home -> replaceFragment(Home())
                R.id.history -> replaceFragment(History())
                R.id.settings -> replaceFragment(Settings())

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