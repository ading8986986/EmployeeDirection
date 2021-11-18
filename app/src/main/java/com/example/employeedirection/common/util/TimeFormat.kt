package com.example.todolist.common.util

import java.text.SimpleDateFormat
import java.util.*

object TimeFormat {

    fun getDateFormattedString(date: Date): String {
        return SimpleDateFormat("yyyy-mm-dd hh:mm:ss", Locale.getDefault())
            .format(date)
    }
}