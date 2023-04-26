package com.example.fileexplorersample.util

import java.text.SimpleDateFormat
import java.util.*

fun dateFormatMM_dd_yy_hh_mm(date: Date): String {
    val newFormat = "MMM dd yy hh:mm"
    val formatter = SimpleDateFormat(newFormat, Locale.getDefault())
    return formatter.format(date)
}