package com.example.simpletodo.utils

import android.content.res.Resources.Theme
import android.util.TypedValue
import android.view.View
import androidx.annotation.ColorInt
import com.example.simpletodo.R
import com.google.android.material.snackbar.Snackbar


object SnackBarUtils {
    fun snackBarWithTextButton(
        mainView: View,
        title: String,
        buttonTitle: String,
        onClick: ((View) -> Unit)?
    ) {
        val snackBar = Snackbar.make(mainView, title, Snackbar.LENGTH_LONG)
        val typedValue = TypedValue()
        val theme: Theme = mainView.context.theme

        theme.resolveAttribute(R.attr.snackBarTextColor, typedValue, true)
        @ColorInt val color = typedValue.data
        snackBar.setTextColor(color)

        theme.resolveAttribute(R.attr.snackBarBgColor, typedValue, true)
        @ColorInt val bgColor = typedValue.data
        snackBar.setBackgroundTint(bgColor)

        theme.resolveAttribute(R.attr.snackBarActionTextColor, typedValue, true)
        @ColorInt val textColor = typedValue.data
        snackBar.setActionTextColor(textColor)

        snackBar.setAction(buttonTitle) {
            onClick?.invoke(it)
        }
        snackBar.show()
    }
}
