package vu.co.mikeroll.ebony.app

import android.database.CursorWrapper
import android.database.Cursor

public class SwipeCursorWrapper(val cursor: Cursor, private val hiddenPosition: Int) : CursorWrapper(cursor) {

    private var virtualPosition: Int = -1

    override fun moveToPosition(position: Int): Boolean {
        virtualPosition = position
        val moveTo = if (position >= hiddenPosition) position + 1 else position
        return cursor.moveToPosition(moveTo)
    }

    override fun getCount() = cursor.getCount() - 1
    override fun getPosition() = virtualPosition

    override fun move(offset: Int) = moveToPosition(getPosition() + offset)
    override fun moveToFirst() = moveToPosition(0)
    override fun moveToLast() = moveToPosition(getCount() - 1)
    override fun moveToNext() = moveToPosition(getPosition() + 1)
    override fun moveToPrevious() = moveToPosition(getPosition() - 1)

    override fun isBeforeFirst() = getPosition() == -1 || getCount() == 0
    override fun isFirst() = getPosition() == 0 && getCount() != 0
    override fun isLast() = getPosition() == (getCount() - 1) && getCount() != 0
    override fun isAfterLast() = getPosition() == getCount() || getCount() == 0
}