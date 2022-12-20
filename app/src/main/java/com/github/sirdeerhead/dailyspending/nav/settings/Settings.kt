package com.github.sirdeerhead.dailyspending.nav.settings

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.sirdeerhead.dailyspending.R
import com.github.sirdeerhead.dailyspending.databinding.FragmentSettingsBinding

class Settings : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        binding.btnNewNotification.setOnClickListener {
            NewNotification().show(childFragmentManager,"newNotificationTag")
        }

        binding.btnCloseApp.setOnClickListener {
            closeApp()
        }

        return binding.root
    }

    private fun closeApp(){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Quit")
        builder.setIcon(R.drawable.ic_quit)
        builder.setMessage("Do you want to close\nthis application completely ?")
        builder.setCancelable(true)

        builder.setNegativeButton("No") { dialogInterface, _ ->
            dialogInterface.cancel()
        }

        builder.setPositiveButton("Yes") { _, _ ->
            activity?.finishAndRemoveTask()
        }

        val alertDialog = builder.create()
        alertDialog.show()
    }
}