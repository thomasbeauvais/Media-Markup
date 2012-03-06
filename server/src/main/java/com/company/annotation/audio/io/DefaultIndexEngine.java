package com.company.annotation.audio.io;

import com.company.annotation.audio.api.IIndexEngine;
import com.company.annotation.audio.pojos.IndexObject;
import com.company.annotation.audio.pojos.Sample;
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

    public synchronized IndexObject makeIndexObject( @NotNull InputStream input, @NotNull String indexName ) {

		long start = System.currentTimeMillis();

		try {
			final Bitstream bitstream   = new Bitstream( input );
            final Decoder decoder       = new Decoder();
            final IndexObject index     = new IndexObject();

            float timeStamp         = 0;
            long pos                = 0;
            Header h                = null;
            SampleBuffer output     = null;
            short[] samples         = null;

            if ( h == null ) {
                throw new NullPointerException( "Something is wrong with the first header!  It's null!" );
            }

            while((h = bitstream.readFrame()) != null) {
				pos             = bitstream.pos();
				output          = (SampleBuffer) decoder.decodeFrame(h, bitstream);

                samples         = getSamples( output.getBuffer(), output.getChannelCount() );

                index.addSample( new Sample( timeStamp, pos, samples[0] ), false );

				timeStamp +=  ( (float) output.getBufferLength() ) / output.getChannelCount() / output.getSampleFrequency();
				bitstream.closeFrame();
			}

            index.setName( indexName );
            index.setChannels( output.getChannelCount() );
            index.updateBounds();

			final long end = System.currentTimeMillis();

			LOGGER.info( "Audio stream indexed in " + (end-start)/1000. + " seconds.");
			
			return index;
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