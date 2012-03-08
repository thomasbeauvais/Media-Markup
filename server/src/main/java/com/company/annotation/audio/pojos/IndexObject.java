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
public class IndexObject extends IndexSummary {
    private ArrayList<Sample> samples = new ArrayList<Sample>();

    private short min;

    private short max;

    public IndexObject() {

    }

    public void updateBoundsForValue( Sample sample ) {
        short value = sample.getValue();

        this.max = (short) Math.max( value, this.max );
        this.min = (short) Math.min( value, this.min );
    }

    public short getMax() {
        return this.max;
    }

    public short getMin() {
        return this.min;
    }

    public void setMin( short min ) {
        this.min = min;
    }

    public void setMax( short max ) {
        this.max = max;
    }

    public void addSample( final Sample sample, final boolean update ) {
        this.samples.add( sample );

        if( update ) {
            updateBoundsForValue( sample );
        }
    }

    public void updateBounds() {
        for( Sample sample : samples ) {
            updateBoundsForValue( sample );
        }
    }

    public Sample[] getSamples() {
        return this.samples.toArray( new Sample[ 0 ] );
    }

    public void setSamples( Sample[] samples ) {
        this.samples = new ArrayList<Sample>( Arrays.asList( samples ) );
    }

    public int getNumSamples() {
        return samples.size();
    }
}
