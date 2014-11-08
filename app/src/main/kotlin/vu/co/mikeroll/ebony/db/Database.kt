package vu.co.mikeroll.ebony.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

public object Database {

    private var dbHelper: SQLiteOpenHelper? = null

    public fun connect(ctx: Context) : Database {
        dbHelper = dbHelper ?: Helper(ctx.getApplicationContext())
        return this
    }

    protected fun finalize() {
        dbHelper?.close()
    }

    private fun getDb() : SQLiteDatabase {
        return dbHelper?.getWritableDatabase()
            ?: throw IllegalStateException("You should open a DB connection first.")
    }

    fun upsert(item: TodoItem) {
        val entry = ContentValues()
        if (item.id != -1.toLong()) entry.put(Todos._ID, item.id)
        entry.put(Todos.KEY_CONTENT, item.content)
        entry.put(Todos.KEY_IS_IMPORTANT, item.important)
        item.id = getDb().insertWithOnConflict(Todos.TABLE, null, entry, SQLiteDatabase.CONFLICT_REPLACE)
    }

    fun selectAll() : Cursor {
        return getDb().query(Todos.TABLE, null, null, null, null, null, null)!!
    }

    fun delete(ids: List<Long>) {
        getDb().delete(Todos.TABLE, "${Todos._ID} IN (${ids.joinToString()})", null)
    }
}
