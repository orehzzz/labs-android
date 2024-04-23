package com.example.lab_6

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EntryActivity:AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.full_entry)
        val dbHelper = DatabaseHelper(this)

        val id = intent.getIntExtra("id", -1)
        findViewById<TextView>(R.id.title).text = intent.getStringExtra("title")
        findViewById<TextView>(R.id.body).text = intent.getStringExtra("body")
        val time = intent.getStringExtra("time")
        val date = intent.getStringExtra("date")
        findViewById<TextView>(R.id.time_text).text = """$time $date"""

        val backButton = findViewById<Button>(R.id.back)
        backButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        val deleteButton = findViewById<Button>(R.id.delete)
        deleteButton.setOnClickListener {
            try{
                if (id == -1) throw Exception()
                dbHelper.deleteEntry(id)
            }
            catch (e: Exception){
                Toast.makeText(this, "Failed to delete", Toast.LENGTH_SHORT).show()
            }
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}