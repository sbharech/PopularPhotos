package com.example.suraj.popularphotos;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.DisplayMetrics;

/**
 * Created by suraj on 05/02/15.
 */
public class Utility {
    public static String getRelativeTime(long epoch) {
        String relativeTime = DateUtils.getRelativeTimeSpanString(epoch * 1000, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        relativeTime = relativeTime.replaceAll("ago","");
        relativeTime = relativeTime.replaceAll("minute.?", "m");
        relativeTime = relativeTime.replaceAll("second.?", "s");
        relativeTime = relativeTime.replaceAll("hour.?", "h");
        relativeTime = relativeTime.replaceAll("day.?", "d");
        relativeTime = relativeTime.replaceAll("week.?", "W");
        relativeTime = relativeTime.replaceAll("year.?", "y");
        relativeTime = relativeTime.replaceAll(" ", "");
        return relativeTime;
    }

    public static int getDisplayWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }
}
