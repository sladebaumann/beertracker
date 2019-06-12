package com.example.beertracker

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.*

class DBHandler(context: Context, name: String?,
                factory: SQLiteDatabase.CursorFactory?, version: Int) :
                        SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_BEER_TABLE = ("CREATE TABLE " +
                TABLE_BEER + "("
                + BEER_COLUMN_ID + " INTEGER PRIMARY KEY," +
                BEER_COLUMN_COUNT
                + " INTEGER," + BEER_COLUMN_TIMESTAMP + " TEXT" + ")")
        db.execSQL(CREATE_BEER_TABLE)

        // initialize value in table
        val values = ContentValues()
        // TODO: use var for initial value
        values.put(BEER_COLUMN_COUNT, 7)
        values.put(BEER_COLUMN_TIMESTAMP, Date().toString())

        db.insert(TABLE_BEER, null, values)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BEER)
        onCreate(db)
    }

    companion object {

        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "beertracker.db"
        val TABLE_BEER = "beer"

        val BEER_COLUMN_ID = "_id"
        val BEER_COLUMN_COUNT = "count"
        val BEER_COLUMN_TIMESTAMP = "timestamp"
    }

    fun resetCountToInitial(initialValue: Int) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(BEER_COLUMN_COUNT, initialValue)
        values.put(BEER_COLUMN_TIMESTAMP, Date().toString())

        db.insert(TABLE_BEER, null, values)

        db.close()
    }

    fun getBeer(): Int {
        // get current count
        val query = "SELECT $BEER_COLUMN_COUNT FROM $TABLE_BEER ORDER BY $BEER_COLUMN_ID DESC LIMIT 0,1"

        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)
        var count: Int? = null
        if (cursor.moveToFirst()) {
            cursor.moveToFirst()

            count = Integer.parseInt(cursor.getString(0))
            cursor.close()
        }
        db.close()
        if (count == null) {
            // TODO: throw error and remove return
            return 0
        } else {
            return count
        }
    }

    fun addBeer(amt: Int) {
        // get current count
        val query = "SELECT $BEER_COLUMN_COUNT FROM $TABLE_BEER ORDER BY $BEER_COLUMN_ID DESC LIMIT 0,1"

        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)
        var count: Int? = null
        if (cursor.moveToFirst()) {
            cursor.moveToFirst()

            count = Integer.parseInt(cursor.getString(0))
            cursor.close()
        }
        // set count with one more bonus beer
        val newCount = (count?: 0) + amt
        val values = ContentValues()
        values.put(BEER_COLUMN_COUNT, newCount)
        values.put(BEER_COLUMN_TIMESTAMP, Date().toString())

        db.insert(TABLE_BEER, null, values)
        db.close()
    }

    fun removeBeer(amt: Int) {
        // get current count
        val query =
            "SELECT $BEER_COLUMN_COUNT FROM $TABLE_BEER ORDER BY $BEER_COLUMN_ID DESC LIMIT 0,1"
        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)
        var count: Int? = null
        if (cursor.moveToFirst()) {
            cursor.moveToFirst()

            count = Integer.parseInt(cursor.getString(0))
            cursor.close()
        }
        // if there are no beers, send failure message
        if (count == null || count == 0) {
            // do nothing
        } else {
            // set count with one less bonus beer
            val newCount = (count?: 0) - amt
            val values = ContentValues()
            values.put(BEER_COLUMN_COUNT, newCount)
            values.put(BEER_COLUMN_TIMESTAMP, Date().toString())

            db.insert(TABLE_BEER, null, values)
        }

        db.close()
    }
}