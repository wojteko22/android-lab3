package pl.edu.pwr.wojciech.okonski.lab3.lab3

import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.SeekBar

class SizeDialog : DialogFragment() {
    private var sizePercent = 0.1f
    private val listener: OnSizeChangedListener by lazy {
        try {
            activity as OnSizeChangedListener
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement OnSizeChangedListener")
        }
    }

    val seekBar: SeekBar
        get() {
            val seekBar = SeekBar(activity)
            seekBar.max = 1
            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    sizePercent = progress * 0.8f + 0.1f
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })
            return seekBar
        }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = AlertDialog.Builder(activity)
            .setTitle(R.string.apple_size)
            .setView(seekBar)
            .setPositiveButton("OK", { _, _ ->
                listener.onSizeChanged(sizePercent)
            })
            .setNegativeButton(R.string.cancel, null)
            .create()

}