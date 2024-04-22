package com.example.lab_6

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent

import android.icu.util.Calendar
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import java.time.LocalDateTime

var setMinute: Int? = null
var setHour: Int? = null
var setDay: Int? = null
var setMonth: Int? = null
var setYear: Int? = null

class AddEntryActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_entry)
        findViewById<Button>(R.id.pick_time).setOnClickListener{
            TimePickerFragment().show(supportFragmentManager, "timePicker")
        }
        findViewById<Button>(R.id.pick_date).setOnClickListener{
            DatePickerFragment().show(supportFragmentManager, "datePicker")
        }

        findViewById<Button>(R.id.create).setOnClickListener{
            try {
                val datetime = LocalDateTime.of(setYear!!, setMonth!!, setDay!!, setHour!!, setMinute!!)
                val title = findViewById<EditText>(R.id.input_body).text.toString()
                val body = findViewById<EditText>(R.id.input_title).text.toString()

                if(title.isEmpty() || body.isEmpty()){
                    throw NullPointerException()
                }

                val db = DatabaseHelper(this)
                db.addEntry(title, body, datetime)

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            catch(e:NullPointerException){
                Toast.makeText(this@AddEntryActivity, "Fill all the fields first", Toast.LENGTH_SHORT).show()
            }
            catch(e: Exception){
                Log.i("INFO", "Bad date")
            }
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
        return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
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
