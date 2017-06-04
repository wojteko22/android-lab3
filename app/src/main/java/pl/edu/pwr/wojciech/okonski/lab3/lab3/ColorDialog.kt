package pl.edu.pwr.wojciech.okonski.lab3.lab3

import android.app.Dialog
import android.app.DialogFragment
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog


class ColorDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(R.string.pick_a_shape_color)
                .setItems(R.array.colors, DialogInterface.OnClickListener { dialog, which ->

                })
        return builder.create()
    }
}