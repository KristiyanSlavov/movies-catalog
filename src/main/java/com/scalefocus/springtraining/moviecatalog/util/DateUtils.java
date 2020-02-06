
package com.scalefocus.springtraining.moviecatalog.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * This class is responsible for converting a Date objects.
 *
 * @author Kristiyan SLavov
 */
public final class DateUtils {

    private DateUtils() {
        throw new AssertionError();
    }

    /**
     * This method converts a {@link LocalDate} object to {@link Date} object
     *
     * @param localDate - the {@link LocalDate} object to be converted
     * @return new {@link Date} object
     */
    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * This method converts a {@link LocalDateTime} object to {@link Date} object
     *
     * @param localDateTime - the {@link LocalDateTime} object to be converted
     * @return new {@link Date} object
     */
    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * This method converts a {@link Date} object to {@link LocalDate} object
     *
     * @param date - the {@link Date} object to be converted
     * @return new {@link LocalDate} object
     */
    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * This method converts a {@link Date} object to {@link LocalDateTime} object
     *
     * @param date - the {@link Date} object to be converted
     * @return new {@link LocalDateTime} object
     */
    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

}
