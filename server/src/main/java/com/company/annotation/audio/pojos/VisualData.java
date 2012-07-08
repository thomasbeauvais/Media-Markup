package com.company.annotation.audio.pojos;

/**
 * Created with IntelliJ IDEA.
 * User: tbeauvais
 * Date: 5/27/12
 * Time: 7:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class VisualData {

    private int[] visualSamples;
    private long[] samplePositions;

    public VisualData() {
    }

    public int[] getVisualSamples() {
        return visualSamples;
    }

    public void setVisualSamples(int[] visualSamples) {
        this.visualSamples = visualSamples;
    }

    public void setVisualPositions(long[] samplePositions) {
        this.samplePositions = samplePositions;
    }
    
    public long[] getVisualPositions()  {
        return this.samplePositions;
    }
}

