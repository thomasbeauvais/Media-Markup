package org.branch.annotation.audio.gui;

import org.branch.annotation.audio.api.IIndexEngine;
import org.branch.annotation.audio.services.AudioAnnotationService;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;

public class MainFrame extends JFrame {
    private JPanel panel_main;
    private JPanel panel_buttons;

    private ApplicationContext applicationContext;

    private static final Logger LOGGER = Logger.getLogger( MainFrame.class );
    private WaveFormPanel currentWaveFormPanel;

    public static void main(String[] args) throws Exception {
        new MainFrame().buildAndShow();
	}

    private MainFrame() {
        applicationContext = new ClassPathXmlApplicationContext( "applicationContext.xml" );
    }

    private void buildAndShow() {
        final JFrame frame = new JFrame( "AudioAnnotation" );
		frame.setLayout( new BorderLayout() );
        frame.addComponentListener( new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                System.out.println( componentEvent.getComponent().getClass().getName() + " --- Resized ");

                if ( currentWaveFormPanel != null ) {
                    currentWaveFormPanel.redraw();
                }
            }
        } );

        panel_main = new JPanel( new GridLayout() );
        panel_main.setMinimumSize( new Dimension( 0, 500 ));

        panel_buttons = new JPanel( );
//        panel_buttons.setLayout(new FlowLayout(FlowLayout.LEADING));
        panel_buttons.setLayout( new GridLayout(1, 1) );

        panel_buttons.add( new JButton( new AbstractAction("Create Index..") {
            public void actionPerformed( ActionEvent e ) {
                final JFileChooser fileChooser = new JFileChooser(  );
                fileChooser.setMultiSelectionEnabled( false );
                fileChooser.showOpenDialog( MainFrame.this );

                final File inputFile = fileChooser.getSelectedFile();

                final IIndexEngine indexEngine = applicationContext.getBean( IIndexEngine.class );
            }
        } ) );

        panel_buttons.add( new JButton( new AbstractAction("Open Index..") {
            public void actionPerformed( ActionEvent e ) {
//                final JFileChooser fileChooser = new JFileChooser( new File( "/Users/tbeauvais/IdeaProjects/Media-Markup/server/data/filePersistenceDir" ) );
//                fileChooser.setMultiSelectionEnabled( false );
//                fileChooser.showOpenDialog( MainFrame.this );
//
//                final File indexFile = fileChooser.getSelectedFile();
//                final File indexFile = new File( "/Users/tbeauvais/IdeaProjects/Media-Markup/server/data/filePersistenceDir/small.index" );
                final File indexFile = new File( "/Users/tbeauvais/IdeaProjects/Media-Markup/server/data/filePersistenceDir/large.index" );

                LOGGER.info( "*** Attempting to load index file for: " + indexFile.getAbsolutePath() );

                final String indexName                  = indexFile.getName().substring( 0, indexFile.getName().length() - 6 );

                final AudioAnnotationService annotationService = applicationContext.getBean( AudioAnnotationService.class );

                LOGGER.info( "*** Loaded indexObject: " + indexName );

                panel_main.removeAll();

                currentWaveFormPanel = new WaveFormPanel( annotationService, indexName );

                panel_main.add( currentWaveFormPanel, 0, 0 );

                panel_main.revalidate();

                frame.setIgnoreRepaint( true );
                panel_main.setIgnoreRepaint( true );
                currentWaveFormPanel.setIgnoreRepaint( true );
            }
        } ) );

        frame.add( panel_main, BorderLayout.CENTER );
        frame.add( panel_buttons, BorderLayout.SOUTH );

        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setBounds(100, 100, 400, 400);
        frame.setVisible( true );
    }
}
