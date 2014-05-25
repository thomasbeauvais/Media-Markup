package org.branch.annotation.audio.io;

import org.branch.common.utils.FileUtils;
import org.apache.log4j.Logger;
import org.branch.common.UncheckedException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class LocalFileStore implements FileStore
{
    private File storeDirectory;

    private static Logger logger = Logger.getLogger("org.branch.annotation.audio");

    @Override
    public String persist(byte[] bytes)
    {
        final String fileId = String.valueOf(UUID.randomUUID());

        final File file = new File(storeDirectory, fileId);

        try
        {
            logger.info("*** persisting file to: " + FileUtils.getCanonicalPath(file));

            FileUtils.writeByteArrayToFile(file, bytes);
        }
        catch (IOException e)
        {
            throw new UncheckedException(e);
        }

        return fileId;
    }

    @Override
    public void delete(String fileId)
    {
        final File file = getFileFromId(fileId);

        if (file.exists())
        {
            try
            {
                FileUtils.forceDelete(file);
            }
            catch (IOException e)
            {
                throw new UncheckedException(e);
            }
        }
    }

    @Override
    public byte[] getBytes(String fileId)
    {
        try
        {
            return FileUtils.readFileToByteArray(getFileFromId(fileId));
        }
        catch (IOException e)
        {
            throw new UncheckedException(e);
        }
    }

    @Override
    public InputStream getInputStream(String fileId)
    {
        try
        {
            return FileUtils.openInputStream(getFileFromId(fileId));
        }
        catch (IOException e)
        {
            throw new UncheckedException(e);
        }
    }

    private File getFileFromId(String fileId)
    {
        return new File(storeDirectory, fileId);
    }

    public File getStoreDirectory()
    {
        return storeDirectory;
    }

    public void setStoreDirectory(File storeDirectory)
    {
        this.storeDirectory = storeDirectory;

        logger.info("*** setting storeDirectory to: " + FileUtils.getCanonicalPath(storeDirectory));

        try
        {
            FileUtils.forceMkdir(storeDirectory);
        }
        catch (IOException e)
        {
            throw new UncheckedException(e);
        }
    }
}
