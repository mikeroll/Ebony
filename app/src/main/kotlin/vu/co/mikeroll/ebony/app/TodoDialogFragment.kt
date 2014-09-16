package vu.co.mikeroll.ebony.app

import vu.co.mikeroll.ebony.db.TodoItem
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.app.DialogFragment
import android.widget.EditText
import android.widget.ImageButton

public class TodoDialogFragment private() : DialogFragment() {

    class object {
        fun new(item: TodoItem): TodoDialogFragment {
            val f = TodoDialogFragment()
            val args = Bundle()
            args.putParcelable("item", item)
            f.setArguments(args)
            return f
        }
    }

    var item: TodoItem? = null
        private set
    private var edit: EditText? = null
    private var okButton: ImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        item = getArguments()?.getParcelable("item")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_todo_dialog, container)!!
        edit = view.findViewById(R.id.dlg_content) as EditText
        edit!!.append(item!!.content)
        okButton = view.findViewById(R.id.dlg_ok_btn) as ImageButton
        okButton!!.setOnClickListener { update(); dismiss() }
        return view
    }

    trait OnUpdateListener {
        fun onUpdate(item : TodoItem)
    }

    var onUpdate : (TodoItem) -> Unit = {}

    private fun update() {
        val newContent = edit!!.getText().toString()
        if (item!!.content != newContent) {
            onUpdate(item!!)
        }
    }
}
