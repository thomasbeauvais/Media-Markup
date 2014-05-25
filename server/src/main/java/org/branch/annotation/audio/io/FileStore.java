package org.branch.annotation.audio.io;

import java.io.InputStream;
import java.util.Map;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 25.05.14
 */
public interface FileStore
{
    /**
     * Persists the {@code byte[]} in the {@link FileStore} and returns the {@code fileId} for later retrieval.
     *
     * @param metadata
     * @param bytes
     * @return
     */
    String persist(Map<String, Object> metadata, byte[] bytes);

    byte[] getBytes(String fileId);

    InputStream getInputStream(String fileId);
}
