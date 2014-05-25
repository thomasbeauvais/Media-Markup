package org.branch.annotation.audio;

import org.branch.annotation.audio.io.AudioStreamIndexer;
import org.branch.annotation.audio.dao.IndexSummaryRepository;
import org.branch.annotation.audio.model.dao.IndexSamples;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class IndexSummaryIntegrationTest extends AbstractSpringTest
{
    @Autowired
    AudioStreamIndexer audioStreamIndexer;

    @Autowired
    IndexSummaryRepository indexSummaryRepository;

    @Test
    @Ignore("Test fails: Value too long for column \"SAMPLES BINARY(255)\"")
    public void persist_indexed_audio() throws FileNotFoundException
    {
        final File file = new File("data/audio/test-file-small.mp3");

        assertTrue(file.exists());

        final IndexSamples indexSamples = audioStreamIndexer.createIndex(new FileInputStream(file));

        assertNotNull(indexSamples);
        assertEquals(indexSamples.size(), 7993);

        final IndexSamples saved = indexSummaryRepository.save(indexSamples);

        assertNotNull(saved.getAudioFileUid());

    }
}
