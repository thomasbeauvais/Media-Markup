package com.company.annotation.audio.api;

import com.company.annotation.audio.pojos.IndexObject;

import javax.print.DocFlavor;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 */
public interface IIndexEngine {
    /**
     *
     * @param inputStream
     * @return
     */
    IndexObject makeIndexObject( InputStream inputStream, String indexName );
}
