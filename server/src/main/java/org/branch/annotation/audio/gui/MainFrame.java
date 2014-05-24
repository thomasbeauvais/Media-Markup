package org.branch.annotation.audio.gui;

import org.branch.annotation.audio.api.IIndexEngine;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class MainFrame extends JFrame {
    private JPanel panel_main;
    private JPanel panel_buttons;

    private ApplicationContext applicationContext;

    public static void main(String[] args) throws Exception {
        new MainFrame().buildAndShow();
	}

    private MainFrame() {
        applicationContext = new ClassPathXmlApplicationContext( "applicationContext.xml" );
    }

    private void buildAndShow() {
        JFrame frame = new JFrame(  );
		frame.setLayout( new GridLayout( 1, 2 ) );

        panel_main = new JPanel();

        panel_buttons = new JPanel( );
        panel_buttons.setLayout( new GridLayout( 10, 1 ) );

        panel_buttons.add( new JButton( new AbstractAction("Create Index..") {
            public void actionPerformed( ActionEvent e ) {
                final JFileChooser fileChooser = new JFileChooser(  );
                fileChooser.setMultiSelectionEnabled( false );
                fileChooser.showOpenDialog( MainFrame.this );

                final File inputFile = fileChooser.getSelectedFile();

                final IIndexEngine indexEngine = applicationContext.getBean( IIndexEngine.class );
            }
        } ) );

        frame.add( panel_main );

        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setBounds(100, 100, 400, 400);
        frame.setVisible( true );
    }

//    private void openIndexFile() {
//        JFileChooser
//
//		WaveFormPanel panelL = new WaveFormPanel();
//		frame.add( panelL );
//
//		panelL.setSamples(sampleData, 0, maxes[0]);
//	}
}
