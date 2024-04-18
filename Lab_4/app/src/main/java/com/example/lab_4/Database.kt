package com.example.lab_4

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.provider.BaseColumns
import android.util.Log
import androidx.core.database.getIntOrNull

class DatabaseHelper(context:Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object FeedEntry : BaseColumns {
        const val DATABASE_NAME = "lab_4.db"
        const val DATABASE_VERSION = 2
        const val TABLE_NAME = "Tracks"
        const val COLUMN_ID = "id"
        const val COLUMN_ARTIST = "artist"
        const val COLUMN_TITLE = "title"
        const val COLUMN_TIME_CREATED = "time_created"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val sqlCreateTable = """CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,$COLUMN_ARTIST TEXT, $COLUMN_TITLE TEXT,$COLUMN_TIME_CREATED DATETIME DEFAULT CURRENT_TIMESTAMP, UNIQUE ($COLUMN_TITLE, $COLUMN_ARTIST)) """
        db?.execSQL(sqlCreateTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val sqlDeleteTable = """DROP TABLE IF EXISTS $TABLE_NAME"""
        db?.execSQL(sqlDeleteTable)
        onCreate(db)
    }

    @SuppressLint("Range")
    fun addEntry(artist: String, title: String): Int {
        val cursor = getEntry()
        cursor?.moveToLast()
        if (cursor?.getString(1) == artist && cursor?.getString(2)== title){
            cursor.close()
            return -1
        }
        val values = ContentValues()
        values.put(COLUMN_ARTIST, artist)
        values.put(COLUMN_TITLE, title)
        val db = this.writableDatabase
        val id = db.insert(TABLE_NAME, null, values)
        cursor?.close()
        return id.toInt()
    }

    fun getEntry(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }
}