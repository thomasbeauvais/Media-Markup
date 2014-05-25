package org.branch.common.utils;

import java.util.concurrent.TimeUnit;

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
        final int unit = si ? 1000 : 1024;
        if (bytes < unit)
        {
            return bytes + " B";
        }

        final int exp = (int) (Math.log(bytes) / Math.log(unit));
        final String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    public static String convertMillis(long millis)
    {
        return String.format("%d:%d",
             TimeUnit.MILLISECONDS.toMinutes(millis),
             TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );
    }
}
