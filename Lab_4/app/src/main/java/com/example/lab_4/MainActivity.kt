package com.example.lab_4

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
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

        showInitialData()

        var lastConnection: Boolean = true
        val scheduler = Executors.newScheduledThreadPool(1)
        scheduler.scheduleAtFixedRate({
            scope.launch {
                if (!connectionCheck(this@MainActivity)){
                    Toast.makeText(this@MainActivity, "No connection - old data only", Toast.LENGTH_LONG).show()
                    lastConnection = false
                }
                else {
                    if (!lastConnection){
                        Toast.makeText(this@MainActivity, "Connection restored", Toast.LENGTH_SHORT).show()
                        lastConnection = true
                    }
                    getTrackIfNew()
                }
            }
        }, 0, 20, TimeUnit.SECONDS)
    }

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }

    private fun connectionCheck(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false

        // Representation of the capabilities of an active network.
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            // Indicates this network uses a Wi-Fi transport,
            // or WiFi has network connectivity
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

            // Indicates this network uses a Cellular transport. or
            // Cellular has network connectivity
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

            // else return false
            else -> false
        }
    }

    @SuppressLint("SetTextI18n")
    private suspend fun getTrackIfNew(){
        val response = requestData()
        if (response.status.value != 200){
            Log.i("RequestError", response.toString())
            return
        }
        Log.i("INFO", response.bodyAsText())

        val newEntryId = dbHelper.addEntry(JSONObject(response.bodyAsText()).getString("artist"), JSONObject(response.bodyAsText()).getString("title"))
        if (newEntryId != -1) {
            val cursor = dbHelper.getEntry()
            cursor?.moveToPosition(newEntryId-1)
            val insertText = (
                    cursor?.getString(0) + ") "
                            + cursor?.getString(1) + "\n"
                            + cursor?.getString(2) + "\n"
                            + cursor?.getString(3) + "\n"
                    )
            val text = findViewById<View>(R.id.list) as TextView
            text.text = insertText + text.getText()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun showInitialData(){
        val cursor = dbHelper.getEntry()
        cursor?.moveToLast()
        val text = findViewById<View>(R.id.list) as TextView
        text.append(
                cursor?.getString(0) + ") "
                        + cursor?.getString(1) + "\n"
                        + cursor?.getString(2) + "\n"
                        + cursor?.getString(3) + "\n"
        )
        while (cursor?.moveToPrevious() == true) {
            val insertText = (
                    cursor.getString(0) + ") "
                    + cursor.getString(1) + "\n"
                    + cursor.getString(2) + "\n"
                    + cursor.getString(3) + "\n"
                    )
            text.append(insertText)
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