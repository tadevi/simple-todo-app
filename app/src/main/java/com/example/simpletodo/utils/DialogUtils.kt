package com.example.simpletodo.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog

object DialogUtils {
    fun makeSimpleDialog(context: Context, title: String, message: String) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(
                android.R.string.yes
            )
            { dialog, _ ->
                dialog.dismiss()
            }
            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.ic_dialog_info)
            .show()
    }
}