package com.company.annotation.audio;

import com.company.annotation.audio.api.IIndexEngine;
import com.company.annotation.audio.api.IFilePersistenceConnector;
import com.company.annotation.audio.io.FilePersistenceEngine;
import com.company.annotation.audio.io.binary.BinaryPersistenceConnector;
import com.company.annotation.audio.io.json.JSONFilePersistenceConnector;
import com.company.annotation.audio.pojos.Annotation;
import com.company.annotation.audio.pojos.IndexSummary;
import com.company.annotation.audio.pojos.SampleList;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Vector;

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

//    public static final String PATH_FILE_PERSISTENCE_BASE   = "/Users/tbeauvais/IdeaProjects/Media-Markup/server/data/filePersistenceDir2";
//    public static final String PATH_AUDIO_FILES             = "/Users/tbeauvais/IdeaProjects/Media-Markup/server/data/songs";

    public static final String PATH_FILE_PERSISTENCE_BASE   = "/home/tbeauvais/Development/personal/Media-Markup/server/data/filePersistenceDir3";
    public static final String PATH_AUDIO_FILES             = "/home/tbeauvais/Development/personal/Media-Markup/server/data/songs";

    @BeforeClass
    public static void beforeClass() {
        applicationContext = new ClassPathXmlApplicationContext( "applicationContext.xml" );

        final IFilePersistenceConnector connector = new JSONFilePersistenceConnector();
//        final IFilePersistenceConnector connector = new BinaryPersistenceConnector();

        filePersistenceEngine    = new FilePersistenceEngine( PATH_FILE_PERSISTENCE_BASE );
        filePersistenceEngine.setFilePersistenceConnector( connector );
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
            final String indexName          = audioFile.getName().substring( 0, audioFile.getName().length() - 4 );

            LOGGER.info( "*** Attempting to create index file for: " + audioFile.getAbsolutePath() );

            InputStream inputStream         = null;

            try {
                inputStream                 = new FileInputStream( audioFile );


                final SampleList sampleList = indexEngine.createIndexForAudioStream( inputStream, indexName );

                final List<Annotation> annotations = new Vector<Annotation>();
                annotations.add( new Annotation( sampleList.getId(), "sample text one", new Date() ) );
                annotations.add( new Annotation( sampleList.getId(), "sample text two", new Date() ) );

                sampleList.getIndexSummary().setAnnotations(annotations);

                LOGGER.info( "*** Created SampleList: " + indexName );

                LOGGER.info( "*** Attempting to save SampleList: " + indexName );

                filePersistenceEngine.save( indexName, sampleList );
                filePersistenceEngine.save( indexName, sampleList.getIndexSummary() );

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

    @Test
    public void readAllIndexFilesForDir() {
        final File audioDirectory                   = new File( PATH_FILE_PERSISTENCE_BASE );
        final File[] indexFiles                     = audioDirectory.listFiles( new FilenameFilter() {
            public boolean accept( File dir, String name ) {
                return name.endsWith( ".index" );
            }
        } );

        for ( File indexFile : indexFiles ) {
            final String indexName                  = indexFile.getName().substring( 0, indexFile.getName().length() - 6 );

            LOGGER.info( "*** Attempting to load index file for: " + indexFile.getAbsolutePath() );

            final IndexSummary indexObject          = filePersistenceEngine.load( indexName, IndexSummary.class );

            LOGGER.info( "*** Loaded indexObject: " + indexName );
        }
    }
}
