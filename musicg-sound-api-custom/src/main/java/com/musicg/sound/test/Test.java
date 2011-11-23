package com.musicg.sound.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.musicg.sound.Wave;
import com.musicg.sound.WaveInputStream;
import com.musicg.sound.graphic.GraphicRender;
import com.musicg.sound.timedomain.AmplitudeTimeDomainRepresentation;
import com.musicg.sound.timedomain.FrequencyTimeDomainRepresentation;

public class Test {

    public static void main( String[] args ) {

        String name = "test";

        String filename = name + ".wav";
//        String filename = name + ".mp3";

        String strProjectBase   = "musicg-sound-api-custom/";
        String strProjectOutput = strProjectBase + "target/" + name + "/";
        String strProjectData   = strProjectBase + "data/";

        new File( strProjectOutput ).mkdirs();

        File file = new File( strProjectData, filename );

        System.out.println( "Using inputfile: \t" + file.getAbsolutePath() );

        // get the wave instance by input stream
        InputStream fis = null;
        try {
            fis = new FileInputStream( file );
        } catch( FileNotFoundException e ) {
            e.printStackTrace();
        }

        // create a wave inputStream by inputStream
        WaveInputStream wis = new WaveInputStream( fis );

        // create a wave object by wave inputStream
        Wave wave = new Wave( wis );

        // print the wave header and info
        System.out.println( wave );

        // TimeDomainRepresentations
        AmplitudeTimeDomainRepresentation ampRp = new AmplitudeTimeDomainRepresentation( wave );
        FrequencyTimeDomainRepresentation freqRp = new FrequencyTimeDomainRepresentation( wave );

        // get the amplitude
        double[] amplitudes = ampRp.getNormalizedAmplitudes();

        // get the spectrogram
        double[][] spectrogram = freqRp.getSpectrogram();

        // Graphic render
        GraphicRender render = new GraphicRender();
        render.renderWaveform( ampRp, strProjectOutput + "waveform.jpg" );
        render.renderSpectrogram( freqRp, strProjectOutput + "spectrogram.jpg" );

//        // change the amplitude representation
//        ampRp.setTimeStep( 0.1F );
//        render.renderWaveform( ampRp, strProjectOutput + "waveform2.jpg" );
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
}