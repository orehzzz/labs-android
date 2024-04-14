package com.example.lab_3

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class DataActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.data)

        val dbHelper = DatabaseHelper(this)

        val cursor = dbHelper.getEntry()

        val dataAsText = findViewById<View>(R.id.dataAsText) as TextView

        if (cursor != null){
            dataAsText.append("cursor != null")
        }
        if (cursor!!.moveToFirst().not()){
            dataAsText.append("cursor empty")
        }
        if (cursor != null && cursor.moveToFirst()) {
            dataAsText.append(
                cursor.getString(0) + ") "
                        + cursor.getString(1) + "\n"
                        + cursor.getString(2) + "\n"
            )
            while(cursor.moveToNext()){
                dataAsText.append(cursor.getString(0)+") "
                        +cursor.getString(1)+"\n"
                        +cursor.getString(2)+"\n")
            }

            cursor.close()
        }
        else{
            dataAsText.append(cursor.toString())
        }

        cursor.close()
    }
}