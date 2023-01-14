package com.github.sirdeerhead.dailyspending

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.sirdeerhead.dailyspending.databinding.ActivityMainBinding
import com.github.sirdeerhead.dailyspending.nav.history.History
import com.github.sirdeerhead.dailyspending.nav.home.Home
import com.github.sirdeerhead.dailyspending.nav.settings.Settings
import com.github.sirdeerhead.dailyspending.nav.stats.Stats
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity()  {

    // Adding binding class
    private lateinit var binding: ActivityMainBinding
    // Adding variable for counting back press time
    private var backPressedTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Initializing binding view
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // First it's opening 'Home' screen
        replaceFragment(Home())

        // Locking screen orientation on portrait
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

    // function to replace FrameLayout with Fragments
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout_navigation, fragment)
        fragmentTransaction.commit()
    }

    // Function being a safety mechanism.
    // User need to click on Back button 2 times in less then 2 sec to close app
    @Deprecated("Deprecated in Java")
    override fun onBackPressed(){
        if (backPressedTime + 2000 > System.currentTimeMillis()){
            super.onBackPressed()
            finishAndRemoveTask()
        } else {
            Toast.makeText(applicationContext, "Press back again to exit app", Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}