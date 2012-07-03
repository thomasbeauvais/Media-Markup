package com.company.annotation.audio.gui;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import javax.swing.border.LineBorder;

import com.company.annotation.audio.pojos.IndexSummary;
import com.company.annotation.audio.pojos.VisualData;
import com.company.annotation.audio.pojos.VisualParameters;
import com.company.annotation.audio.pojos.VisualRegion;
import com.company.annotation.audio.services.AudioAnnotationService;
import com.company.annotation.audio.util.StringUtils;

public class WaveFormPanel extends JLayeredPane {
    private AudioAnnotationService annotationService;

    private String indexName;
    private JPanel panel_wave;
    private JPanel panel_time;
    private JPanel panel_selection;
    private JPanel panel_main;

//	private SampleList sampleList;
//
//    public WaveFormPanel(SampleList sampleList) {
//        this.sampleList = sampleList;
//    }

    public WaveFormPanel(AudioAnnotationService annotationService, String indexName) {
        this.annotationService  = annotationService;
        this.indexName          = indexName;

        this.panel_main         = new JPanel();
        this.panel_time         = new JPanel();
        this.panel_wave         = new JPanel();
        this.panel_selection    = new JPanel();

        this.panel_selection.setOpaque( false );
        this.panel_main.setLayout( new BorderLayout() );

        this.panel_time.add( new JLabel( "Time" ) );
        this.panel_time.setBorder( new LineBorder( Color.BLACK, 1 ) );

        this.panel_main.add(panel_time, BorderLayout.NORTH);
        this.panel_main.add(panel_wave, BorderLayout.CENTER);

        this.add( panel_main );
//        this.add( panel_selection, 1, 0 );

        panel_selection.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                System.out.println("clocuasdf");
            }
        });
    }

    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);

//        drawWaveform(g);
    }
	
//	public void setSamples(SampleList sampleData) {
//		this.sampleList = sampleData;
//
//		this.repaint();
//	}
	
//	public void drawWaveform(Graphics context) {
//
//        int height          = Math.max( 400, this.getHeight() );
//        int width           = this.getWidth();
//
//        int lineHeight = height / 2;
//        context.setColor(Color.BLACK );
//        context.drawLine(0, lineHeight, width, lineHeight);
//
//        if ( sampleList != null && sampleList.getSamples() != null ) {
//            int startX              = 0;
//
//            Sample[] dataArray      = sampleList.getSamples();
//
//            int step                = dataArray.length / width;
//            double scale            = sampleList.getMax() / height;
//
//            int center              = (int) Math.floor( height / 2 );
//
//            context.setColor(Color.black);
//
//            for ( int i = 0; i < dataArray.length; i++, startX++ ) {
//                int max = 0;
//                int min = 0;
//
//                for ( int s = 0; s < step && i < dataArray.length; s++, i++ ) {
//                    max             = Math.max( max, dataArray[i].getValue() );
//                    min             = Math.min( min, dataArray[i].getValue() );
//                }
//
//                int value           = (int) ((max + (min * -1) ) / scale);
//                int y               = value / 4;
//
//                context.drawLine( startX, center - y, startX, center + y );
//            }
//        }
//	}

    public void redraw() {
        drawTime();

        drawWaveform();
        drawRegions();
    }

    private void drawTime() {
        final IndexSummary indexSummary = annotationService.loadIndexSummary( indexName );
        final Graphics graphics = panel_time.getGraphics();

        graphics.setColor( new Color( 0, 0, 255, 50 ) );

        final double totalTime  = indexSummary.getTime();

        final int height        = panel_time.getHeight();
        final int width         = panel_time.getWidth();

        final int tickHeight    = panel_time.getHeight() / 4;
        final int numSteps      = 10;
        final int stepPix       = width / numSteps;
        final double stepTime   = totalTime / numSteps;
        for ( int i = 0; i < numSteps; i++ ) {
            graphics.drawLine( stepPix * i, 0, stepPix * i, tickHeight );

            final int s   = (int) (stepTime * i);
            if ( s > 60 * 60 ) {
                graphics.drawString( String.format("%d:%02d:%02d", s/3600, s/60%60, s%60), stepPix * i, height / 2);
            } else {
                graphics.drawString( String.format("%02d:%02d", s/60%60, s%60) , stepPix * i, height / 2);
            }
        }
    }

    private void drawRegions() {
        final Graphics graphics = panel_main.getGraphics();

        final int height        = panel_main.getHeight();

        final VisualRegion[] visualRegions = annotationService.loadVisualRegions( indexName, new VisualParameters() );
        for ( VisualRegion visualRegion : visualRegions ) {
            final int width = visualRegion.getStop() - visualRegion.getStart();

            graphics.setColor( new Color( 0, 0, 255, 50 ) );

            graphics.fillRect( visualRegion.getStart(), 0, width, height);
        }
    }

    private void drawWaveform() {
        final Graphics context  = panel_wave.getGraphics();

        int height              = Math.max( 400, panel_wave.getHeight() );
        int width               = panel_wave.getWidth();

        // Draw the center line
        int center              = height / 2;
        context.setColor(Color.BLACK );
        context.drawLine(0, center, width, center);

        // Call the server to retrieve the scaled VisualData
        final VisualParameters visualParameters = new VisualParameters();
        visualParameters.setHeight( height );
        visualParameters.setWidth(width);

        final VisualData visualData = annotationService.loadVisualData( indexName, visualParameters );
        final int[] visualSamples   = visualData != null ? visualData.getVisualSamples() : null;

        if ( visualData != null && visualSamples != null ) {
            context.setColor(Color.black);

            for ( int i = 0; i < visualSamples.length; i++ ) {
                final int half = visualSamples[ i ];

                context.drawLine( i, center - half, i, center + half );
            }
        }
	}
}
