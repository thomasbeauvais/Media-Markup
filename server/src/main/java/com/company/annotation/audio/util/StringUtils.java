package com.company.annotation.audio.util;

import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: tbeauvais
 * Date: 5/27/12
 * Time: 5:50 PM
 * To change this template use File | Settings | File Templates.
 */
public final class StringUtils {
    private StringUtils() {}

    public static String getTimeStringFromSeconds( long millis ) {
        return String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );
    }
}
