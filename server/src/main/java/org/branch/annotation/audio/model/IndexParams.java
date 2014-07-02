package org.branch.annotation.audio.model;

/**
 * Index Parameters that describe certain injesti
 */
public class IndexParams {
    private int minSeconds      = 20;

    /**
     * For the minimum number of seconds, what is the minimum number of data points per that amount of seconds.
     *
     * For example,
     *
     * If <code>minSeconds</code> is 20 and <code>minDataPoints</code> is 1000, then the minimum supported visualization
     * would be 20 sec resolution at 1000 pixels (granted that there is no extrapolation).
     */
    private int minDataPoints   = 1000;
}
