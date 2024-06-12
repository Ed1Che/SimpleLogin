package com.example.loginsqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf

class DatabaseHelper (private val context: Context ):
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object{
        private const val DATABASE_NAME = "userdatabase.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "data"
        private const val COLUMN_ID = "id"
        private const val COLUMN_USEREMAIL = "useremail"
        private const val COLUMN_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE = ("CREATE TABLE $TABLE_NAME(" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_USEREMAIL TEXT," +
                "$COLUMN_PASSWORD TEXT)")
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
       val droptable = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(droptable)
        onCreate(db)
    }

    fun insertUser(useremail:String, password:String ): Long {
        val values = ContentValues().apply{
            put (COLUMN_USEREMAIL, useremail)
            put (COLUMN_PASSWORD, password)
        }
        val db = writableDatabase
        return db.insert(TABLE_NAME, null, values)
    }
    fun readUser(useremail:String, password:String ): Boolean {
        val db = readableDatabase
        val selection = "$COLUMN_USEREMAIL = ? AND $COLUMN_PASSWORD = ?"
        val selectionArgs = arrayOf(useremail, password)
        val cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null,null, null)

        val userexists = cursor.count > 0
        cursor.close()
        return userexists
    }


}