package com.example.shashank.login

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//MCO3 Group 17- Chua, Shi, Tan
class DatabaseHelperAccount(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    companion object {
        const val DATABASE_NAME = "UserDataBase"
        const val TABLE_NAME = "UserTable"
        const val Table_Column_ID = "id"
        const val Table_Column_1_username = "username"
        const val Table_Column_2_Email = "email"
        const val Table_Column_3_Password = "password"
        const val Table_Column_4_Name = "Name"
        const val Table_Column_5_bio = "bio"
    }

    override fun onCreate(database: SQLiteDatabase) {
        val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME ( $Table_Column_ID INTEGER PRIMARY KEY AUTOINCREMENT, $Table_Column_1_username VARCHAR, $Table_Column_2_Email VARCHAR, $Table_Column_3_Password VARCHAR, $Table_Column_4_Name VARCHAR, $Table_Column_5_bio VARCHAR)"
        database.execSQL(CREATE_TABLE)
    }


    override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        database.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(database)
    }
}
