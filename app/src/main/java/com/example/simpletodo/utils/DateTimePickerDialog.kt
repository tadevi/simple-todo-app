package com.example.simpletodo.utils


import androidx.appcompat.app.AppCompatActivity
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import java.text.SimpleDateFormat
import java.util.*

fun AppCompatActivity.showDatePickerDialog(
    callback: DatePickerDialog.OnDateSetListener,
    default: Calendar? = null
) {
    val now = default ?: Calendar.getInstance()
    val dpd = DatePickerDialog.newInstance(
        callback,
        now.get(Calendar.YEAR),
        now.get(Calendar.MONTH),
        now.get(Calendar.DAY_OF_MONTH)
    )
    dpd.show(this.supportFragmentManager, "Date Picker Dialog")
}

fun AppCompatActivity.showTimePickerDialog(
    callback: TimePickerDialog.OnTimeSetListener,
    default: Calendar? = null
) {
    val now = default ?: Calendar.getInstance()
    val tpd = TimePickerDialog.newInstance(
        callback,
        now.get(Calendar.HOUR_OF_DAY),
        now.get(Calendar.MINUTE),
        true
    )
    tpd.show(this.supportFragmentManager, "Time Picker Dialog")
}

fun formatDate(calendar: Calendar): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy")
    return sdf.format(calendar.time)
}

fun formatTime(calendar: Calendar): String {
    val sdf = SimpleDateFormat("hh:mm")
    return sdf.format(calendar.time)
}