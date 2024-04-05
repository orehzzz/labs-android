package com.example.lab_2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class Activity4: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle? ){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout4)

        val button: Button = findViewById(R.id.colorButton)
    }
}