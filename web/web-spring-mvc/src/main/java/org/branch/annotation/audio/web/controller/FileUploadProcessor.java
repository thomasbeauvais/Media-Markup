package org.branch.annotation.audio.web.controller;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.branch.annotation.audio.dao.MetadataRepository;
import org.branch.annotation.audio.dao.SamplesRepository;
import org.branch.annotation.audio.io.AudioStreamIndexer;
import org.branch.annotation.audio.io.FileStore;
import org.branch.annotation.audio.model.dao.Metadata;
import org.branch.annotation.audio.model.dao.Samples;
import org.branch.annotation.audio.model.dao.Summary;
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
    private SamplesRepository samplesRepository;

    @Autowired
    private MetadataRepository metadataRepository;

    public void uploadFile(Map<String, String> metadataMap, InputStream inputStream) throws IOException
    {
        final long start = System.currentTimeMillis();

        final String originalFilename = (String) metadataMap.get("originalFilename");

        try
        {
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            IOUtils.copy(inputStream, byteArrayOutputStream);

            logger.info("*** Uploading file: " + originalFilename);

            logger.info("*** Creating samples: " + originalFilename);

            // TODO split so that each indexing and uploading is asynchronous
            final byte[] bytes = byteArrayOutputStream.toByteArray();
            final Samples samples = audioStreamIndexer.createIndex(new ByteArrayInputStream(bytes));

            final Summary summary = new Summary();

            // TODO figure out how to do proper inheritance so that we don't have to duplicate this info
            summary.setTime(samples.getTime());
            summary.setSize(samples.getSize());
            summary.setDateUploaded(System.currentTimeMillis());

            logger.info("*** Saving file: " + originalFilename);

            summary.setAudioFileUid(fileStore.persist(bytes));

            logger.info("*** Saving samples: " + originalFilename);

            samples.setSummary(summary);

            samplesRepository.save(samples);

            logger.info("*** Saving metadata: " + originalFilename);

            final Metadata metadata = new Metadata(samples.getId(), metadataMap);

            metadataRepository.save(metadata);

            logger.info("*** Upload of file and creation of samples is complete for " + originalFilename);
        }
        finally
        {
            if (inputStream != null)
            {
                inputStream.close();
            }

            logger.info("Time to upload " + originalFilename + " was " + convertMillis(System.currentTimeMillis() - start));
        }
    }
}
