package id.revze.myfriendapp.ui

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import id.revze.myfriendapp.R

class InterfaceManager {
    var context: Context

    constructor(context: Context) {
        this.context = context
    }

    constructor(context: Context, testMessage: String) {
        this.context = context
    }

    fun showFailedDialog(message: String) {
        val alertBuilder = AlertDialog.Builder(context)
        alertBuilder.setMessage(message)
                    .setPositiveButton(context.getString(R.string.ok_action),
                                       DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
        alertBuilder.create().show()
    }

    fun showConfirmationDialog(message: String, clickListener: DialogInterface.OnClickListener) {
        val alertBuilder = AlertDialog.Builder(context)
        alertBuilder.setMessage(message)
                    .setPositiveButton(context.getString(R.string.ok_action), clickListener)
                    .setNegativeButton(context.getString(R.string.cancel_action),
                                       DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
        alertBuilder.create().show()
    }
}