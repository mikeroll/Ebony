package vu.co.mikeroll.ebony.app

import vu.co.mikeroll.ebony.db.TodoItem
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.app.DialogFragment
import android.widget.EditText
import android.widget.ImageButton
import android.view.Window
import android.widget.ToggleButton

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
    private var importantButton: ToggleButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        item = getArguments()?.getParcelable("item")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getDialog()?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val view = inflater.inflate(R.layout.fragment_todo_dialog, container)!!
        edit = view.findViewById(R.id.dlg_content) as EditText
        edit!!.append(item!!.content)
        okButton = view.findViewById(R.id.dlg_ok_btn) as ImageButton
        okButton!!.setOnClickListener { if (check()) { update(); dismiss() } }
        importantButton = view.findViewById(R.id.dlg_important_btn) as ToggleButton
        importantButton!!.setChecked(item!!.important)
        return view
    }

    private fun check(): Boolean {
        val empty = edit!!.getText().toString().trim().equalsIgnoreCase("")
        if (empty) edit!!.setError(getResources()?.getString(R.string.todo_error_empty))
        return !empty
    }

    var onUpdate : (TodoItem) -> Unit = {}

    private fun update() {
        val newContent = edit!!.getText().toString()
        val newIsImportant = importantButton!!.isChecked()
        if (item!!.content != newContent || item!!.important != newIsImportant) {
            item!!.content = newContent
            item!!.important = newIsImportant
            onUpdate(item!!)
        }
    }
}
