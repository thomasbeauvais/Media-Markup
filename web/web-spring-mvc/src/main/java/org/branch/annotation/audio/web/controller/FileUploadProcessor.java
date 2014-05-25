package org.branch.annotation.audio.web.controller;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.branch.annotation.audio.dao.IndexSamplesRepository;
import org.branch.annotation.audio.dao.MetadataRepository;
import org.branch.annotation.audio.io.AudioStreamIndexer;
import org.branch.annotation.audio.io.FileStore;
import org.branch.annotation.audio.model.dao.IndexSamples;
import org.branch.annotation.audio.model.dao.Metadata;
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

    @Autowired
    private MetadataRepository metadataRepository;

    public void uploadFile(Map<String, Object> metadataMap, InputStream inputStream) throws IOException
    {
        final long start = System.currentTimeMillis();

        final String originalFilename = (String) metadataMap.get("originalFilename");

        try
        {
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            IOUtils.copy(inputStream, byteArrayOutputStream);

            logger.info("*** Creating IndexSamples: " + originalFilename);

            // TODO split so that each indexing and uploading is asynchronous
            final byte[] bytes = byteArrayOutputStream.toByteArray();
            final IndexSamples indexSamples = audioStreamIndexer.createIndex(new ByteArrayInputStream(bytes));

            logger.info("*** Created AudioFile: " + originalFilename);

            logger.info("*** Attempting to save AudioFile: " + originalFilename);

            final String fileId = fileStore.persist(bytes);

            indexSamples.setAudioFileUid(fileId);

            logger.info("*** Attempting to save IndexSamples: " + originalFilename);

            indexSamplesRepository.save(indexSamples);

            final Metadata metadata = new Metadata(indexSamples.getId(), metadataMap);

            metadataRepository.save(metadata);

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
