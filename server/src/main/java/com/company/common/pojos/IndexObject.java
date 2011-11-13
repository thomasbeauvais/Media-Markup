package com.company.common.pojos;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: tbeauvais
 * Date: 11/5/11
 * Time: 10:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class IndexObject {
    private String m_fileName;

    private int numChannels;

    private ArrayList<Sample> samples = new ArrayList<Sample>();

    private String name;

    private short[] mins;

    private short[] maxes;

    public IndexObject() {

    }

    public IndexObject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addSample( final Sample sample, final boolean update) {
		this.samples.add( sample );

        if ( update ) {
            updateBounds( sample.getSamples() );
        }
	}

	public void setChannels(int numChannels) {
		this.numChannels = numChannels;
	}

	public void updateMins(short[] samples) {
        lcl_updateBounds( samples, this.mins );
    }

    public void updateMaxes(short[] samples) {
        lcl_updateBounds(samples, this.maxes);
    }

    public void updateBounds( short[] samples ) {
        lcl_updateBounds( samples, this.maxes);
        lcl_updateBounds( samples, this.mins);
    }

    public void updateBounds() {
        this.mins   = new short[ numChannels ];
        this.maxes  = new short[ numChannels ];

        for ( Sample sample : samples ) {
            short[] samples1 = sample.getSamples();
            lcl_validateSamples( samples1 );
            for (int i = 0, samples1Length = samples1.length; i < samples1Length; i++) {
                short value = samples1[i];
                this.maxes[ i ] = (short) Math.max( value, this.maxes[ i ] );
                this.mins[ i ] = (short) Math.min( value, this.mins[ i ] );
            }
        }
    }

    public void lcl_updateBounds( short[] samples, short[] bounds ) {
        lcl_validateSamples(samples);

        bounds = new short[ numChannels ];

        if ( samples.length != bounds.length ) {
			throw new RuntimeException( "There is a mismatch between arrays" );
		}

		for ( int i = 0; i < bounds.length; i++ ) {
            if ( bounds == mins ) bounds[ i ] = (short) Math.min( bounds[ i ], samples [ i ] );
            if ( bounds == maxes ) bounds[ i ] = (short) Math.max(bounds[i], samples[i]);
		}
	}

    private void lcl_validateSamples( short[] samples ) {
        if ( samples == null ) {
            throw new RuntimeException( "Samples can't be null" );
        }

        if ( this.numChannels == 0 ) {
            throw new RuntimeException( "The number of channels can't be null when setting a max/min" );
        }
    }

	public Sample[] getSamples() {
		return this.samples.toArray( new Sample[ 0 ] );
	}

	public short[] getMaxes() {
		return this.maxes;
	}

	public short[] getMins() {
		return this.mins;
	}

	public int getNumChannels() {
		return numChannels;
	}

	public void setNumChannels(int numChannels) {
		this.numChannels = numChannels;
	}

	public void setSamples(Sample[] samples) {
		this.samples = new ArrayList<Sample>( Arrays.asList(samples) );
	}

	public void setMins(short[] mins) {
		this.mins = mins;
	}

	public void setMaxes(short[] maxes) {
		this.maxes = maxes;
	}


    public int getNumSamples() {
        return samples.size();
    }
}
