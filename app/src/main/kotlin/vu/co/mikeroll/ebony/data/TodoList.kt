package vu.co.mikeroll.ebony.data

import java.util.ArrayList
import org.json.JSONArray

public data class TodoItem(var content: String, var important: Boolean)

public class TodoList(source: JSONArray) : ArrayList<TodoItem>() {
    {
        for (i in IntRange(0, source.length() - 1)) {
            val item = source.getJSONObject(i)
            this.add(TodoItem(item.optString("c", "")!!, item.optBoolean("i", false)))
        }
    }
}
