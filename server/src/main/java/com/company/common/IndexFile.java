//package com.company.common;
//import javax.jdo.annotations.*;
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.Arrays;
//
//@PersistenceCapable(identityType = IdentityType.APPLICATION)
//public class IndexObject implements Serializable {
//
//    @PrimaryKey
//    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
//    Long id;
//
//    @Persistent
//    private int numChannels;
//
//    @Persistent(serialized="true")
//	private ArrayList<Sample> samples;
//
//    @Persistent
//    private String name;
//
//    @Persistent
//	private short[] mins;
//
//    @Persistent
//    private short[] maxes;
//
//    public IndexObject() {
//
//    }
//
//	public IndexObject(String name) {
//		this.samples    = new ArrayList<Sample>();
//	    this.name       = name;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void addSample(Sample sample) {
//		this.samples.add(sample);
//	}
//
//	public void setChannels(int numChannels) {
//		this.numChannels = numChannels;
//	}
//
//	public void updateMins(short[] samples) {
//		if ( samples == null ) {
//			throw new RuntimeException( "Samples can't be null" );
//		}
//
//		if ( this.mins == null ) {
//			if ( this.numChannels == 0 ) {
//				throw new RuntimeException( "The number of channels can't be null when setting a max/min" );
//			}
//
//			this.mins = new short[ numChannels ];
//		}
//
//		if ( samples.length != mins.length ) {
//			throw new RuntimeException( "There is a mismatch between arrays" );
//		}
//
//		for ( int i = 0; i < mins.length; i++ ) {
//			mins[ i ] = (short) Math.min( mins[ i ], samples [ i ] );
//		}
//	}
//
//	public void updateMaxes(short[] samples) {
//		if ( samples == null ) {
//			throw new RuntimeException( "Samples can't be null" );
//		}
//
//		if ( this.maxes == null ) {
//			if ( this.numChannels == 0 ) {
//				throw new RuntimeException( "The number of channels can't be null when setting a max/min" );
//			}
//
//			this.maxes = new short[ numChannels ];
//		}
//
//		if ( samples.length != maxes.length ) {
//			throw new RuntimeException( "There is a mismatch between arrays" );
//		}
//
//		for ( int i = 0; i < maxes.length; i++ ) {
//			maxes[ i ] = (short) Math.max( maxes[ i ], samples [ i ] );
//		}
//	}
//
//	public Sample[] getSamples() {
//		return this.samples.toArray( new Sample[ 0 ] );
//	}
//
//	public short[] getMaxes() {
//		return this.maxes;
//	}
//
//	public short[] getMins() {
//		return this.mins;
//	}
//
//	public int getNumChannels() {
//		return numChannels;
//	}
//
//	public void setNumChannels(int numChannels) {
//		this.numChannels = numChannels;
//	}
//
//	public void setSamples(Sample[] samples) {
//		this.samples = new ArrayList<Sample>( Arrays.asList(samples) );
//	}
//
//	public void setMins(short[] mins) {
//		this.mins = mins;
//	}
//
//	public void setMaxes(short[] maxes) {
//		this.maxes = maxes;
//	}
//
//
//
//}
