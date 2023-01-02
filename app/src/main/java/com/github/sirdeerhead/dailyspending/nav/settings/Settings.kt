package com.github.sirdeerhead.dailyspending.nav.settings

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.github.sirdeerhead.dailyspending.R
import com.github.sirdeerhead.dailyspending.databinding.FragmentSettingsBinding
import com.github.sirdeerhead.dailyspending.preferences.CurrencySharedPreferences


class Settings : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        loadPreferences()
        savePreferences()

        binding.btnNewNotification.setOnClickListener {
            NewNotification().show(childFragmentManager,"newNotificationTag")
        }

        binding.btnCloseApp.setOnClickListener {
            closeApp()
        }

        binding.rgCurrency.setOnCheckedChangeListener { _, _ ->
            savePreferences()
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

    private fun savePreferences(){
        val currencySharedPreferences = CurrencySharedPreferences(activity?.applicationContext)

        val rbId: Int = binding.rgCurrency.checkedRadioButtonId
        val radioGroup: RadioGroup = binding.rgCurrency
        val radioButton: RadioButton = binding.root.findViewById(rbId)
        val currencyOption: String = radioButton.text.toString()
        val checkedIndex: Int = radioGroup.indexOfChild(radioButton)

        currencySharedPreferences.SHARED_PREFERENCES = activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = currencySharedPreferences.SHARED_PREFERENCES?.edit()
        editor?.apply{
            putString("CURRENCY_KEY", currencyOption)
            putInt("RADIO_KEY", checkedIndex)
        }?.apply()
    }

    private fun loadPreferences(){
        val sharedPreferences = activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val loadedRadioIndex = sharedPreferences?.getInt("RADIO_KEY", 0)

        val radioGroup: RadioGroup = binding.rgCurrency

        val loadedCheckedRadioButton : RadioButton = loadedRadioIndex?.let {
            radioGroup.getChildAt(
                it
            )
        } as RadioButton

        loadedCheckedRadioButton.isChecked = true
    }
}