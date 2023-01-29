package com.example.dontlate

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class personnelDBManager (context : Context,
                          name : String,
                          factory : SQLiteDatabase.CursorFactory?,
                          version : Int
) : SQLiteOpenHelper (context, name, factory, version) {
    var dbName : String = name

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE $dbName (date text, title text, time text, place text)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}