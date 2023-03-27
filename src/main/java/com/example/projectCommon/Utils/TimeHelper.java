package com.example.projectCommon.Utils;


import com.example.projectCommon.Config.UtilConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Component
public class TimeHelper {

    public static final Integer DAILY = 1;

    @Autowired
    UtilConfig utilConfig;

    private static final String defaultTimeFormatString = "yyyy-MM-dd HH:mm:ss";
    private static final SimpleDateFormat sdf = new SimpleDateFormat(defaultTimeFormatString);

    public Date timeStampToDate(String timeStampString) throws ParseException {
        sdf.setTimeZone(TimeZone.getTimeZone(utilConfig.getTimeZone()));
        Date date = sdf.parse(timeStampString);

        return date;
    }

    public String getCurrentTimeStamp() {

        sdf.setTimeZone(TimeZone.getTimeZone(utilConfig.getTimeZone()));

        Date currentTime = new Date();
        String currentTimeString = sdf.format(currentTime);

        return currentTimeString;
    }

    public Date getCurrentTime() {
        return new Date();
    }


    public Date getDaysBefore(Date time, Integer interval) {

        // need to consider timezone
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        c.add(Calendar.DATE, -1*interval);
        return c.getTime();
    }

    public Boolean isWithinTimeRange(Date startTime, Date endTime, Date compareTime) {
        return compareTime.compareTo(startTime) >= 0 && compareTime.compareTo(endTime) <= 0;
    }
}
