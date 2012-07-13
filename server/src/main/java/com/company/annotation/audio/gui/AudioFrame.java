//package com.company.annotation.audio.gui;
//import java.io.*;
//import java.util.ArrayList;
//import java.util.Scanner;
//
//import javax.swing.JFrame;
//
//import com.company.annotation.audio.io.FilePersistenceEngine;
//import com.company.annotation.audio.io.json.JSONFilePersistenceConnector;
//import com.company.annotation.audio.pojos.SampleList;
//import com.company.annotation.audio.pojos.Sample;
//
//
//public class AudioFrame {
//	public void go() throws Exception {
////		FileInputStream fis = new FileInputStream("/Users/thomasbeauvais/eric.index");
//
////		parse( fis );
////		show(samples, maxes);
//
////		FileInputStream serial = new FileInputStream("/Users/thomasbeauvais/eric.ser");
//
//        final String strIndexId = "test-file-small";
//        //final String strFilePath = "test-file-large";
//
//		SampleList indexFile = new FilePersistenceEngine( ".", new JSONFilePersistenceConnector() ).load( strIndexId, SampleList.class );
//
////		show( indexFile );
//	}
//
////	private int[] getDurations(SampleList indexFile) {
////		int[] durs = new int[ indexFile.getMaxes().length ];
////		for ( int i = 0; i < durs.length; i++ ) {
////			durs[ i ] = indexFile.getMaxes()[ i ] + (indexFile. getMins()[ i ] * -1);
////		}
////
////		return durs;
////	}
//
//	private float totalTime;
//	private int numChannels;
//	private int[] maxes;
//	private ArrayList<Sample> samples;
//
//	private void parse(FileInputStream fis) {
//		long startX = System.currentTimeMillis();
//
//		Scanner scanner = new Scanner( fis );
//
//		totalTime = scanner.nextFloat();
//		numChannels = scanner.nextInt();
//		maxes = new int[ numChannels ];
//		for ( int i = 0; i < numChannels; i++ ) {
//			maxes[ i ] = scanner.nextInt();
//		}
//
//		samples = new ArrayList<Sample>();
//		while ( scanner.hasNextLine() ) {
//			String str = scanner.nextLine();
//			try {
//				Scanner in = new Scanner( str );
//				float timeStamp = in.nextFloat();
//				long byteMarker = in.nextLong();
//
//				short[] samps = new short[ numChannels ];
//				for ( int i = 0; i < numChannels; i++ ) {
//					samps[ i ] = in.nextShort();
//				}
//
//				samples.add( new Sample( timeStamp, byteMarker, samps[0] ) );
//			} catch ( Exception e ) {
//			}
//		}
//
//		long end = System.currentTimeMillis();
//
//		System.err.println( "Parsed in " + (end-startX)/1000. + " secs.");
//	}
//
//	private static void show(Sample[] sampleData, int[] maxes) {
//		JFrame frame = new JFrame(  );
////		frame.setLayout( new GridLayout( 2, 1 ) );
//
//
//		WaveFormPanel panelL = new WaveFormPanel();
////		WaveFormPanel panelR = new WaveFormPanel();
//		frame.add( panelL );
////		frame.add( panelR );
//		frame.setBounds(100, 100, 400, 400);
//		frame.setVisible( true );
//
//		panelL.setSamples(sampleData, 0, max);
//	}
//
//}
