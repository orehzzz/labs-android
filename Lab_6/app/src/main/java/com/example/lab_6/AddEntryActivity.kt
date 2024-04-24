package com.example.lab_6

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent

import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.TaskStackBuilder
import androidx.fragment.app.DialogFragment
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

var setMinute: Int? = null
var setHour: Int? = null
var setDay: Int? = null
var setMonth: Int? = null
var setYear: Int? = null

class AddEntryActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_entry)
        supportActionBar?.hide()
        findViewById<Button>(R.id.pick_time).setOnClickListener{
            TimePickerFragment().show(supportFragmentManager, "timePicker")
        }
        findViewById<Button>(R.id.pick_date).setOnClickListener{
            DatePickerFragment().show(supportFragmentManager, "datePicker")
        }

        findViewById<Button>(R.id.create).setOnClickListener{
            onCreateButton()
        }
    }

    @SuppressLint("ScheduleExactAlarm")
    private fun onCreateButton(){
        try {
            val datetime = LocalDateTime.of(setYear!!, setMonth!!, setDay!!, setHour!!, setMinute!!)
            val title = findViewById<EditText>(R.id.input_title).text.toString()
            val body = findViewById<EditText>(R.id.input_body).text.toString()

            if(title.isEmpty() || body.isEmpty()){
                throw NullPointerException()
            }

            val db = DatabaseHelper(this)
            val id = db.addEntry(title, body, datetime)


            //Create Intent which will open EntryActivity when click on notification, and pass it to retranslator as extra
            val resultIntent = Intent(this, EntryActivity::class.java)

            resultIntent.putExtra("id", id)
            resultIntent.putExtra("title", title)
            resultIntent.putExtra("body", body)
            val timeForEntry = datetime.format(DateTimeFormatter.ISO_LOCAL_TIME)
            val dateForEntry = datetime.format(DateTimeFormatter.ISO_LOCAL_DATE)
            resultIntent.putExtra("time", timeForEntry)
            resultIntent.putExtra("date", dateForEntry)
            // Create the TaskStackBuilder.
            val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
                // Add the intent, which inflates the back stack.
                addNextIntentWithParentStack(resultIntent)
                // Get the PendingIntent containing the entire back stack.
                getPendingIntent(0,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            }

            //create alarm which retranslator catches? not sure, but works:)
            val calendar = java.util.Calendar.getInstance()
            calendar.set(setYear!!, setMonth!!, setDay!!, setHour!!, setMinute!!)
            val time = calendar.timeInMillis

            val intent = Intent(applicationContext, NotificationRetranslator::class.java)
            intent.putExtra("title", title)
            intent.putExtra("intent", resultPendingIntent)

            val pendingIntent = PendingIntent.getBroadcast(
                applicationContext,
                100,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            // Get the selected time and schedule the notification
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                time,
                pendingIntent
            )


            val returnToMain = Intent(this, MainActivity::class.java)
            startActivity(returnToMain)
        }
        catch(e:NullPointerException){
            Toast.makeText(this@AddEntryActivity, "Fill all the fields first", Toast.LENGTH_SHORT).show()
        }
        catch(e: Exception){
            Log.i("ERROR", e.toString())
        }
    }
}

class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current time as the default values for the picker.
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        // Create a new instance of TimePickerDialog and return it.
        return TimePickerDialog(activity, this, hour, minute, true)
    }

    override fun onTimeSet(view: TimePicker, hour: Int, minute: Int) {
        setHour = hour
        setMinute = minute
    }
}
class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker.
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it.
        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        setDay = day
        setMonth = month
        setYear = year
    }
}
