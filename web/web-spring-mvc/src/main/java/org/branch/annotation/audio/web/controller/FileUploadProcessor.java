package org.branch.annotation.audio.web.controller;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.branch.annotation.audio.io.AudioStreamIndexer;
import org.branch.annotation.audio.io.FileStore;
import org.branch.annotation.audio.dao.IndexSamplesRepository;
import org.branch.annotation.audio.model.dao.AudioFile;
import org.branch.annotation.audio.model.dao.IndexSamples;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static org.branch.common.utils.HumanReadable.*;

/**
 * This controller will be used to upload the MP3 files to the database and index them.
 * <p/>
 * TODO:  Should this be abstracted?  Are there other places that make more sense to store the bytes?
 */
public class FileUploadProcessor
{
    private static Logger logger = Logger.getLogger("org.branch.annotation.audio");

    @Autowired
    private AudioStreamIndexer audioStreamIndexer;

    @Autowired
    private FileStore fileStore;

    @Autowired
    private IndexSamplesRepository indexSamplesRepository;

    public void uploadFile(Map<String, Object> metadata, InputStream inputStream) throws IOException
    {
        final long start = System.currentTimeMillis();

        final String originalFilename = (String) metadata.get("originalFilename");

        try
        {
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            IOUtils.copy(inputStream, byteArrayOutputStream);

            logger.info("*** Creating IndexSamples: " + originalFilename);

            // TODO split so that each indexing and uploading is asynchronous
            final byte[] bytes = byteArrayOutputStream.toByteArray();
            final IndexSamples indexSamples = audioStreamIndexer.createIndex(new ByteArrayInputStream(bytes));

            final AudioFile audioFile = new AudioFile();
            audioFile.setBytes(bytes);

            logger.info("*** Created AudioFile: " + originalFilename);

            logger.info("*** Attempting to save AudioFile: " + originalFilename);

            final String fileId = fileStore.persist(metadata, bytes);

            // TODO:  Add this
//                indexSamples.setOriginalFilename( fileName );
            indexSamples.setAudioFileUid(fileId);

            logger.info("*** Attempting to save IndexSamples: " + originalFilename);

            indexSamplesRepository.save(indexSamples);

            logger.info("*** Creation of IndexSamples complete: " + originalFilename);
        }
        finally
        {
            if (inputStream != null)
            {
                inputStream.close();
            }

            logger.info("Time to upload " + originalFilename + " was " + convertMillis(start - System.currentTimeMillis()));
        }
    }
}
