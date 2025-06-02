package com.zeezaglobal.digitalprescription.Utils

import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter


    object AgeUtils {

        // Function to caljhvjhvculate age from date of birth (dob) string in "MM/dd/yyyy" format
        fun calculateAge(dobString: String?): String {
            if (dobString.isNullOrEmpty()) {
                return "Age: Not available"
            }

            // Define the formatter for the "M/d/yyyy" format (supports one or two digits for month and day)
            val formatter = DateTimeFormatter.ofPattern("M/d/yyyy")

            try {
                // Parse the date of birth string into a LocalDate using the custom format
                val dateOfBirth = LocalDate.parse(dobString, formatter)

                // Get the current date
                val currentDate = LocalDate.now()

                // Calculate the period between the current date and the date of birth
                val period = Period.between(dateOfBirth, currentDate)

                // Return the age in years
                return "Age: ${period.years}"
            } catch (e: Exception) {
                // Handle invalid date format
                return "Invalid date format"
            }
        }
    }
