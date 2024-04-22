package com.example.lab_6

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object FeedEntry : BaseColumns {
        const val DATABASE_NAME = "lab_6.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "Reminders"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_BODY = "body"
//        const val COLUMN_TIME_REMIND = "time_remind" ??????????
        const val COLUMN_TIME_CREATED = "time_created"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val sqlCreateTable = """CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,$COLUMN_TITLE TEXT, $COLUMN_BODY TEXT, $COLUMN_TIME_CREATED DATETIME DEFAULT CURRENT_TIMESTAMP) """
        db?.execSQL(sqlCreateTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val sqlDeleteTable = """DROP TABLE IF EXISTS $TABLE_NAME"""
        db?.execSQL(sqlDeleteTable)
        onCreate(db)
    }

    fun addEntry(title: String, body: String){

    }

    fun deleteEntry(id: Int){

    }

    fun getCursor(): Cursor?{
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }
}