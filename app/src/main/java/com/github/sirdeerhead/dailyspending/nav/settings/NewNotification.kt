package com.github.sirdeerhead.dailyspending.nav.settings

import android.app.*
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.sirdeerhead.dailyspending.databinding.FragmentNewNotificationBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

class NewNotification : BottomSheetDialogFragment() {

    private var _binding: FragmentNewNotificationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewNotificationBinding.inflate(inflater, container, false)

        createNotificationChannel()
        binding.btnNewNotification.setOnClickListener {
            scheduleNotification()
        }

        return binding.root
    }

    private fun scheduleNotification(){
        val intent = Intent(activity, Notification::class.java)
        val title = binding.tietTitle.text.toString()
        val message = binding.tietMessage.text.toString()
        intent.putExtra(TITLE_EXTRA, title)
        intent.putExtra(MESSAGE_EXTRA, message)

        val pendingIntent = PendingIntent.getBroadcast(
            activity,
            System.currentTimeMillis().toInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
        showAlert(time, title, message)
    }

    private fun showAlert(time: Long, title: String, message: String) {
        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(activity)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(activity)

        AlertDialog.Builder(activity)
            .setMessage(
                "Title: $title" +
                "\nMessage: $message" +
                "\nAt: " + dateFormat.format(date) + " " + timeFormat.format(date))
            .setPositiveButton("OK"){_,_ ->}
            .show()
    }

    private fun getTime(): Long {
        val minute = binding.tpTimePicker.minute
        val hour = binding.tpTimePicker.hour
        val day = binding.dpDatePicker.dayOfMonth
        val month = binding.dpDatePicker.month
        val year = binding.dpDatePicker.year

        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minute)
        return calendar.timeInMillis
    }

    private fun createNotificationChannel(){
        val name = "Notif Channel"
        val desc = "A Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance)
        channel.description = desc
        val notificationManager = activity?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

}