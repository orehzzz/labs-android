package com.example.lab_1

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Activity2: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout2)

        val parameterValue = intent.getStringExtra("parameter")

        val textView: TextView = findViewById(R.id.txt2)

        textView.text = parameterValue
    }
}