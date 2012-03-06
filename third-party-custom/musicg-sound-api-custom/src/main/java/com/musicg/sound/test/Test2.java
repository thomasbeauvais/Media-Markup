package com.musicg.sound.test;

import java.io.*;

import com.musicg.sound.Wave;
import com.musicg.sound.WaveInputStream;
import com.musicg.sound.timedomain.AmplitudeTimeDomainRepresentation;
import javazoom.jl.converter.Converter;
import javazoom.jl.decoder.JavaLayerException;

public class Test2 {



    public static void main( String[] args ) throws IOException {

        String name = "test";

//        String filenameWav = name + ".wav";
        String filenameMp3 = name + ".mp3";

        String strProjectBase   = "musicg-sound-api-custom/";
        String strProjectOutput = strProjectBase + "target/" + name + "/";
        String strProjectData   = strProjectBase + "data/";

        new File( strProjectOutput ).mkdirs();

        File fileMp3 = new File( strProjectData, filenameMp3 );
        File fileWav = new File( "/tmp/test4743065779674894099.wav" );
//        File fileWav = File.createTempFile( name, ".wav");

        System.out.println( "*** Using input file: \t" + fileMp3.getAbsolutePath() );

//        convertMp3( fileMp3, fileWav ) ;

        System.out.println( "*** Using input file: \t" + fileWav.getAbsolutePath() );

        // get the wave instance by input stream
//        InputStream fis = new FileInputStream( fileWav );
        InputStream fis = new FileInputStream( fileWav );

        // create a wave inputStream by inputStream
        WaveInputStream wis = new WaveInputStream( fis );

        // create a wave object by wave inputStream
        Wave wave = new Wave( wis );

        // print the wave header and info
        System.out.println( wave );

        // TimeDomainRepresentations
        AmplitudeTimeDomainRepresentation ampRp = new AmplitudeTimeDomainRepresentation( wave );

        double[] nAmplitudes    = ampRp.getNormalizedAmplitudes();

        ampRp.setTimeStep( 0.9f );

        int width = (int) (nAmplitudes.length / ampRp.getWave().getSampleRate() / ampRp.getTimeStep());

        System.out.println( "Done!" );
    }

    private static void convertMp3( File fileMp3, File fileOuputWav ) throws IOException {
                InputStream fis = null;
        try {
            long start = System.currentTimeMillis();

            System.out.println( "*** Converting file:   \t" + fileMp3.getAbsolutePath() );
            System.out.println( "     to WAV file : \t" + fileOuputWav.getAbsolutePath() );

            fis = new FileInputStream( fileMp3 );

            new Converter().convert( fis , fileOuputWav.getAbsolutePath(), null, null );

            System.out.println( "*** Conversion took " + (System.currentTimeMillis() - start ) + "ms");
        } catch( JavaLayerException e ) {
            throw new IOException( e );
        }
    }
}