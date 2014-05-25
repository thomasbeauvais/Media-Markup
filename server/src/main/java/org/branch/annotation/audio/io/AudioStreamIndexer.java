package org.branch.annotation.audio.io;

import org.branch.annotation.audio.model.dao.Samples;

import java.io.InputStream;

/**
 */
public interface AudioStreamIndexer
{
    /**
     *
     * @param inputStream
     * @return
     */
    Samples createIndex(InputStream inputStream);
}
