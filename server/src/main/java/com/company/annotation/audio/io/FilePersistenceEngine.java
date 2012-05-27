package com.company.annotation.audio.io;

import com.company.annotation.audio.api.IFilePersistenceConnector;
import com.company.annotation.audio.api.IPersistenceEngine;
import com.company.annotation.audio.io.exceptions.PersistenceException;
import com.company.annotation.audio.pojos.IndexSummary;
import com.company.annotation.audio.pojos.SampleList;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.io.*;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: tbeauvais
 * Date: 11/6/11
 * Time: 9:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class FilePersistenceEngine implements IPersistenceEngine {
    private static final Logger LOGGER = Logger.getLogger( IPersistenceEngine.class );

    private static final String EXTENSION_INDEX_SUMMARY = "index";
    private static final String EXTENSION_SAMPLE_LIST   = "samples";

    private Map<Class, String> extensionMap = new HashMap<Class, String>();

    private IFilePersistenceConnector filePersistenceConnector;

    private String basePath;

    private File baseDirectory;

    @Autowired
    public void setFilePersistenceConnector( IFilePersistenceConnector filePersistenceConnector ) {
        this.filePersistenceConnector = filePersistenceConnector;
    }

    public FilePersistenceEngine( @Nullable String basePath ) {
        this.basePath                   = basePath;
        this.baseDirectory              = new File( basePath );

        if( !baseDirectory.exists() ) {
            throw new PersistenceException( "Couldn't create " + FilePersistenceEngine.class.getSimpleName(),
                    new FileNotFoundException( "Couldn't find base directory: " + basePath ) );
        }

        extensionMap.put( SampleList.class, EXTENSION_SAMPLE_LIST );
        extensionMap.put( IndexSummary.class, EXTENSION_INDEX_SUMMARY );

    }

    public void save(String id, Object obj) {
        writeObjectToFile(id, obj);
    }

    public <T extends Object> T load(String id, Class<T> objectClazz) {
        final File inputFile = getFileFromId( id, objectClazz );

        if ( !inputFile.exists() ) {
            throw new PersistenceException( "Couldn't locate file for id=" + id + " @ " + inputFile.getAbsolutePath() );
        }

        return readObjectFromFile( id, inputFile, objectClazz );
    }

    public <T extends Object> T[] loadAll( final Class<T> objectClazz ) {
        final File baseDirectory    = new File( basePath );
        final String ext            = extensionMap.get( objectClazz );
        final File[] allFiles       = baseDirectory.listFiles( new FilenameFilter() {
            public boolean accept( File dir, String name ) {
                return name.endsWith( ext );
            }
        } );

        final T[]objects            = (T[]) Array.newInstance( objectClazz, allFiles.length );
        for( int i = 0, allFilesLength = allFiles.length; i < allFilesLength; i++ ) {
            final File file = allFiles[ i ];
            final String filename = file.getName();
            final String id = filename.substring( 0, filename.length() - ext.length() );
            objects[ i ] = readObjectFromFile( id, file, objectClazz );
        }

        return objects;
    }

    private <T extends Object> T readObjectFromFile( String id, File inputFile, Class<T> objectClazz ) {
        InputStream inputStream     = null;
        try {
            inputStream             = new FileInputStream( inputFile );

            return filePersistenceConnector.readObject( id, inputStream, objectClazz );
        } catch (Throwable e) {
            throw new PersistenceException( "Error opening file: " + inputFile.getAbsolutePath(), e );
        } finally {
            if ( inputStream != null ) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
        }
    }

    private void writeObjectToFile( String id, Object obj ) {
        if ( obj == null ) {
            return;
        }

        final File outputFile = getFileFromId( id, obj.getClass() );

        if ( outputFile.exists() ) {
            LOGGER.warn( "File already exists.  Attempting to overwrite file: " + outputFile.getAbsolutePath() );
        } else {
           LOGGER.info( "File doesn't exist.  Creating output file: " + outputFile.getAbsolutePath() );
        }

        try {
            filePersistenceConnector.writeObject( id, obj, outputFile );
        } catch( IOException e ) {
            throw new PersistenceException( "Couldn't save IndexFile: " + outputFile.getAbsolutePath(), e );
        }

//        Writer outputWriter         = null;
//        try {
//            outputWriter            = new FileWriter( outputFile, false );
//            filePersistenceConnector.writeObject( obj, outputWriter );
//
//        } catch (IOException e) {
//            throw new PersistenceException( "Couldn't save IndexFile: " + outputFile.getAbsolutePath(), e );
//        } finally {
//            if ( outputWriter != null ) {
//                try {
//                    outputWriter.close();
//                } catch (IOException e) {
//                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//                }
//            }
//        }
    }

    private File getFileFromId( String id, Class objectClazz ) {
        final String ext = extensionMap.get( objectClazz );
        if ( ext == null ) {
            throw new PersistenceException( "There is no extension map for the provided class: " + objectClazz.getName() );
        }

        return new File( basePath, id + "." + ext );

    }
}
