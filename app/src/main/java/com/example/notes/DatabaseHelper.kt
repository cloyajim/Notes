package com.example.notes

import android.annotation.SuppressLint
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

    @SuppressLint("Range")
    fun getAllNotes(): MutableList<Note>{
        val notes:MutableList<Note> = mutableListOf()

        val database = this.readableDatabase
        val query = "SELECT * FROM ${Constants.ENTITY_NOTE}"

        val result = database.rawQuery(query,null)

        if(result.moveToFirst()){
            do{
                val note = Note()
                note.id = result.getLong(result.getColumnIndex(Constants.PROPERTY_ID))
                note.description = result.getString(result.getColumnIndex(Constants.PROPERTY_DESCRIPTION))
                note.isFinish =
                    result.getInt(result.getColumnIndex(Constants.PROPERTY_IS_FINISHED)) == Constants.TRUE

                notes.add(note)
            } while (result.moveToNext())
        }

        return notes
    }

    fun insertNote(note: Note): Long {
        val database = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(Constants.PROPERTY_DESCRIPTION, note.description)
            put(Constants.PROPERTY_IS_FINISHED, note.isFinish)
        }

        val resultId = database.insert(Constants.ENTITY_NOTE,
            null,
            contentValues)
        return resultId
    }

    fun updateNote(note:Note): Boolean{
        val database = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(Constants.PROPERTY_DESCRIPTION, note.description)
            put(Constants.PROPERTY_IS_FINISHED, note.isFinish)
        }

         val result = database.update(Constants.ENTITY_NOTE,
             contentValues,
             "${Constants.PROPERTY_ID} = ${note.id}",
             null)

        return result == Constants.TRUE
    }
}