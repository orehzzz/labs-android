package com.example.lab_3

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

        cursor!!.moveToFirst()
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
}