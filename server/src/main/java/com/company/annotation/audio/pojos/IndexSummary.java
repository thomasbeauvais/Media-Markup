package com.company.annotation.audio.pojos;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: tbeauvais
 * Date: 11/5/11
 * Time: 10:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class IndexSummary {
    private int numChannels;

    private String name;

    private float time;

    private String description;

    public IndexSummary() {

    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public void setChannels( int numChannels ) {
        this.numChannels = numChannels;
    }

    public int getNumChannels() {
        return numChannels;
    }

    public void setNumChannels( int numChannels ) {
        this.numChannels = numChannels;
    }

    public float getTime() {
        return time;
    }

    public void setTime( float time ) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }
}
