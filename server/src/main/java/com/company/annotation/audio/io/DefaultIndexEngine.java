package com.company.annotation.audio.io;

import com.company.annotation.audio.api.IIndexEngine;
import com.company.annotation.audio.pojos.IndexWithSamples;
import com.company.annotation.audio.pojos.Sample;
import com.company.annotation.audio.pojos.SampleList;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.SampleBuffer;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.*;

/**
 */
public class DefaultIndexEngine implements IIndexEngine {
    private static final Logger LOGGER = Logger.getLogger( IIndexEngine.class );

    public synchronized SampleList createIndexForAudioStream( @NotNull InputStream input, @NotNull String indexName ) {

		long start = System.currentTimeMillis();

		try {
            final Bitstream bitstream   = new Bitstream( input );
            final Decoder decoder       = new Decoder();
            final SampleList sampleList = new SampleList();
            final IndexWithSamples index    = new IndexWithSamples();

            float timeStamp         = 0;
            long pos                = 0;
            Header h                = null;
            SampleBuffer output     = null;
            short[] samples         = null;

            while((h = bitstream.readFrame()) != null) {
				pos             = bitstream.pos();
				output          = (SampleBuffer) decoder.decodeFrame(h, bitstream);

                samples         = getSamples( output.getBuffer(), output.getChannelCount() );

                sampleList.addSample( new Sample( timeStamp, pos, samples[0] ), false );

				timeStamp +=  ( (float) output.getBufferLength() ) / output.getChannelCount() / output.getSampleFrequency();
				bitstream.closeFrame();
			}

            index.setTime( timeStamp );
            index.setName( indexName );
            index.setChannels( output.getChannelCount() );
            index.setSampleList( sampleList );

            sampleList.setIndexSummary( index );
            sampleList.updateBounds();

			final long end = System.currentTimeMillis();

			LOGGER.info( "Audio stream indexed in " + (end-start)/1000. + " seconds.");
			
			return sampleList;
		} catch (Exception e) {
            LOGGER.error( e );

            e.printStackTrace();

			throw new RuntimeException( e );
		} finally {
            try {
                input.close();
            } catch( Exception e ) {
            }
        }
	}

//	private static int[] getLargestGap(short[] maxes, short[] mins) {
//		int[] ret = new int[ maxes.length ];
//
//		for ( int i = 0; i < maxes.length; i++ ) {
//			ret[ i ] = maxes[ i ] + ( mins[ i ] * -1 );
//		}
//
//		return ret;
//	}
//
//	private static String getSampleStr(int[] samples) {
//		StringBuffer str = new StringBuffer();
//		for ( int i = 0; i < samples.length; i++) {
//			str.append( samples[ i ] ).append("\t" );
//		}
//
//		return str.toString();
//	}
//
//
//	private static String getSampleStr(short[] samples) {
//		StringBuffer str = new StringBuffer();
//		for ( int i = 0; i < samples.length; i++) {
//			str.append( samples[ i ] ).append("\t" );
//		}
//
//		return str.toString();
//	}

	private static short[] getSamples(short[] buffer, int numChannels) {
		short[] shorts = new short[ numChannels ];
		for ( int i = 0; i < numChannels; i++ ) {
			shorts[ i ] = buffer[ i ];
		}
		
		return shorts;
	}

}