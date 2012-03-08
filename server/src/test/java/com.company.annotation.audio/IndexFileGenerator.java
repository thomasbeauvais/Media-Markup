package com.company.annotation.audio;

import com.company.annotation.audio.api.IIndexEngine;
import com.company.annotation.audio.api.IPersistenceConnector;
import com.company.annotation.audio.io.FilePersistenceEngine;
import com.company.annotation.audio.io.json.JSONPersistenceConnector;
import com.company.annotation.audio.pojos.IndexObject;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: tbeauvais
 * Date: 3/7/12
 * Time: 2:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class IndexFileGenerator {

    private static Logger LOGGER = Logger.getLogger( IndexFileGenerator.class );

    private static ApplicationContext applicationContext;

    private static FilePersistenceEngine filePersistenceEngine;

    public static final String PATH_FILE_PERSISTENCE_BASE   = "/home/tbeauvais/Personal/Code/github/Media-Markup/server/data/filePersistenceDir";
    public static final String PATH_AUDIO_FILES             = "/home/tbeauvais/Personal/Code/github/Media-Markup/server/data/songs";

    @BeforeClass
    public static void beforeClass() {
        applicationContext = new ClassPathXmlApplicationContext( "applicationContext.xml" );

        final IPersistenceConnector connector = new JSONPersistenceConnector();

        filePersistenceEngine    = new FilePersistenceEngine( PATH_FILE_PERSISTENCE_BASE, connector );
    }

    @Test
    public void generateAllIndexFilesForDir() {
        final IIndexEngine indexEngine  = applicationContext.getBean( IIndexEngine.class );

        final File audioDirectory       = new File( PATH_AUDIO_FILES );
        final File[] audioFiles         = audioDirectory.listFiles( new FilenameFilter() {
            public boolean accept( File dir, String name ) {
                return name.endsWith( ".mp3" );
            }
        } );

        for ( File audioFile : audioFiles ) {
            final String indexName          = audioFile.getName();

            LOGGER.info( "*** Attempting to load and create index file for: " + audioFile.getAbsolutePath() );

            InputStream inputStream         = null;

            try {
                inputStream                     = new FileInputStream( audioFile );

                final IndexObject indexObject   = indexEngine.makeIndexObject( inputStream, indexName );

                LOGGER.info( "*** Created IndexObject: " + indexName );

                LOGGER.info( "*** Attempting to save IndexObject: " + indexName );

                filePersistenceEngine.save( indexName, indexObject );

                LOGGER.info( "*** Creation of index file complete for: " + indexName );
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
