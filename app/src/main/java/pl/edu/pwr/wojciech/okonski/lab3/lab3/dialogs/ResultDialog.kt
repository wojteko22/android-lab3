package pl.edu.pwr.wojciech.okonski.lab3.lab3.dialogs

import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.support.v7.app.AlertDialog
import pl.edu.pwr.wojciech.okonski.lab3.lab3.R

class ResultDialog(points: Int) : DialogFragment() {
    val message: String by lazy {
        val template = getString(R.string.result_message)
        String.format(template, points)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = AlertDialog.Builder(activity)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .create()
}