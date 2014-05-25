package org.branch.annotation.audio.web.controller;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.branch.annotation.audio.api.AudioStreamIndexer;
import org.branch.annotation.audio.api.PersistenceEngine;
import org.branch.annotation.audio.model.jpa.AudioFile;
import org.branch.annotation.audio.model.jpa.IndexSamples;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.List;

import static org.branch.common.utils.HumanReadable.*;

/**
 * This controller will be used to upload the MP3 files to the database and index them.
 *
 * TODO:  Should this be abstracted?  Are there other places that make more sense to store the bytes?
 */
public class UploadBean {

    private static Logger logger = Logger.getLogger("org.branch.annotation.audio");

    private PersistenceEngine persistenceEngine;

    @Autowired
    public void setPersistenceEngine( PersistenceEngine persistenceEngine ) {
        this.persistenceEngine = persistenceEngine;
    }

    private AudioStreamIndexer audioStreamIndexer;

    @Autowired
    public void setAudioStreamIndexer(AudioStreamIndexer audioStreamIndexer) {
        this.audioStreamIndexer = audioStreamIndexer;
    }

    private List<String> autoUploadFileList;

    @Autowired
    public void setAutoUploadFileList( List<String> autoUploadFileList ) {
        this.autoUploadFileList = autoUploadFileList;
    }

    @PostConstruct
    public void autoUploadOnPostConstruct( ) {
        if ( this.autoUploadFileList != null ) {
            for( String filePath : autoUploadFileList ) {
                logger.info( "Attempting to automatically upload file:" + filePath);
                InputStream inputStream = null;
                try {
                    inputStream = new FileInputStream(filePath);

                    uploadFile( "auto-" + System.currentTimeMillis(), inputStream );
                } catch (FileNotFoundException e) {
                    logger.warn( "Couldn't upload file.", e );
                } catch (IOException e) {
                    logger.warn( "Couldn't upload file.", e );
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                        }
                    }
                }
            }
        } else {
            logger.info( "No files provided for the automatic upload feature." );
        }
    }

    public void uploadFile( String name, InputStream inputStream ) throws IOException {
        final long start        = System.currentTimeMillis();

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            IOUtils.copy(inputStream, byteArrayOutputStream);

            final byte[] bytes              = byteArrayOutputStream.toByteArray();
            final IndexSamples indexSamples     = audioStreamIndexer.createIndex(new ByteArrayInputStream(bytes));
            indexSamples.setUid(name);

            logger.info("*** Created SampleList: " + name);

            logger.info("*** Creating AudioFile: " + name);

            final AudioFile audioFile = new AudioFile();
            audioFile.setBytes(bytes);

            logger.info("*** Created AudioFile: " + name);

            logger.info("*** Attempting to save AudioFile: " + name);

            final AudioFile saved = persistenceEngine.save( audioFile );

            // TODO:  Add this
//                indexSamples.setOriginalFilename( fileName );
            indexSamples.setAudioFileUid(saved.getUid());

            logger.info("*** Attempting to save SampleList: " + name);

            persistenceEngine.save( indexSamples );

            logger.info("*** Creation of index file complete for: " + name);
        } finally {
            if ( inputStream != null ) {
                inputStream.close();
            }

            logger.info("Time to upload " + name + " was " + convertMillis(start - System.currentTimeMillis()));
        }
    }
}
