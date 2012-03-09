package com.company.annotation.audio.api;

import com.company.annotation.audio.pojos.SampleList;

import java.io.InputStream;

/**
 */
public interface IIndexEngine {
    /**
     *
     * @param inputStream
     * @return
     */
    SampleList createIndexForAudioStream( InputStream inputStream, String indexName );
}
