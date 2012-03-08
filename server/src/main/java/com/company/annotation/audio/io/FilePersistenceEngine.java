package com.company.annotation.audio.io;

import com.company.annotation.audio.api.IPersistenceConnector;
import com.company.annotation.audio.api.IPersistenceEngine;
import com.company.annotation.audio.io.exceptions.PersistenceException;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.lang.reflect.Array;

/**
 * Created by IntelliJ IDEA.
 * User: tbeauvais
 * Date: 11/6/11
 * Time: 9:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class FilePersistenceEngine implements IPersistenceEngine {
    private static final Logger LOGGER = Logger.getLogger( IPersistenceEngine.class );

    private String basePath;

    private File baseDirectory;

    private IPersistenceConnector fileInputOutput;
    private static final String EXTENSION = ".index";

    public FilePersistenceEngine( @Nullable String basePath, @NotNull IPersistenceConnector fileInputOutputConnector ) {
        this.basePath = basePath;

        this.baseDirectory = new File( basePath );

        if( !baseDirectory.exists() ) {
            throw new PersistenceException( "Couldn't create " + FilePersistenceEngine.class.getSimpleName(),
                    new FileNotFoundException( "Couldn't find base directory: " + basePath ) );
        }

        this.fileInputOutput = fileInputOutputConnector;
    }

    public void save(String id, Object obj) {
        writeObjectToFile(id, obj);
    }

    public <T extends Object> T load(String id, Class<T> objectClazz) {
        final File inputFile       = new File( basePath, id + EXTENSION );

        if ( !inputFile.exists() ) {
            throw new PersistenceException( "Couldn't locate file for id=" + id + " @ " + inputFile.getAbsolutePath() );
        }

        return readObjectFromFile( inputFile, objectClazz );
    }

    public <T extends Object> T[] loadAll( Class<T> objectClazz ) {
        final File baseDirectory    = new File( basePath );
        final File[] allFiles       = baseDirectory.listFiles( new FilenameFilter() {
            public boolean accept( File dir, String name ) {
                return name.endsWith( EXTENSION );
            }
        } );

        final T[]objects            = (T[]) Array.newInstance( objectClazz, allFiles.length );
        for( int i = 0, allFilesLength = allFiles.length; i < allFilesLength; i++ ) {
            final File file = allFiles[ i ];
            objects[ i ] = readObjectFromFile( file, objectClazz );
        }

        return objects;
    }

    private <T extends Object> T readObjectFromFile( File inputFile, Class<T> objectClazz) {
        InputStream inputStream     = null;
        try {
            inputStream             = new FileInputStream( inputFile );

            return fileInputOutput.readObject( inputStream, objectClazz );
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

    private void writeObjectToFile(String id, Object obj) {
        final File outputFile       = new File( basePath, id + EXTENSION );

        if ( outputFile.exists() ) {
            LOGGER.warn( "File already exists.  Attempting to overwrite file: " + outputFile.getAbsolutePath() );
        } else {
           LOGGER.info( "File doesn't exist.  Creating output file: " + outputFile.getAbsolutePath() );
        }

        Writer outputWriter         = null;
        try {
            outputWriter            = new FileWriter( outputFile, false );
            fileInputOutput.writeObject( obj, outputWriter );

        } catch (IOException e) {
            throw new PersistenceException( "Couldn't save IndexFile: " + outputFile.getAbsolutePath(), e );
        } finally {
            if ( outputWriter != null ) {
                try {
                    outputWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
    }

}
