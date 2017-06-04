package pl.edu.pwr.wojciech.okonski.lab3.lab3

import android.app.Dialog
import android.app.DialogFragment
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AlertDialog

class ColorDialog(val titleId: Int) : DialogFragment() {
    private val listener: OnColorSelectionListener by lazy {
        try {
            activity as OnColorSelectionListener
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement OnColorSelectionListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = AlertDialog.Builder(activity)
            .setTitle(titleId)
            .setItems(R.array.colors, { _, which ->
                when (which) {
                    0 -> listener.onColorSelection(Color.RED, this)
                    1 -> listener.onColorSelection(Color.GREEN, this)
                    2 -> listener.onColorSelection(Color.BLUE, this)
                }
            })
            .create()
}