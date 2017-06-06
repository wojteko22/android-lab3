package pl.edu.pwr.wojciech.okonski.lab3.lab3

import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.SeekBar

class SizeDialog(initialPercent: Float) : DialogFragment() {
    private var sizePercent = 0.1f
    private val listener: OnSizeChangedListener by lazy {
        try {
            activity as OnSizeChangedListener
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement OnSizeChangedListener")
        }
    }

    val seekBar: SeekBar by lazy {
        with(SeekBar(activity)) {
            progress = ((initialPercent - 0.1f) / 0.7f * 100f).toInt()
            setOnProgressChangedListener {
                sizePercent = it / 100f * 0.7f + 0.1f
            }
            this
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = AlertDialog.Builder(activity)
            .setTitle(R.string.apple_size)
            .setView(seekBar)
            .setPositiveButton("OK") { _, _ ->
                listener.onSizeChanged(sizePercent)
            }
            .setNegativeButton(R.string.cancel, null)
            .create()
}