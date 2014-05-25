package org.branch.annotation.audio.api;

import org.branch.annotation.audio.model.jpa.IndexSamples;

import java.io.InputStream;

/**
 */
public interface IndexEngine
{
    /**
     *
     * @param inputStream
     * @return
     */
    IndexSamples createIndex(InputStream inputStream);
}
