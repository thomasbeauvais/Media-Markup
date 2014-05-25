package org.branch.annotation.audio.io;

import org.apache.commons.io.FileUtils;
import org.branch.annotation.audio.AbstractSpringTest;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;

import static org.junit.Assert.*;

public class LocalFileStoreTest extends AbstractSpringTest
{
    LocalFileStore fileStore;

    final File storeDirectory = new File("target/test-data/LocalFileStoreTest-" + System.currentTimeMillis());

    private String fileId;

    @Before
    public void before()
    {
        fileStore = new LocalFileStore();
        fileStore.setStoreDirectory(storeDirectory);
    }

    @Test
    public void save() throws Exception
    {
        final File input = new File("data/audio/test-file-small.mp3");
        
        fileId = fileStore.persist(FileUtils.readFileToByteArray(input));
        
        // the LocalFileStore puts the files directory to the store directory with the fileId as the filename

        final File expected = new File(storeDirectory, fileId);
        
        assertTrue(expected.exists());
    }

    @Test
    public void delete() throws Exception
    {
        save();

        fileStore.delete(fileId);

        // the LocalFileStore puts the files directory to the store directory with the fileId as the filename

        final File expected = new File(storeDirectory, fileId);

        assertFalse(expected.exists());
    }

    @Test
    public void getBytes() throws Exception
    {
        save();

        final byte[] bytes = fileStore.getBytes(fileId);

        assertNotNull(bytes);
        assertEquals(3333081, bytes.length);
    }

    @Test
    public void getInputStream() throws Exception
    {
        save();

        final InputStream inputStream = fileStore.getInputStream(fileId);

        assertNotNull(inputStream);
        assertEquals(3333081, inputStream.available());
    }
}
