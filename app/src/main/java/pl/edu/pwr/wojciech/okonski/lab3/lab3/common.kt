package pl.edu.pwr.wojciech.okonski.lab3.lab3

import android.graphics.drawable.ShapeDrawable
import android.widget.SeekBar

fun toTrue(func: () -> Unit): Boolean {
    func()
    return true
}

fun SeekBar.setOnProgressChangedListener(onProgressChanged: (progress: Int) -> Unit) {
    this.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            onProgressChanged(progress)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    })
}

var ShapeDrawable.color: Int
    get() = this.paint.color
    set(color) {
        this.paint.color = color
    }