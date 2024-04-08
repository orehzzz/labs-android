package com.example.lab_3

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class DatabaseHelper(context:Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object FeedEntry : BaseColumns {
        const val DATABASE_NAME = "lab_3.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "people"
        const val COLUMN_ID = "id"
        const val COLUMN_INITIALS = "initials"
        const val COLUMN_TIME_CREATED = "time_created"
        }

    override fun onCreate(db: SQLiteDatabase?) {
        val sqlCreateTable = """CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY,$COLUMN_INITIALS TEXT,$COLUMN_TIME_CREATED TEXT)"""
        db?.execSQL(sqlCreateTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val sqlDeleteTable = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(sqlDeleteTable)
        onCreate(db)
    }



}