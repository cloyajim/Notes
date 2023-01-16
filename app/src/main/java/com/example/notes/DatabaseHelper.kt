package com.example.notes

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context : Context): SQLiteOpenHelper(context, Constants.DATABASE_NAME,
    null,Constants.DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE ${Constants.ENTITY_NOTE} (" +
                "${Constants.PROPERTY_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${Constants.PROPERTY_DESCRIPTION} VARCHAR(60), " +
                "${Constants.PROPERTY_IS_FINISHED} BOOLEAN)"

        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun insertNote(note: Note): Long {
        val database = this.readableDatabase
        val contentValues = ContentValues().apply {
            put(Constants.PROPERTY_DESCRIPTION, note.description)
            put(Constants.PROPERTY_IS_FINISHED, note.isFinish)
        }

        val resultId = database.insert(Constants.ENTITY_NOTE, null, contentValues)
        return resultId
    }
}