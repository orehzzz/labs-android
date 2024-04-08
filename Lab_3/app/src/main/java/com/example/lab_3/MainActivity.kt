package com.example.lab_3

import android.content.Intent
import android.os.Bundle
import android.provider.BaseColumns
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button



//private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${DatabaseHelper.TABLE_NAME}"


class MainActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        val dbHelper = DatabaseHelper(this)//should work


        val toData: Button = findViewById(R.id.toData)
        toData.setOnClickListener {
            val intent = Intent(this, DataActivity::class.java)
            startActivity(intent)
        }


    }




}