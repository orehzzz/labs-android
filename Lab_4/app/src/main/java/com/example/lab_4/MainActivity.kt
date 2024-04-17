package com.example.lab_4

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class MainActivity:AppCompatActivity() {

    private val dbHelper = DatabaseHelper(this)
    private val scope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

//        showInitialData()

        val text = findViewById<View>(R.id.list) as TextView

        val scheduler = Executors.newScheduledThreadPool(1)
        scheduler.scheduleAtFixedRate({
            scope.launch {
                val response = requestData()
//                if (newEntry. != null){
//                    text.append(newEntry.body())
//                }
                Log.i("INFO", response.bodyAsText())
            }
        }, 0, 20, TimeUnit.SECONDS)

    }

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }
    private fun showInitialData(){
        val cursor = dbHelper.getEntry()
        cursor?.moveToFirst()
        val text = findViewById<View>(R.id.list) as TextView
        text.append(
                cursor?.getString(0) + ") "
                        + cursor?.getString(1) + "\n"
                        + cursor?.getString(2) + "\n"
                        + cursor?.getString(3) + "\n"
        )
        while (cursor?.moveToNext() == true) {
            text.append(
                cursor.getString(0) + ") "
                    + cursor.getString(1) + "\n"
                    + cursor.getString(2) + "\n"
            )
        }
        cursor?.close()
    }

    private suspend fun requestData(): HttpResponse {
        val client = HttpClient(CIO)
        val response: HttpResponse = client.get("https://webradio.io/api/radio/pi/current-song")
        client.close()
        return response
    }
}