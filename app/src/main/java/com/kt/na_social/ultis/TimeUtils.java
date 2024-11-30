package com.kt.na_social.ultis;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class TimeUtils {
    public static String getRelativeTime(String dateString) {
        // Parse the input date string
        DateTimeFormatter formatter = null; // For ISO 8601 format
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ISO_DATE_TIME;
            LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
            // Convert to ZonedDateTime for accurate time zone handling
            ZonedDateTime inputTime = dateTime.atZone(ZoneId.systemDefault());
            ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault());

            // Calculate the difference
            long days = ChronoUnit.DAYS.between(inputTime, now);
            long months = ChronoUnit.MONTHS.between(inputTime, now);

            // Determine relative time
            if (months > 12) {
                // Format and return the date string
                return inputTime.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));
            } else if (days >= 30) {
                return months + " months ago";
            } else if (days > 0) {
                return days + " days ago";
            } else {
                long hours = ChronoUnit.HOURS.between(inputTime, now);
                return hours == 0 ? "Just now" : hours + " hours ago";
            }
        }
        return "Now Days";
    }
}
