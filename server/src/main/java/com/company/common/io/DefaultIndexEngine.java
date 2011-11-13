package com.company.common.io;
/*
 * created on: Aug 12, 2010
 * Copyright(c) 2002-2008 Thetus Corporation.  All Rights Reserved.
 *                        www.thetus.com
 *
 * Use of copyright notice does not imply publication or disclosure.
 * THIS SOFTWARE CONTAINS CONFIDENTIAL AND PROPRIETARY INFORMATION 
CONSTITUTING VALUABLE TRADE SECRETS
 *  OF THETUS CORPORATION, AND MAY NOT BE:
 *  (a) DISCLOSED TO THIRD PARTIES;
 *  (b) COPIED IN ANY FORM;
 *  (c) USED FOR ANY PURPOSE EXCEPT AS SPECIFICALLY PERMITTED IN 
WRITING BY THETUS CORPORATION.
 */

import com.company.common.api.IIndexEngine;
import com.company.common.pojos.IndexObject;
import com.company.common.pojos.Sample;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.SampleBuffer;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import java.io.*;


/**
 * @author cmason
 */
public class DefaultIndexEngine implements IIndexEngine {
    private static final Logger LOGGER = Logger.getLogger( IIndexEngine.class );

    public synchronized IndexObject makeIndexObject(InputStream input, String filename ) {

		long start = System.currentTimeMillis();

		try {
			final Bitstream bitstream   = new Bitstream( input );
            final Decoder decoder       = new Decoder();
            final IndexObject index     = new IndexObject( filename );

            float timeStamp         = 0;
            int numChannels         = 0;
            long pos                = 0;
            Header h                = null;
            SampleBuffer output     = null;
            short[] samples         = null;

			while((h = bitstream.readFrame()) != null) {
				pos             = bitstream.pos();
				h               = bitstream.readFrame();
				output          = (SampleBuffer) decoder.decodeFrame(h, bitstream);


				samples         = getSamples( output.getBuffer(), output.getChannelCount() );

//                index.addSample( new Sample( timeStamp, pos, samples ), false );
                index.addSample( new Sample( timeStamp, pos, new short[] { samples[0] } ), false );

				timeStamp +=  ( (float) output.getBufferLength() ) / output.getChannelCount() / output.getSampleFrequency();
				bitstream.closeFrame();
			}

//            index.setChannels( output.getChannelCount() );
            index.setChannels( 1 );
            index.updateBounds();

			final long end = System.currentTimeMillis();
            
			LOGGER.info( filename + " indexed in " + (end-start)/1000. + " secs.");
			
			return index;
		} catch (Exception e) {
            LOGGER.error( e );

            e.printStackTrace();
			throw new RuntimeException( e );
		} finally {
		}
	}

	private static int[] getLargestGap(short[] maxes, short[] mins) {
		int[] ret = new int[ maxes.length ];
		
		for ( int i = 0; i < maxes.length; i++ ) {
			ret[ i ] = maxes[ i ] + ( mins[ i ] * -1 );
		}
		
		return ret;
	}

	private static String getSampleStr(int[] samples) {
		StringBuffer str = new StringBuffer();
		for ( int i = 0; i < samples.length; i++) {
			str.append( samples[ i ] ).append("\t" );
		}
		
		return str.toString();
	}


	private static String getSampleStr(short[] samples) {
		StringBuffer str = new StringBuffer();
		for ( int i = 0; i < samples.length; i++) {
			str.append( samples[ i ] ).append("\t" );
		}
		
		return str.toString();
	}

	private static short[] getSamples(short[] buffer, int numChannels) {
		short[] shorts = new short[ numChannels ];
		for ( int i = 0; i < numChannels; i++ ) {
			shorts[ i ] = buffer[ i ];
		}
		
		return shorts;
	}
}