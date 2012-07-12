package com.company.annotation.audio;

import com.company.annotation.audio.api.IIndexEngine;
import com.company.annotation.audio.api.IPersistenceEngine;
import com.company.annotation.audio.pojos.Comment;
import com.company.annotation.audio.pojos.IndexWithSamples;
import com.company.annotation.audio.pojos.SampleList;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.*;
import java.util.List;
import java.util.Vector;

/**
 */
public class IndexDriver {

    private static Logger LOGGER = Logger.getLogger( IndexDriver.class );

    private static ApplicationContext applicationContext    = new ClassPathXmlApplicationContext( "applicationContext.xml" );;

    public static final String PATH_AUDIO_FILES             = "/home/tbeauvais/Development/personal/Media-Markup/server/data/songs";

    public static void main(String[] asdf) {
        final IIndexEngine indexEngine  = applicationContext.getBean( IIndexEngine.class );

        final File audioDirectory       = new File( PATH_AUDIO_FILES );
        final File[] audioFiles         = audioDirectory.listFiles( new FilenameFilter() {
            public boolean accept( File dir, String name ) {
                return name.endsWith( ".mp3" );
            }
        } );

        for ( File audioFile : audioFiles ) {
            final String uid          = audioFile.getName().substring( 0, audioFile.getName().length() - 4 );

            LOGGER.info( "*** Attempting to create index file for: " + audioFile.getAbsolutePath() );

            InputStream inputStream         = null;

            try {
                inputStream                     = new FileInputStream( audioFile );

                final SampleList sampleList     = indexEngine.createIndexForAudioStream( inputStream, uid );

                final IndexWithSamples indexSummary = sampleList.getIndexSummary();

                final List<Comment> comments = new Vector<Comment>();
                comments.add( new Comment( "sample text one", 100, 1000 ) );
                comments.add( new Comment( "sample text two", 100, 1000 ) );

                indexSummary.setComments(comments);

                LOGGER.info( "*** Created SampleList: " + uid );

                LOGGER.info( "*** Attempting to save SampleList: " + uid );

                applicationContext.getBean(IPersistenceEngine.class).save( indexSummary );

                LOGGER.info( "*** Creation of index file complete for: " + uid );
            } catch( FileNotFoundException e ) {
                LOGGER.warn( "*** Error creating index file: " + audioFile.getAbsolutePath(), e );
            } finally {
                try {
                    inputStream.close();
                } catch( Exception e ) {
                    e.printStackTrace();
                }
            }
        }
    }
}