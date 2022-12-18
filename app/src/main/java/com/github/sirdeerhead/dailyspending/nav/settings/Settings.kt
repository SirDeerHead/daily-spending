package com.github.sirdeerhead.dailyspending.nav.settings

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.github.sirdeerhead.dailyspending.databinding.FragmentSettingsBinding
import com.github.sirdeerhead.dailyspending.nav.home.NewCashFlow
import kotlin.system.exitProcess

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
        builder.setMessage("Do you want to Exit ?")
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