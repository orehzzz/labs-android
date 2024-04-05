package com.example.lab_2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Activity1: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle? ){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout1)

        val toActivity2: Button = findViewById(R.id.toActivity2)
        toActivity2.setOnClickListener {
            val intent = Intent(this, Activity2::class.java)
            startActivity(intent)
        }

        val toActivity3: Button = findViewById(R.id.toActivity3)
        toActivity3.setOnClickListener {
            val intent = Intent(this, Activity3::class.java)
            startActivity(intent)
        }

        val toActivity4: Button = findViewById(R.id.toActivity4)
        toActivity4.setOnClickListener {
            val intent = Intent(this, Activity4::class.java)
            startActivity(intent)
        }

        val exitButton: Button = findViewById(R.id.exit)
        exitButton.setOnClickListener {
            finishAffinity()
        }
    }
}