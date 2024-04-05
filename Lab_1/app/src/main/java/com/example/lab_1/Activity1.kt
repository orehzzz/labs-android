package com.example.lab_1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Activity1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout1)

        val switchButton: Button = findViewById(R.id.btn1)
        switchButton.setOnClickListener {
            val intent = Intent(this, Activity2::class.java)

            intent.putExtra("parameter", "Zaika") // Replace "Your parameter value" with the actual value you want to pass
            startActivity(intent)
        }
    }
}