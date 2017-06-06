package pl.edu.pwr.wojciech.okonski.lab3.lab3

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.view.Gravity
import android.widget.EditText

class TimeDialog : DialogFragment() {
    private val listener: OnTimeSetListener by lazy {
        try {
            activity as OnTimeSetListener
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement OnTimeSetListener")
        }
    }

    val editText: EditText by lazy {
        val editText = EditText(activity)
        editText.filters = arrayOf(InputFilter.LengthFilter(2))
        editText.inputType = InputType.TYPE_CLASS_NUMBER
        editText.gravity = Gravity.CENTER_HORIZONTAL
        editText
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = AlertDialog.Builder(activity)
            .setTitle(R.string.set_time)
            .setView(editText)
            .setPositiveButton("OK") { _, _ ->
                listener.onTimeSet(editText.text.toString().toInt())
            }
            .setNegativeButton(R.string.cancel, null)
            .create()
}