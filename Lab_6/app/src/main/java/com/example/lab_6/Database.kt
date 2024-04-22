package com.example.lab_6

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object FeedEntry : BaseColumns {
        const val DATABASE_NAME = "lab_6.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "Reminders"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_BODY = "body"
        const val COLUMN_TIME_REMIND = "time_remind"
        const val COLUMN_TIME_CREATED = "time_created"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val sqlCreateTable =
            "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,$COLUMN_TITLE TEXT, $COLUMN_BODY TEXT," +
                    " $COLUMN_TIME_REMIND TEXT,$COLUMN_TIME_CREATED DATETIME DEFAULT CURRENT_TIMESTAMP) "
        db?.execSQL(sqlCreateTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val sqlDeleteTable = """DROP TABLE IF EXISTS $TABLE_NAME"""
        db?.execSQL(sqlDeleteTable)
        onCreate(db)
    }

    fun addEntry(title: String, body: String, datetime: LocalDateTime): Int {
        return try {

            val values = ContentValues()
            values.put(COLUMN_TITLE, title)
            values.put(COLUMN_BODY, body)
            val formattedDateTime = datetime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            values.put(COLUMN_TIME_REMIND, formattedDateTime)

            val db = this.writableDatabase
            val id = db.insert(TABLE_NAME, null, values)

            id.toInt()
        }
        catch(e: Exception) {
            Log.e("ERROR", "$e")
            0
        }
    }

    fun deleteEntry(id: Int): Int{
        return try {
            val db = this.writableDatabase
            db.delete(TABLE_NAME, "$COLUMN_ID = $id", null)
        } catch(e: Exception) {
            Log.e("ERROR", "$e")
            0
        }
    }

    fun getCursor(): Cursor?{
        val db = this.readableDatabase
        return try {
            db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        } catch(e: Exception) {
            Log.e("ERROR", "$e")
            null
        }
    }
}