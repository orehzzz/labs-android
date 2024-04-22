package com.example.lab_6

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Layout

class MainActivity:AppCompatActivity() {

    private val dbHelper = DatabaseHelper(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        val addEntryButton = findViewById<Button>(R.id.add_entry_button)
        addEntryButton.setOnClickListener {
            val intent = Intent(this, AddEntryActivity::class.java)
            startActivity(intent)
        }

        showInitialData()

    }

    private fun showInitialData(){
        val linearLayout: LinearLayout = findViewById(R.id.container)
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val cursor = dbHelper.getCursor()
        cursor!!.moveToFirst()

        if (!cursor.isAfterLast){
            Log.i("INFO", "+1 cursor move")
            val view = inflater.inflate(R.layout.little_entry, linearLayout, false)
            linearLayout.addView(view)
            cursor.moveToNext()
        }

        cursor.close()
// Assuming you have a LinearLayout defined in your layout XML file with id "linear_layout"

// Initialize LayoutInflater

// Inflate the XML file into the LinearLayout

// Add the inflated view to the LinearLayout
    }
}