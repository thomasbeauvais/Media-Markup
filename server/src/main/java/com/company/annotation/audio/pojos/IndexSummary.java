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
    private double time;
    private String name;
    private String description;
    private String id;

    public IndexSummary() {

    }

    public String getId() {
        return this.id;
    }

    public void setId( String id ) {
        this.id = id;
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

    public double getTime() {
        return time;
    }

    public void setTime( double time ) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }
}
