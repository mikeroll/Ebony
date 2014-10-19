package vu.co.mikeroll.ebony.app

import android.database.CursorWrapper
import android.database.Cursor

public class SwipeCursorWrapper(cursor: Cursor, private val hiddenPositions: IntArray)
           : CursorWrapper(if (cursor is CursorWrapper) cursor.getWrappedCursor() else cursor) {

    private var virtualPosition: Int = -1
    private var positions: List<Int>
    {
        val original = IntRange(0, super.getCount() - 1)
        positions = original.subtract(hiddenPositions.toList()).toSortedList()
    }

    override fun moveToPosition(position: Int): Boolean {
        virtualPosition = position
        return super.moveToPosition(positions[position])
    }

    override fun getCount() = positions.size
    override fun getPosition() = virtualPosition
}