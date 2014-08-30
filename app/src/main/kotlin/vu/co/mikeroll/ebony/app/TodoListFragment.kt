package vu.co.mikeroll.ebony.app

import android.app.Fragment
import android.os.Bundle
import android.app.ListFragment
import vu.co.mikeroll.ebony.data.TodoList
import org.json.JSONArray

public class TodoListFragment() : ListFragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val input : String = """
          [
             { "c": "Entry 1", "i": false },
             { "c": "Entry 2", "i": true },
             { "c": "Entry 3" }
          ]
        """
        setListAdapter(TodoListAdapter(getActivity()!!, TodoList(JSONArray(input: String?))))
    }
}
