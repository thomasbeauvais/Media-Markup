package org.branch.annotation.audio.api;

import org.branch.annotation.audio.model.jpa.IndexSamples;

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
