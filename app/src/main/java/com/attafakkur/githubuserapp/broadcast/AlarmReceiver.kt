package com.attafakkur.githubuserapp.broadcast

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.attafakkur.githubuserapp.R
import com.attafakkur.githubuserapp.utils.Constants.ID_REPEATING
import com.attafakkur.githubuserapp.utils.Constants.MESSAGE
import com.attafakkur.githubuserapp.utils.Constants.REPEATING
import com.attafakkur.githubuserapp.views.activity.MainActivity
import java.util.*

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        setAlarmNotification(context)
    }

    private fun setAlarmNotification(context: Context?) {
        val channelId = "channel_1"
        val channelName = "Set Alarm"

        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmReceiver = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_home_alarm)
            .setContentTitle(REPEATING)
            .setContentText(MESSAGE)
            .setContentIntent(
                PendingIntent.getActivity(
                    context, ID_REPEATING,
                    Intent(context, MainActivity::class.java), 0
                )
            )
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmReceiver)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId, channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(channelId)

            notificationManager.createNotificationChannel(channel)
        }

        val notification = builder.build()
        notificationManager.notify(ID_REPEATING, notification)
    }

    fun setRepeatingAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmPending: PendingIntent =
            Intent(context, AlarmReceiver::class.java).let {
                PendingIntent.getBroadcast(context, ID_REPEATING, it, 0)
            }
        val calender: Calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 9)
        }

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calender.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            alarmPending
        )
        Toast.makeText(context, "Set Repeating Alarm at 9 am", Toast.LENGTH_SHORT).show()
    }

    fun cancelAlarm(context: Context?) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmPending: PendingIntent =
            Intent(context, AlarmReceiver::class.java).let {
                PendingIntent.getBroadcast(context, ID_REPEATING, it, 0)
            }
        alarmPending.cancel()
        alarmManager.cancel(alarmPending)

        Toast.makeText(context, "Cancel Repeating Alarm at 9 am", Toast.LENGTH_SHORT).show()
    }
}