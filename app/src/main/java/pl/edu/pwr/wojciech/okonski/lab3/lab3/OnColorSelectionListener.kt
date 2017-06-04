package pl.edu.pwr.wojciech.okonski.lab3.lab3

import android.app.DialogFragment

interface OnColorSelectionListener {
    fun onColorSelection(color: Int, dialog: DialogFragment)
}