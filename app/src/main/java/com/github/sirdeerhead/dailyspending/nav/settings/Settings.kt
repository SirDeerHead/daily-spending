package com.github.sirdeerhead.dailyspending.nav.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.sirdeerhead.dailyspending.databinding.FragmentSettingsBinding
import com.github.sirdeerhead.dailyspending.nav.home.NewCashFlow

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

        return binding.root
    }
}