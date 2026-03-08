package com.droid.ffdpboss

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalTime
import java.time.Duration
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun getTimeLeft(targetTimeString: String): String {
    // Define the time formatter
    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")

    // Parse the target time string into a LocalTime object
    val targetTime = LocalTime.parse(targetTimeString, formatter)

    // Get the current time
    val currentTime = LocalTime.now()

    // Calculate the duration between the current time and the target time
    val duration = Duration.between(currentTime, targetTime)

    // Check if the duration is negative (i.e., target time is on the next day)
    val adjustedDuration = if (duration.isNegative) {
        duration.plusDays(1)
    } else {
        duration
    }

    // Get hours, minutes, and seconds from the duration
    val hoursLeft = adjustedDuration.toHours()
    val minutesLeft = adjustedDuration.toMinutes() % 60
    val secondsLeft = adjustedDuration.seconds % 60

    // Format the result
    return String.format("%d Hr - %d Min", hoursLeft, minutesLeft)
}