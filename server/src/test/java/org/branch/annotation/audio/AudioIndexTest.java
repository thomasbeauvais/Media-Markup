package org.branch.annotation.audio;

import org.branch.annotation.audio.api.IndexEngine;
import org.branch.annotation.audio.model.jpa.IndexSamples;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.junit.Assert.*;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 24.05.14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class AudioIndexTest
{
    @Autowired
    IndexEngine indexEngine;

    @Test
    public void valid_createIndex_small() throws FileNotFoundException
    {
        final File file = new File("data/audio/test-file-small.mp3");

        assertTrue(file.exists());

        final IndexSamples indexSamples = indexEngine.createIndex(new FileInputStream(file));

        assertNotNull(indexSamples);
        assertEquals(indexSamples.size(), 7993);
    }

    @Test
    public void valid_createIndex_large() throws FileNotFoundException
    {
        final File file = new File("data/audio/test-file-large.mp3");

        assertTrue(file.exists());

        final IndexSamples indexSamples = indexEngine.createIndex(new FileInputStream(file));

        assertNotNull(indexSamples);
        assertEquals(indexSamples.size(), 161703);
    }
}
