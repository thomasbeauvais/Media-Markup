package org.branch.common.utils;

import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

/**
 *
 */
public class HumanReadable
{
    private HumanReadable()
    {
    }

    public static String convertBytes(long bytes)
    {
        return convertBytes(bytes, true);
    }

    public static String convertBytes(long bytes, boolean si)
    {
        return FileUtils.byteCountToDisplaySize(bytes);
//        final int unit = si ? 1000 : 1024;
//        if (bytes < unit)
//        {
//            return bytes + " B";
//        }
//
//        final int exp = (int) (Math.log(bytes) / Math.log(unit));
//        final String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
//        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    private static final PeriodFormatter dateFormat = new PeriodFormatterBuilder().appendMinutes()
                                                                                  .appendSuffix(":")
                                                                                  .appendSeconds()
                                                                                  .appendSuffix(":")
                                                                                  .appendMillis().toFormatter();

    public static String convertMillis(long millis)
    {
        return new Period(millis).toString(dateFormat);
    }
}
