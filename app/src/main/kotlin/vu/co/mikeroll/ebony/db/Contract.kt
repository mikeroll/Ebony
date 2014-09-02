package vu.co.mikeroll.ebony.db

import android.provider.BaseColumns

private val DATABASE_NAME: String = "ebony.db"
private val DATABASE_VERSION: Int = 1

private val SQL_CREATE_TABLE: String =
        "CREATE TABLE " + Todos.TABLE + " (" +
                Todos._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Todos.KEY_CONTENT + " TEXT," +
                Todos.KEY_IS_IMPORTANT + " INTEGER" +
        ")"

private val SQL_DROP_TABLE: String =
        "DROP TABLE IF EXISTS " + Todos.TABLE

private abstract class Todos {
    public class object {
        val _ID: String = BaseColumns._ID
        val _COUNT: String = BaseColumns._COUNT
        val TABLE: String = "todos"
        val KEY_CONTENT: String = "content"
        val KEY_IS_IMPORTANT: String = "is_important"
    }
}

