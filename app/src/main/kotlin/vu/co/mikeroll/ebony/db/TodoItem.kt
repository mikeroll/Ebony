package vu.co.mikeroll.ebony.db

import android.os.Parcelable
import android.os.Parcel
import android.database.Cursor

public data class TodoItem(var content: String, var important: Boolean, var id: Long = -1) : Parcelable {

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(content)
        dest.writeByte(if (important) 1 else 0)
        dest.writeLong(id)
    }

    class object {
        val CREATOR = object : Parcelable.Creator<TodoItem> {
            override fun createFromParcel(p: Parcel) =
                TodoItem(p.readString(), p.readByte() != 0.toByte(), p.readLong())

            override fun newArray(size: Int) = Array(size, { TodoItem("", false) })
        }
    }
}

public fun TodoItem(row: Cursor) : TodoItem =
    TodoItem(row.getString(1) ?: "" , row.getInt(2) != 0, row.getLong(0))
