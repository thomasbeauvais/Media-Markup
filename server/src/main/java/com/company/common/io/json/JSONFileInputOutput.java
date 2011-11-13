package com.company.common.io.json;

import com.company.common.api.IFileInputOutput;
import com.company.common.io.exceptions.PersistenceException;
import com.company.common.pojos.IndexObject;
import com.company.common.pojos.Sample;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * Created by IntelliJ IDEA.
 * User: tbeauvais
 * Date: 11/5/11
 * Time: 11:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class JSONFileInputOutput implements IFileInputOutput {
    private IndexObject lcl_readIndexObject( final Reader reader ) throws IOException {
        final IndexObject indexObject   = new IndexObject();

        final JSONObject jsonIndex      = (JSONObject) JSONValue.parse(reader);
        indexObject.setName((String) jsonIndex.get( "name" ));
        indexObject.setNumChannels(((Long) jsonIndex.get("numChannels")).intValue());

        indexObject.setMaxes( JSONUtils.readShortArray(jsonIndex, "maxes") );
        indexObject.setMins( JSONUtils.readShortArray(jsonIndex, "mins") );

        final long[] arrPos             = JSONUtils.readLongArray( jsonIndex, "positions" );
        final float[] arrTimeStamps     = JSONUtils.readFloatArray(jsonIndex, "timeStamps");
        final short[] arrSamples        = JSONUtils.readShortArray(jsonIndex, "samples");

        final int size                  = arrPos.length;
        for (int i = 0; i < size; i++) {
            final Sample sample = new Sample( arrTimeStamps[ i ], arrPos[ i ], new short[] { arrSamples[ i ] } );

            indexObject.addSample( sample, false );
        }

        indexObject.updateBounds();

        return indexObject;
    }

    private void lcl_writeIndexObject(final IndexObject indexObject, final Writer writer) throws IOException {
        final JSONObject jsonIndex      = new JSONObject();
        jsonIndex.put("name", indexObject.getName() );
        jsonIndex.put("numChannels", (Integer) indexObject.getNumChannels() );

        JSONUtils.writeShortArray( jsonIndex, "maxes", indexObject.getMaxes() );
        JSONUtils.writeShortArray(jsonIndex, "mins", indexObject.getMaxes());

        final Sample[] sampleObjects    = indexObject.getSamples();

        final short[] samples           = new short[ sampleObjects.length ];
        final float[] timeStamps        = new float[ sampleObjects.length ];
        final long[] positions          = new long[ sampleObjects.length ];


        for (int i = 0; i < samples.length; i++) {
            final Sample sample         = sampleObjects[ i ];

            samples[ i ]                = sample.getSamples() [ 0 ];
            timeStamps[ i ]             = sample.getTimeStamp();
            positions[ i ]              = sample.getPosition();

        }

        JSONUtils.writeShortArray( jsonIndex, "samples", samples );
        JSONUtils.writeFloatArray( jsonIndex, "timeStamps", timeStamps );
        JSONUtils.writeLongArray( jsonIndex, "positions", positions );

        jsonIndex.writeJSONString( writer );
    }

    public <T extends Object> T readObject(Reader reader, Class<T> objectClazz) throws IOException {
        if ( objectClazz.equals( IndexObject.class ) ) {
            return (T) lcl_readIndexObject( reader );
        }

        throw new PersistenceException( "Class, " + objectClazz.getName() + ", not supported." );
    }

    public void writeObject(Object obj, Writer writer) throws IOException {
        //TODO: Instance of?
        lcl_writeIndexObject((IndexObject) obj, writer);
    }
}
