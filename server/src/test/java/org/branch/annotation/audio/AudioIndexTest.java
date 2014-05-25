package org.branch.annotation.audio;

import org.branch.annotation.audio.api.AudioStreamIndexer;
import org.branch.annotation.audio.model.jpa.IndexSamples;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.junit.Assert.*;

/**
 * Tests to make sure that whichever {@link org.branch.annotation.audio.api.AudioStreamIndexer} in the context, can properly index a number of audio streams.
 *
 * TODO Parametrize these tests
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 24.05.14
 */
public class AudioIndexTest extends AbstractSpringTest
{
    @Autowired
    AudioStreamIndexer audioStreamIndexer;

    @Test
    public void valid_createIndex_small() throws FileNotFoundException
    {
        final File file = new File("data/audio/test-file-small.mp3");

        assertTrue(file.exists());

        final IndexSamples indexSamples = audioStreamIndexer.createIndex(new FileInputStream(file));

        assertNotNull(indexSamples);
        assertEquals(indexSamples.size(), 7993);
    }

    @Test
    public void valid_createIndex_large() throws FileNotFoundException
    {
        final File file = new File("data/audio/test-file-large.mp3");

        assertTrue(file.exists());

        final IndexSamples indexSamples = audioStreamIndexer.createIndex(new FileInputStream(file));

        assertNotNull(indexSamples);
        assertEquals(indexSamples.size(), 161703);
    }
}
