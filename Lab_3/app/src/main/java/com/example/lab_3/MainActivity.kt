package com.example.lab_3

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class MainActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        val dbHelper = DatabaseHelper(this)

        dbHelper.deleteAllEntries()

        dbHelper.addEntry("Олег Олег Олегович")
        dbHelper.addEntry("Назар Назар Назарович")
        dbHelper.addEntry("Сірий Сергій Сергійович")
        dbHelper.addEntry("Великий Володимир Володимирович")
        dbHelper.addEntry("Вовк Віталій Віталійович")

        val toData: Button = findViewById(R.id.toData)
        toData.setOnClickListener {
            val intent = Intent(this, DataActivity::class.java)
            startActivity(intent)
        }

        val addEntry: Button = findViewById(R.id.addEntry)
        addEntry.setOnClickListener {
            dbHelper.addEntry("Милий Микола Миколайович")
        }

        val changeEntry: Button = findViewById(R.id.changeEntry)
        changeEntry.setOnClickListener {
            dbHelper.changeLastEntry("Петренко Петро Петрович")
        }

    }

}