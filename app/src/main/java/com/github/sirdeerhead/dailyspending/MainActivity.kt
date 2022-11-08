package com.github.sirdeerhead.dailyspending

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.github.sirdeerhead.dailyspending.databinding.ActivityMainBinding

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

    }
}