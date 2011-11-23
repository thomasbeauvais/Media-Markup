package com.musicg.sound.test;

import java.io.*;

import com.musicg.sound.Wave;
import com.musicg.sound.WaveInputStream;
import com.musicg.sound.graphic.GraphicRender;
import com.musicg.sound.timedomain.AmplitudeTimeDomainRepresentation;
import com.musicg.sound.timedomain.FrequencyTimeDomainRepresentation;
import javazoom.jl.converter.Converter;
import javazoom.jl.decoder.JavaLayerException;

public class Test {



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
//        FrequencyTimeDomainRepresentation freqRp = new FrequencyTimeDomainRepresentation( wave );

        // get the amplitude
//        double[] amplitudes = ampRp.getNormalizedAmplitudes();

        // get the spectrogram
//        double[][] spectrogram = freqRp.getSpectrogram();

        // Graphic render
        GraphicRender render = new GraphicRender();
//        render.renderWaveform( ampRp, strProjectOutput + "waveform.jpg" );
//        render.renderSpectrogram( freqRp, strProjectOutput + "spectrogram.jpg" );

//        // change the amplitude representation
        ampRp.setTimeStep( 0.9F );
        render.renderWaveform( ampRp, strProjectOutput + "waveform2.jpg" );
//
//        // change the spectrogram representation
//        freqRp.setFftSampleSize( 512 );
//        freqRp.setOverlapFactor( 2 );
//        render.renderSpectrogram( freqRp, strProjectOutput + "spectrogram2.jpg" );
//
//        // trim the wav
//        wave.leftTrim( 1 );
//        wave.rightTrim( 0.5F );
//
//        // save the trimmed wav
//        wave.saveAs( strProjectOutput + "out.wav" );
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