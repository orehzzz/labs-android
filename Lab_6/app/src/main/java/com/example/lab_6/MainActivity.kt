package com.example.lab_6

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

class MainActivity:AppCompatActivity() {

    private val dbHelper = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        supportActionBar?.hide()

        val addEntryButton = findViewById<Button>(R.id.add_entry_button)
        addEntryButton.setOnClickListener {
            val intent = Intent(this, AddEntryActivity::class.java)
            startActivity(intent)
        }

        showInitialData()

        createNotificationChannel()

    }


    private fun createNotificationChannel(){
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel("channel1", "Reminders", importance)
        val notificationManager = getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    @SuppressLint("SetTextI18n")
    private fun showInitialData(){
        val linearLayout: LinearLayout = findViewById(R.id.container)
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val cursor = dbHelper.getCursor()
        cursor!!.moveToFirst()

        while (!cursor.isAfterLast){

            val view = inflater.inflate(R.layout.little_entry, linearLayout, false)
            view.findViewById<TextView>(R.id.title).text = cursor.getString(1)

            val datetime = LocalDateTime.parse(cursor.getString(3), DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            val time = datetime.format(DateTimeFormatter.ISO_LOCAL_TIME)
            val date = datetime.format(DateTimeFormatter.ISO_LOCAL_DATE)
            view.findViewById<TextView>(R.id.time_text).text = """$time $date"""

            val intent = Intent(this, EntryActivity::class.java)
            intent.putExtra("id", cursor.getInt(0))
            intent.putExtra("title", cursor.getString(1))
            intent.putExtra("body", cursor.getString(2))
            intent.putExtra("time", time)
            intent.putExtra("date", date)

            linearLayout.addView(view)
            view.setOnClickListener {
                startActivity(intent)
            }

            cursor.moveToNext()
        }

        cursor.close()
    }
}