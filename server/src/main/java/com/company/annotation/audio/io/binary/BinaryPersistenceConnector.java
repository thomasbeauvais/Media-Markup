package com.company.annotation.audio.io.binary;

import com.company.annotation.audio.api.IFilePersistenceConnector;
import com.company.annotation.audio.api.IPersistenceEngine;
import com.company.annotation.audio.io.exceptions.PersistenceException;
import com.company.annotation.audio.pojos.IndexSummary;
import com.company.annotation.audio.pojos.Sample;
import com.company.annotation.audio.pojos.SampleList;
import com.company.common.utils.DataOutputUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.io.*;

/**
 */
public class BinaryPersistenceConnector implements IFilePersistenceConnector {

    private IPersistenceEngine persistenceEngine;

    @Autowired
    @Lazy
    public void setPersistenceEngine( IPersistenceEngine persistenceEngine ) {
        this.persistenceEngine = persistenceEngine;
    }

    private IndexSummary lcl_readIndexSummary( @NotNull final DataInput dataInput ) throws IOException {
        final IndexSummary indexSummary = new IndexSummary();

        indexSummary.setName( dataInput.readUTF() );
        indexSummary.setDescription(dataInput.readUTF());
        indexSummary.setTime(dataInput.readFloat());
        indexSummary.setNumChannels( dataInput.readInt() );

        return indexSummary;
    }

    private SampleList lcl_readSampleList( @NotNull final DataInputStream dataInputStream ) throws IOException {
        final SampleList sampleList = new SampleList();

        sampleList.setMax(dataInputStream.readShort());
        sampleList.setMin(dataInputStream.readShort());

        final short[] arrSamples        = DataOutputUtils.readShortArray(dataInputStream);
        final float[] arrTimeStamps     = DataOutputUtils.readFloatArray(dataInputStream);
        final long[] arrPos             = DataOutputUtils.readLongArray(dataInputStream);

        final int size                  = arrPos.length;
        for (int i = 0; i < size; i++) {
            final Sample sample = new Sample( arrTimeStamps[ i ], arrPos[ i ], arrSamples[ i ] );

            sampleList.addSample( sample, false );
        }

        sampleList.updateBounds();

        return sampleList;
    }

    private void lcl_writeIndexSummary(final IndexSummary indexObject, final DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(indexObject.getName());
        dataOutput.writeUTF(indexObject.getDescription() == null ? "" : indexObject.getDescription() );
        dataOutput.writeDouble(indexObject.getTime());
        dataOutput.writeInt(indexObject.getNumChannels());
    }

    private void lcl_writeSampleList(final SampleList sampleList, final DataOutput dataOutput) throws IOException {
        final Sample[] sampleObjects    = sampleList.getSamples();

        final short[] samples           = new short[ sampleObjects.length ];
        final float[] timeStamps        = new float[ sampleObjects.length ];
        final long[] positions          = new long[ sampleObjects.length ];

        for (int i = 0; i < samples.length; i++) {
            final Sample sample         = sampleObjects[ i ];

            samples[ i ]                = sample.getValue();
            timeStamps[ i ]             = sample.getTimeStamp();
            positions[ i ]              = sample.getPosition();

        }

        dataOutput.writeShort(sampleList.getMax());
        dataOutput.writeShort(sampleList.getMin());

        DataOutputUtils.writeShortArray(dataOutput, samples);
        DataOutputUtils.writeFloatArray(dataOutput, timeStamps);
        DataOutputUtils.writeLongArray(dataOutput, positions);
    }

    public <T extends Object> T readObject( @NotNull String id, @NotNull InputStream inputStream, @NotNull Class<T> objectClazz ) throws IOException {
        if ( objectClazz.equals( SampleList.class ) ) {
            return (T) lcl_readSampleList( new DataInputStream( inputStream ) );
        } else if ( objectClazz.equals( IndexSummary.class ) ) {
            return (T) lcl_readIndexSummary( new DataInputStream( inputStream ) );
        }

        throw new PersistenceException( "Class, " + objectClazz.getName() + ", not supported." );
    }

    public void writeObject( @NotNull String id, @NotNull Object obj, @NotNull Writer writer ) throws IOException {
        throw new PersistenceException( "Not supported." );
    }

    public void writeObject( @NotNull String id, @NotNull Object obj, @NotNull File outputFile ) throws IOException {
        // If there already exists a file, then we want to modify the existing one..
        final DataOutputStream outputStream = new DataOutputStream( new FileOutputStream( outputFile ) );

        if ( obj instanceof SampleList ) {
            lcl_writeSampleList( (SampleList) obj, outputStream );

        } else if ( obj instanceof IndexSummary ) {
            lcl_writeIndexSummary( (IndexSummary) obj, outputStream );
        } else {
            throw new PersistenceException( "Class, " + obj.getClass() + ", not supported." );
        }

        try {
            outputStream.close();
        } finally {

        }
    }
}
