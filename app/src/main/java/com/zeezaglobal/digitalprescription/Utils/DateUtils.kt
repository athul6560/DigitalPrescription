package com.zeezaglobal.digitalprescription.Utils

import java.text.SimpleDateFormat
import java.util.Date

class DateUtils {


    companion object {
        fun getCurrentDate(): String {
            val sdf = SimpleDateFormat("dd") // Change format as needed
            // Get the current date and format it
            val currentDate = Date()
            return sdf.format(currentDate)
        }

        fun getCurrentMonth(): String {
            val sdf = SimpleDateFormat("MMM") // Change format as needed
            // Get the current date and format it
            val currentDate = Date()
            return sdf.format(currentDate)
        }
    }
}