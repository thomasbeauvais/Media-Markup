package org.branch.common.utils;

/**
 *
 */
public class FileUtils
{
    private FileUtils()
    {
    }

    public static String humanReadableBytes(long bytes)
    {
        return humanReadableBytes(bytes, true);
    }

    public static String humanReadableBytes(long bytes, boolean si)
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
}
