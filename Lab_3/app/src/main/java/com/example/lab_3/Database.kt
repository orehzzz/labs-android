package com.example.lab_3

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.Cursor
import android.provider.BaseColumns

class DatabaseHelper(context:Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object FeedEntry : BaseColumns {
        const val DATABASE_NAME = "lab_3.db"
        var DATABASE_VERSION = 7
        var TABLE_NAME = "Одногрупники"
        var COLUMN_ID = "id"
        var COLUMN_INITIALS = "initials" //later = ""
        var COLUMN_TIME_CREATED = "time_created"
        var COLUMN_NAME = "" //later = "name"
        var COLUMN_SURNAME = "" //later = "surname"
        var COLUMN_PATRONYMIC = "" //later = "patronymic"
    }


    override fun onCreate(db: SQLiteDatabase?) {
        val sqlCreateTable = """CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,$COLUMN_INITIALS TEXT,$COLUMN_TIME_CREATED DATETIME DEFAULT CURRENT_TIMESTAMP)"""
        db?.execSQL(sqlCreateTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//        val sqlDeleteTable = "DROP TABLE IF EXISTS $TABLE_NAME"
//        db?.execSQL(sqlDeleteTable)
//
//        COLUMN_ID = "id"
//        COLUMN_INITIALS = ""
//        COLUMN_NAME = "name"
//        COLUMN_SURNAME = "surname"
//        COLUMN_PATRONYMIC = "patronymic"
//
//        val sqlCreateTable = """CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_SURNAME TEXT, $COLUMN_NAME TEXT, $COLUMN_PATRONYMIC TEXT,$COLUMN_TIME_CREATED DATETIME DEFAULT CURRENT_TIMESTAMP)"""
//        db?.execSQL(sqlCreateTable)
    }

    fun deleteAllEntries(){
        val db = this.writableDatabase
        db.delete(TABLE_NAME, null, null)
        db.close()
    }

    fun addEntry(initials: String){
        val values = ContentValues()
        values.put(COLUMN_INITIALS, initials)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getEntry(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    fun changeLastEntry(initials: String){
        val values = ContentValues()
        values.put(COLUMN_INITIALS, initials)
        val db = this.writableDatabase

        val query = "SELECT MAX($COLUMN_ID) FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        if (cursor != null && cursor.moveToFirst()) {
            val largestId = cursor.getInt(0)
            val whereClause = "$COLUMN_ID = ?"
            val whereArgs = arrayOf(largestId.toString())
            db.update(TABLE_NAME, values, whereClause, whereArgs)
        }
        cursor.close()
        db.close()
    }
}