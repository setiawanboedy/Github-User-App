package com.attafakkur.githubuserapp.views.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.attafakkur.githubuserapp.broadcast.AlarmReceiver
import com.attafakkur.githubuserapp.databinding.ActivityAlarmBinding

class AlarmActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlarmBinding
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alarmReceiver = AlarmReceiver()
        binding.setAlarm.isChecked = update("save")
        binding.setAlarm.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                alarmReceiver.setRepeatingAlarm(this)
                saveIntoPreferences("save", isChecked)
            } else {
                alarmReceiver.cancelAlarm(this)
                saveIntoPreferences("save", false)
            }
        }
    }

    private fun saveIntoPreferences(key: String, value: Boolean) {
        val sharedPreferences = getSharedPreferences("save", MODE_PRIVATE)
        sharedPreferences.edit()
            .putBoolean(key, value)
            .apply()
    }

    private fun update(key: String): Boolean {
        val sharedPreferences = getSharedPreferences("save", MODE_PRIVATE)
        return sharedPreferences.getBoolean(key, false)
    }
}