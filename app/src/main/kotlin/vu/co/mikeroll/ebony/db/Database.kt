package vu.co.mikeroll.ebony.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.Cursor
import android.content.ContentValues
import vu.co.mikeroll.ebony.app.TodoItem

public object Database {

    private var dbHelper: SQLiteOpenHelper? = null

    public fun connect(ctx: Context) : Database {
        if (dbHelper == null) {
            dbHelper = Helper(ctx)
        }
        return this
    }

    protected fun finalize() {
        if (dbHelper != null) {
            dbHelper!!.close()
        }
    }

    private fun getDb() : SQLiteDatabase {
        if (dbHelper != null) {
            return dbHelper!!.getWritableDatabase()!!
        } else {
            throw IllegalStateException("You should open a DB connection first.")
        }
    }

    fun upsert(item: TodoItem) {
        val entry = ContentValues()
        entry.put(Todos.KEY_CONTENT, item.content)
        entry.put(Todos.KEY_IS_IMPORTANT, item.important)
        item.id = getDb().insertWithOnConflict(Todos.TABLE, null, entry, SQLiteDatabase.CONFLICT_REPLACE)
    }

    fun selectAll() : Cursor? {
        return getDb().query(Todos.TABLE, null, null, null, null, null, null)
    }

    fun delete(item: TodoItem) {
        getDb().delete(Todos.TABLE, "${Todos._ID} = ?", array(item.id.toString()))
    }
}
