package org.branch.annotation.audio.io;

import org.branch.annotation.audio.model.dao.IndexSamples;

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
    IndexSamples createIndex(InputStream inputStream);
}
