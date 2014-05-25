package org.branch.annotation.audio.dao;

import org.branch.annotation.audio.AbstractSpringTest;
import org.branch.annotation.audio.io.AudioStreamIndexer;
import org.branch.annotation.audio.model.dao.Samples;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class SummaryIntegrationTest extends AbstractSpringTest
{
    @Autowired
    AudioStreamIndexer audioStreamIndexer;

    @Autowired
    SamplesRepository samplesRepository;

    @Test
    public void persist_indexed_audio() throws FileNotFoundException
    {
        final File file = new File("data/audio/test-file-small.mp3");

        assertTrue(file.exists());

        final Samples indexSamples = audioStreamIndexer.createIndex(new FileInputStream(file));

        assertNotNull(indexSamples);
        assertEquals(indexSamples.size(), 7993);

        final Samples saved = samplesRepository.save(indexSamples);

        assertNotNull(saved.getId());
    }
}
