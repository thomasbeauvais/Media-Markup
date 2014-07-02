package org.branch.annotation.audio.io;

import java.io.InputStream;

public interface FileStore
{
    /**
     * Persists the {@code byte[]} in the {@link FileStore} and returns the {@code fileId} for later retrieval.
     *
     * @param bytes
     * @return
     */
    String persist(byte[] bytes);

    void delete(String id);

    byte[] getBytes(String fileId);

    InputStream getInputStream(String fileId);
}
