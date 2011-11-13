package com.company.common.io;

import com.company.common.api.IFileInputOutput;
import com.company.common.api.IPersistenceEngine;
import com.company.common.io.exceptions.PersistenceException;
import org.apache.log4j.Logger;

import java.io.*;

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

    private IFileInputOutput fileInputOutput;
    private static final String EXTENSION = ".index";

    public FilePersistenceEngine(String basePath, IFileInputOutput fileInputOutput) {
        this.basePath = basePath;
        this.fileInputOutput = fileInputOutput;
    }

    public void save(String id, Object obj) {
        writeObjectToFile(id, obj);
    }

    public <T extends Object> T load(String id, Class<T> objectClazz) {
        return readObjectFromFile( id, objectClazz );
    }

    private <T extends Object> T readObjectFromFile(String id, Class<T> objectClazz) {
        final File outputFile       = new File( basePath, id + EXTENSION );

        if ( !outputFile.exists() ) {
            throw new PersistenceException( "Couldn't locate file for id=" + id + " @ " + outputFile.getAbsolutePath() );
        }

        Reader reader               = null;
        try {
            reader                  = new FileReader( outputFile );

            return fileInputOutput.readObject( reader, objectClazz );
        } catch (Throwable e) {
            throw new PersistenceException( "Error opening file: " + outputFile.getAbsolutePath(), e );
        } finally {
            if ( reader != null ) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
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
