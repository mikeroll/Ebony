package vu.co.mikeroll.ebony.app

import android.app.DialogFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ToggleButton

import com.jakewharton.kotterknife.*

import vu.co.mikeroll.ebony.db.TodoItem
import android.view.WindowManager

public class TodoDialogFragment() : DialogFragment() {

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
    private val edit: EditText by bindView(R.id.dlg_content)
    private val okButton: ImageButton by bindView(R.id.dlg_ok_btn)
    private val importantButton: ToggleButton by bindView(R.id.dlg_important_btn)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        item = getArguments()?.getParcelable("item")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getDialog()?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return inflater.inflate(R.layout.fragment_todo_dialog, container)!!
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        edit.append(item!!.content)
        edit.setOnFocusChangeListener { (v, f) -> if (f) {
            getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }}
        okButton.setOnClickListener { if (check()) { update(); dismiss() } }
        importantButton.setChecked(item!!.important)
        super.onActivityCreated(savedInstanceState)
    }

    private fun check(): Boolean {
        val empty = edit.getText().toString().trim().equalsIgnoreCase("")
        if (empty) edit.setError(getResources()?.getString(R.string.todo_error_empty))
        return !empty
    }

    var onUpdate : (TodoItem) -> Unit = {}

    private fun update() {
        val newContent = edit.getText().toString()
        val newIsImportant = importantButton.isChecked()
        if (item!!.content != newContent || item!!.important != newIsImportant) {
            item!!.content = newContent
            item!!.important = newIsImportant
            onUpdate(item!!)
        }
    }
}
