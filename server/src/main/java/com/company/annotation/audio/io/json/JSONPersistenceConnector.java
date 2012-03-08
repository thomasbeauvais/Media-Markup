package com.company.annotation.audio.io.json;

import com.company.annotation.audio.api.IPersistenceConnector;
import com.company.annotation.audio.io.exceptions.PersistenceException;
import com.company.annotation.audio.pojos.IndexObject;
import com.company.annotation.audio.pojos.IndexSummary;
import com.company.annotation.audio.pojos.Sample;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: tbeauvais
 * Date: 11/5/11
 * Time: 11:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class JSONPersistenceConnector implements IPersistenceConnector {
    private IndexSummary lcl_readIndexSummary( @NotNull final InputStream reader ) throws IOException {
        final JSONObject jsonIndex      = (JSONObject) JSONValue.parse( new InputStreamReader( reader ) );

        return lcl_readIndexCommon( jsonIndex, new IndexSummary() );
    }

    private IndexSummary lcl_readIndexCommon( @NotNull final JSONObject jsonIndex, @NotNull final IndexSummary instance ) throws IOException {
        instance .setName((String) jsonIndex.get( "name" ));
        instance.setNumChannels(((Long) jsonIndex.get("numChannels")).intValue());

        return instance;
    }

    private IndexObject lcl_readIndexObject( @NotNull final InputStream reader ) throws IOException {
        final IndexObject indexObject   = new IndexObject();

        final JSONObject jsonIndex      = (JSONObject) JSONValue.parse( new InputStreamReader( reader ) );

        lcl_readIndexCommon( jsonIndex, indexObject );

        indexObject.setMax( ((Long) jsonIndex.get( "max" )).shortValue() );
        indexObject.setMin( ((Long) jsonIndex.get( "min" )).shortValue() );

        final long[] arrPos             = JSONUtils.readLongArray( jsonIndex, "positions" );
        final float[] arrTimeStamps     = JSONUtils.readFloatArray(jsonIndex, "timeStamps");
        final short[] arrSamples        = JSONUtils.readShortArray(jsonIndex, "samples");

        final int size                  = arrPos.length;
        for (int i = 0; i < size; i++) {
            final Sample sample = new Sample( arrTimeStamps[ i ], arrPos[ i ], arrSamples[ i ] );

            indexObject.addSample( sample, false );
        }

        indexObject.updateBounds();

        return indexObject;
    }

    private void lcl_writeIndexObject(final IndexObject indexObject, final Writer writer) throws IOException {
        final JSONObject jsonIndex      = new JSONObject();
        jsonIndex.put("name", indexObject.getName() );
        jsonIndex.put("numChannels", (Integer) indexObject.getNumChannels() );
        jsonIndex.put("max", (Short) indexObject.getMax() );
        jsonIndex.put("min", (Short) indexObject.getMin() );

        final Sample[] sampleObjects    = indexObject.getSamples();

        final short[] samples           = new short[ sampleObjects.length ];
        final float[] timeStamps        = new float[ sampleObjects.length ];
        final long[] positions          = new long[ sampleObjects.length ];


        for (int i = 0; i < samples.length; i++) {
            final Sample sample         = sampleObjects[ i ];

            samples[ i ]                = sample.getValue();
            timeStamps[ i ]             = sample.getTimeStamp();
            positions[ i ]              = sample.getPosition();

        }

        JSONUtils.writeShortArray( jsonIndex, "samples", samples );
        JSONUtils.writeFloatArray( jsonIndex, "timeStamps", timeStamps );
        JSONUtils.writeLongArray( jsonIndex, "positions", positions );

        jsonIndex.writeJSONString( writer );
    }

    public <T extends Object> T readObject( @NotNull InputStream inputStream, @NotNull Class<T> objectClazz ) throws IOException {
        if ( objectClazz.equals( IndexObject.class ) ) {
            return (T) lcl_readIndexObject( inputStream );
        } else if ( objectClazz.equals( IndexSummary.class ) ) {
            return (T) lcl_readIndexSummary( inputStream );
        }

        throw new PersistenceException( "Class, " + objectClazz.getName() + ", not supported." );
    }

    public void writeObject( @NotNull Object obj, @NotNull Writer writer ) throws IOException {
        if ( obj instanceof IndexObject ) {
            lcl_writeIndexObject((IndexObject) obj, writer);
        } else if ( obj instanceof IndexSummary ) {
//            lcl_writeIndexObject((IndexObject) obj, writer);

            throw new PersistenceException( "Currently, " + IndexSummary.class.getSimpleName() + " objects are readonly!" );
        } else {
            throw new PersistenceException( "Class, " + obj.getClass() + ", not supported." );
        }
    }
}
