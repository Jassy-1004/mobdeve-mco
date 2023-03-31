package com.mobdeve.s13.Group17.MCO2

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.shashank.login.DatabaseHelperAccount

class DatabaseHelperCommentAndReview (context: Context) :
    SQLiteOpenHelper(context, DatabaseHelperAccount.DATABASE_NAME, null, 1) {

    companion object {
        const val DATABASE_NAME = "UserDataBase"
        const val TABLE_NAME = "CommentTable"
        const val Table_Column_ID = "id"
        const val Table_Column_1_BookTitle = "book"
        const val Table_Column_2_Comment = "comment"
        const val Table_Column_3_Review = "review"
    }

    override fun onCreate(database: SQLiteDatabase) {
        val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME ( $Table_Column_ID INTEGER PRIMARY KEY AUTOINCREMENT, $Table_Column_1_BookTitle VARCHAR, $Table_Column_2_Comment VARCHAR, $Table_Column_3_Review FLOAT)"
        database.execSQL(CREATE_TABLE)
    }


    override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        database.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(database)
    }
}