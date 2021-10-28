package fit.iterway.processor.utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class DateHelper {
    public static final ZoneId ZONE_ID = ZoneId.systemDefault();
    public static final int MINUTES = 1;
    public static final int HOURS = 2;
    public static final int DAYS = 3;
    static DecimalFormat formatter = new DecimalFormat("00");

    public static boolean isToday(Date date) {

        DateTime today = new DateTime();

        DateTime compared = new DateTime(date);

        return DateTimeComparator.getDateOnlyInstance().compare(today, compared) == 0;
    }

    public static Date getDateTimeJoda(Date date) {
        DateTime newDateTime = null;

        try {
            newDateTime = new DateTime(date);
            newDateTime = newDateTime.withZoneRetainFields(DateTimeZone.UTC);
            return newDateTime.toDate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date toDefaultTimeZone(Date date, String zoneID) {
        DateTime dateTime = getDateTimeWithZone(date, DateTimeZone.forTimeZone(TimeZone.getTimeZone(zoneID)));
        return dateTime.toLocalDateTime().toDate();
    }

    public static DateTime getDateTimeWithZone(Date date, DateTimeZone dateTimeZone) {
        DateTime now = new DateTime(date);
        DateTime nowUtc = now.withZone(dateTimeZone);
        return nowUtc;
    }

    public static Date getDateByZone(Date dateToConvert, String zoneId) {
        return toDefaultTimeZone(getDateTimeJoda(dateToConvert), zoneId);
    }

    public static String getStringDateByZone(Date dateToConvert, String offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateToConvert);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");

        sdf.setTimeZone(TimeZone.getTimeZone(DateHelper.getTimeZone(offset)));
        return sdf.format(calendar.getTime());
    }

    public static String getTimeZone(String rawOffset) {
        int timeZoneCompany = (int) TimeUnit.HOURS.toMillis(Integer.parseInt(rawOffset.equals("") ? "0" : rawOffset));
        List<String> listTimeZone = new ArrayList<>(Arrays.asList(TimeZone.getAvailableIDs(timeZoneCompany)));
        Optional<String> timeZone = listTimeZone.stream().filter(s -> s.contains("Etc/")).findFirst();

        String newTimeZone = TimeZone.getTimeZone(timeZone.get()).getDisplayName(false, TimeZone.SHORT);
        return newTimeZone;
    }

    public static LocalDateTime convertToLocalDateTime(Date in) {
        if (in == null) return null;
        return LocalDateTime.fromDateFields(in);
    }

    public static String durationHHMM(Double minutes) {
        int hours = Math.abs(minutes.intValue()) / 60;
        int minutes2 = (Math.abs(minutes.intValue()) - (60 * hours));
        String hFormatted = formatter.format(hours);
        String mFormatted = formatter.format(minutes2);
        return hFormatted + ":" + mFormatted;
    }

    public static String durationHHMM(Long seconds) {
        int hours = Math.abs(seconds.intValue()) / 3600;
        int minutes = (Math.abs(seconds.intValue()) - (3600 * hours)) / 60;
        String hFormatted = formatter.format(hours);
        String mFormatted = formatter.format(minutes);
        return hFormatted + ":" + mFormatted;
    }

    public static Date getZonedDate(Date dateToConvert) {
        return dateToConvert == null ? null : java.sql.Timestamp.valueOf(dateToConvert.toString());
    }

    public static Date convertToDate(LocalDateTime dateToConvert) {
        return dateToConvert == null ? null : dateToConvert.toDate();
    }

    public static String formattedTime(Integer num) {
        return durationHHMM(num.doubleValue());
    }

//    public static LocalDateTime convertToLocalDateTimeZoneIdUTC(Date dateToConvert) {
//        return dateToConvert == null ? null : dateToConvert.toInstant()
//                .atZone(ZoneId.of("UTC"))
//                .toLocalDateTime();
//    }

    public static org.joda.time.LocalDate convertUtilDateToJodaLocalDate(Date date) {
        if (date == null) return null;
        DateTime dt = new DateTime(date);
        return dt.toLocalDate();
    }

    public static org.joda.time.LocalDateTime convertUtilDateToJodaLocalDateTime(Date date) {
        if (date == null) return null;
        DateTime dt = new DateTime(date);
        return dt.toLocalDateTime();
    }


    public static LocalDate jodaTimeToJavaLocalDate(org.joda.time.LocalDate localDate) {
        return LocalDate.of(localDate.getYear(), localDate.getMonthOfYear(), localDate.getDayOfMonth());
    }

    public static LocalTime jodaTimeToJavaLocalTime(org.joda.time.LocalDateTime localTime) {
        return LocalTime.of(localTime.getHourOfDay(), localTime.getMinuteOfHour());
    }

    public static LocalDate convertToLocalDate(Date dateToConvert) {
        return jodaTimeToJavaLocalDate(convertUtilDateToJodaLocalDate(dateToConvert));
    }

    public static LocalTime convertToLocalTime(Date dateToConvert) {
        return jodaTimeToJavaLocalTime(convertUtilDateToJodaLocalDateTime(dateToConvert));
    }

    public static Long diffBetweenDate(Temporal start, Temporal end, int scale) {
        Duration duration = Duration.between(end, start);
        switch (scale) {
            case MINUTES:
                return duration.toMinutes();
            case HOURS:
                return duration.toHours();
            case DAYS:
                return duration.toDays();
            default:
                return null;
        }
    }

//    public static LocalDateTime jodaTimeToJavaLocalDateTime(org.joda.time.LocalDateTime localDateTime) {
//        return LocalDateTime.of(
//                localDateTime.getYear(),
//                localDateTime.getMonthOfYear(),
//                localDateTime.getDayOfMonth(),
//                localDateTime.getHourOfDay(),
//                localDateTime.getMinuteOfHour(),
//                localDateTime.getSecondOfMinute());
//    }
//
//    public static String getFormatedDate(Date d) {
//        LocalDate date = convertToLocalDate(d);
//        return date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
//    }
//
//
//    public static String dateFormatReport(Date date) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyyy");
//        return dateFormat.format(date);
//    }
//
//    public static String formatTimeReport(Date date) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmss");
//        return dateFormat.format(date);
//    }
//
//    public static Date getDateByStringSQL(String dateString) {
//        Date date = null;
//        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        if (!dateString.equals("")) {
//            try {
//                date = formatter.parse(dateString);
//                return date;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    public static Date plusSeconds(Date date, int seconds) {
//        LocalDateTime d = LocalDateTime.fromDateFields(date);
//        return d.plusSeconds(seconds).toDate();
//    }
//
//    public static Date plusMinutes(Date date, int minutes) {
//        LocalDateTime d = LocalDateTime.fromDateFields(date);
//        return d.plusMinutes(minutes).toDate();
//    }
//
//    public static Date plusHours(Date date, int hours) {
//        LocalDateTime d = LocalDateTime.fromDateFields(date);
//        return d.plusHours(hours).toDate();
//    }
//
//    public static Date plusDays(Date date, int days) {
//        LocalDateTime d = LocalDateTime.fromDateFields(date);
//        return d.plusDays(days).toDate();
//    }
//
//    public static Date getDateByStringWeb(String dateString, String timezone) {
//        Date date = null;
//        DateTime newDateTime = null;
//        /*2019-01-12 00:00:00*/
//        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");
//        formatter.setTimeZone(TimeZone.getTimeZone(timezone));
//        if (!dateString.equals("")) {
//            try {
//                date = formatter.parse(dateString);
//                newDateTime = new DateTime(date, DateTimeZone.UTC);
//                newDateTime = newDateTime.withZoneRetainFields(DateTimeZone.UTC);
//                return newDateTime.toLocalDateTime().toDate();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    public static Date getTimeByStringWeb(String timeString, String timezone) {
//        Date date = null;
//        DateTime newDateTime = null;
//        /*2019-01-12 00:00:00*/
//        DateFormat formatter = new SimpleDateFormat("HH:mm");
//        formatter.setTimeZone(TimeZone.getTimeZone(timezone));
//        if (!timeString.equals("")) {
//            try {
//                date = formatter.parse(timeString);
//                newDateTime = new DateTime(date, DateTimeZone.UTC);
//                newDateTime = newDateTime.withZoneRetainFields(DateTimeZone.UTC);
//                return newDateTime.toLocalDateTime().toDate();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    public static String convertTimeStringToTimeZone(String timeString, String timezone) {
//
//        if (!timeString.equals("")) {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
//            LocalTime time = LocalTime.parse(timeString, formatter);
//            OffsetTime timeUtc = time.atOffset(ZoneOffset.UTC);
//            OffsetTime t = timeUtc.withOffsetSameInstant(ZoneId.of(timezone).getRules().getOffset(Instant.now()));
//            return t.format(formatter);
//        }
//        return null;
//    }
//
//    public static String contrastTimeZone(String timeZoneId) {
//        String gmt = timeZoneId.substring(0, 3);
//        String sign = (timeZoneId.substring(3, 4).equals("+")) ? "-" : "+";
//        String number = timeZoneId.substring(4, timeZoneId.length());
//        return gmt + sign + number;
//    }
//
//    public static Date minusMinutes(Date date, int minutes) {
//        LocalDateTime localDateTime =
//                LocalDateTime.ofInstant(date.toInstant(), ZoneOffset.UTC).minusMinutes(minutes);
//        return Date.from(localDateTime.atZone(ZoneOffset.systemDefault()).toInstant());
//    }
//
//    public static Date utc0DateMinusHours(int hours) {
//        Instant i = Instant.now().atOffset(ZoneOffset.ofHours(0)).toInstant();
//        LocalDateTime localDateTime = LocalDateTime.ofInstant(i, ZoneOffset.UTC).minusHours(hours);
//        return Date.from(localDateTime.atZone(ZoneOffset.systemDefault()).toInstant());
//    }
//
//    public static Date utc0DatePlusHours(int hours) {
//        Instant i = Instant.now().atOffset(ZoneOffset.ofHours(0)).toInstant();
//        LocalDateTime localDateTime = LocalDateTime.ofInstant(i, ZoneOffset.UTC).withHour(hours);
//        return Date.from(localDateTime.atZone(ZoneOffset.systemDefault()).toInstant());
//    }
//
//    public static Date utc0DateWithHourMinuteAndSecond(int hours, int minutes, int seconds) {
//        Instant i = Instant.now().atOffset(ZoneOffset.ofHours(0)).toInstant();
//        LocalDateTime localDateTime = LocalDateTime.ofInstant(i, ZoneOffset.UTC).withHour(hours).withMinute(minutes).withSecond(seconds);
//        return Date.from(localDateTime.atZone(ZoneOffset.systemDefault()).toInstant());
//    }
//
//    public static LocalTime utc0LocalTimeWithHourMinuteAndSecond(int hours, int minutes, int seconds) {
//        Instant i = Instant.now().atOffset(ZoneOffset.ofHours(0)).toInstant();
//        LocalDateTime localDateTime = LocalDateTime.ofInstant(i, ZoneOffset.UTC).withHour(hours).withMinute(minutes).withSecond(seconds).withNano(0);
//        return localDateTime.atZone(ZoneOffset.systemDefault()).toLocalTime();
//    }
//
    public static Date nowInUTC0() {
        Instant i = Instant.now().atOffset(ZoneOffset.ofHours(0)).toInstant();
        OffsetDateTime odt = OffsetDateTime.now( ZoneOffset.UTC );
//        LocalDateTime localDateTime = LocalDateTime.ofInstant(i, ZoneOffset.UTC);
//        return Date.from(localDateTime.atZone(ZoneOffset.systemDefault()).toInstant());
        return Date.from(odt.toInstant());
    }
//
//    public static LocalDateTime localDateTimeInUTC0() {
//        Instant i = Instant.now().atOffset(ZoneOffset.ofHours(0)).toInstant();
//        LocalDateTime localDateTime = LocalDateTime.ofInstant(i, ZoneOffset.UTC);
//        return localDateTime.atZone(ZoneOffset.systemDefault()).toLocalDateTime();
//    }
//
//    public static LocalTime localTimeInTimezoneOffset(Timezone timezone) {
//        //TODO PARAMETER OFFSET MEMBER
//        Instant i = Instant.now().atOffset(ZoneOffset.ofHours(0)).toInstant();
//        LocalDateTime localDateTime = LocalDateTime.ofInstant(i, ZoneOffset.UTC);
//        return localDateTime.atZone(ZoneOffset.systemDefault()).toLocalTime().plusHours((int) timezone.getOffset());
//    }
//
//    public static Date nowAtTimeZoneOffset(Long offsetHours, Long offsetMinutes) {
//        return Date.from(localDateTimeInTimezoneOffset(offsetHours, offsetMinutes)
//                .atZone(ZoneOffset.systemDefault()).toInstant());
//    }
//
//    public static LocalTime localTimeAtTimezoneOffset(Long offsetHours, Long offsetMinutes) {
//        return localDateTimeInTimezoneOffset(offsetHours, offsetMinutes).toLocalTime();
//    }
//
//    public static DayOfWeek dayOfWeekInTimezoneOffset(Long offsetHours, Long offsetMinutes) {
//        return localDateTimeInTimezoneOffset(offsetHours, offsetMinutes).getDayOfWeek();
//    }
//
//    public static LocalDateTime localDateTimeInTimezoneOffset(Long offsetHours, Long offsetMinutes) {
//        Instant i = Instant.now().atOffset(ZoneOffset.ofHours(0)).toInstant();
//        return LocalDateTime.ofInstant(i, ZoneOffset.UTC)
//                .plusHours(offsetHours).plusMinutes(offsetMinutes);
//    }
//
//    public static DayOfWeek dayOfWeekInUTC0() {
//        Instant i = Instant.now().atOffset(ZoneOffset.ofHours(0)).toInstant();
//        LocalDateTime localDateTime = LocalDateTime.ofInstant(i, ZoneOffset.UTC);
//        return localDateTime.atZone(ZoneOffset.systemDefault()).toLocalDateTime().getDayOfWeek();
//    }
}
