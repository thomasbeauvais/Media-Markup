//package com.company.annotation.audio.io.json;
//
//import com.company.annotation.audio.api.IFilePersistenceConnector;
//import com.company.annotation.audio.api.IPersistenceEngine;
//import com.company.annotation.audio.io.exceptions.PersistenceException;
//import com.company.annotation.audio.pojos.Comment;
//import com.company.annotation.audio.pojos.IndexSummary;
//import com.company.annotation.audio.pojos.Sample;
//import com.company.annotation.audio.pojos.SampleList;
//import org.jetbrains.annotations.NotNull;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.JSONValue;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Lazy;
//
//import java.io.*;
//import java.util.List;
//import java.util.Vector;
//
///**
// * Created by IntelliJ IDEA.
// * User: tbeauvais
// * Date: 11/5/11
// * Time: 11:47 PM
// * To change this template use File | Settings | File Templates.
// */
//public class JSONFilePersistenceConnector implements IFilePersistenceConnector {
//
//
//    private IPersistenceEngine persistenceEngine;
//
//    @Autowired
//    @Lazy
//    public void setPersistenceEngine( IPersistenceEngine persistenceEngine ) {
//        this.persistenceEngine = persistenceEngine;
//    }
//
//    public static final String KEY_NUM_CHANNELS     = "numChannels";
//    public static final String KEY_TIME             = "time";
//    public static final String KEY_NAME             = "name";
//    public static final String KEY_DESCRIPTION      = "description";
//
//    public static final String KEY_ID_INDEX         = "idIndexFile";
//    public static final String KEY_ANNOTATIONS      = "annotations";
//    public static final String KEY_TEXT             = "text";
//    public static final String KEY_AUTHOR           = "author";
//    public static final String KEY_DATE             = "date";
//
//    private IndexSummary lcl_readIndexSummary( @NotNull String id, @NotNull final InputStream reader) throws IOException {
//        final JSONObject jsonIndex      = (JSONObject) JSONValue.parse( new InputStreamReader( reader ) );
//        final IndexSummary indexSummary = new IndexSummary();
//
//        indexSummary.setName( (String) jsonIndex.get( KEY_NAME ) );
//        indexSummary.setTime( (Float) jsonIndex.get( KEY_TIME ) );
//        indexSummary.setDescription( (String) jsonIndex.get( KEY_DESCRIPTION ) );
//        indexSummary.setNumChannels( ( (Long) jsonIndex.get( "numChannels" ) ).intValue() );
//        indexSummary.setId( id );
//
//        final JSONArray jsonAnnotations     = (JSONArray) jsonIndex.get( KEY_ANNOTATIONS );
//        final List<Comment> annotations  = new Vector<Comment>();
//        for ( Object jsonObject: jsonAnnotations.toArray() ) {
//            final JSONObject jsonAnnotation = (JSONObject) jsonObject;
//            final Comment annotation     = new Comment();
//            annotation.setText((String) jsonAnnotation.get( KEY_TEXT ));
//            annotation.setIndexSummary( id );
//        }
//
//        return indexSummary;
//    }
//
//    private SampleList lcl_readSampleList( @NotNull String id, @NotNull final InputStream reader) throws IOException {
//        final SampleList sampleList = new SampleList();
//
//        final JSONObject jsonIndex      = (JSONObject) JSONValue.parse( new InputStreamReader( reader ) );
//
//        sampleList.setMax( ((Long) jsonIndex.get( "max" )).shortValue() );
//        sampleList.setMin( ((Long) jsonIndex.get( "min" )).shortValue() );
//
//        final long[] arrPos             = JSONUtils.readLongArray( jsonIndex, "positions" );
//        final float[] arrTimeStamps     = JSONUtils.readFloatArray(jsonIndex, "timeStamps");
//        final short[] arrSamples        = JSONUtils.readShortArray(jsonIndex, "samples");
//
//        final int size                  = arrPos.length;
//        for (int i = 0; i < size; i++) {
//            final Sample sample = new Sample( arrTimeStamps[ i ], arrPos[ i ], arrSamples[ i ] );
//
//            sampleList.addSample( sample, false );
//        }
//
//        sampleList.updateBounds();
//
//        return sampleList;
//    }
//
//    private void lcl_writeIndexSummary(final IndexSummary indexObject, final JSONObject jsonIndex) throws IOException {
//        jsonIndex.put( KEY_NAME, indexObject.getName() );
//        jsonIndex.put( KEY_DESCRIPTION, indexObject.getDescription() );
//        jsonIndex.put( KEY_TIME, (Float) indexObject.getTime() );
//        jsonIndex.put( KEY_NUM_CHANNELS, (Integer) indexObject.getNumChannels() );
//
//        final JSONArray jsonAnnotations = new JSONArray();
//        for ( Comment annotation : indexObject.getComments() ) {
//            final JSONObject jsonObject = new JSONObject();
//            jsonObject.put( KEY_TEXT, annotation.getText() );
//            jsonObject.put( KEY_ID_INDEX, annotation.getIndexSummary() );
//
//            jsonAnnotations.add( jsonObject );
//        }
//
//        jsonIndex.put( KEY_ANNOTATIONS, jsonAnnotations );
//    }
//
//    private void lcl_writeSampleList(final SampleList sampleList, final JSONObject jsonIndex) throws IOException {
//        jsonIndex.put("max", (Short) sampleList.getMax() );
//        jsonIndex.put( "min", (Short) sampleList.getMin() );
//
//        final Sample[] sampleObjects    = sampleList.getSamples();
//
//        final short[] samples           = new short[ sampleObjects.length ];
//        final float[] timeStamps        = new float[ sampleObjects.length ];
//        final long[] positions          = new long[ sampleObjects.length ];
//
//
//        for (int i = 0; i < samples.length; i++) {
//            final Sample sample         = sampleObjects[ i ];
//
//            samples[ i ]                = sample.getValue();
//            timeStamps[ i ]             = sample.getTimeStamp();
//            positions[ i ]              = sample.getPosition();
//
//        }
//
//        JSONUtils.writeShortArray( jsonIndex, "samples", samples );
//        JSONUtils.writeFloatArray( jsonIndex, "timeStamps", timeStamps );
//        JSONUtils.writeLongArray( jsonIndex, "positions", positions );
//    }
//
//    public <T extends Object> T readObject( @NotNull String id, @NotNull InputStream inputStream, @NotNull Class<T> objectClazz ) throws IOException {
//        if ( objectClazz.equals( SampleList.class ) ) {
//            return (T) lcl_readSampleList( id, inputStream );
//        } else if ( objectClazz.equals( IndexSummary.class ) ) {
//            return (T) lcl_readIndexSummary( id, inputStream );
//        }
//
//        throw new PersistenceException( "Class, " + objectClazz.getName() + ", not supported." );
//    }
//
//    public void writeObject( @NotNull String id, @NotNull Object obj, @NotNull Writer writer ) throws IOException {
//        throw new PersistenceException( "Not supported." );
//    }
//
//    public void writeObject( @NotNull String id, @NotNull Object obj, @NotNull File outputFile ) throws IOException {
//        JSONObject jsonObject   = new JSONObject();
//
//        // If there already exists a file, then we want to modify the existing one..
//        if ( outputFile.exists() ) {
//            InputStreamReader inputStreamReader = null;
//            try{
//                inputStreamReader   = new InputStreamReader( new FileInputStream( outputFile ) );
//                jsonObject          = (JSONObject) JSONValue.parse( inputStreamReader );
//            } finally {
//                if (inputStreamReader != null ) inputStreamReader.close();
//            }
//        }
//
//        if ( obj instanceof SampleList ) {
//            lcl_writeSampleList( (SampleList) obj, jsonObject );
//        } else if ( obj instanceof IndexSummary ) {
//            lcl_writeIndexSummary( (IndexSummary) obj, jsonObject );
//        } else {
//            throw new PersistenceException( "Class, " + obj.getClass() + ", not supported." );
//        }
//
//        Writer writer = null;
//
//        try {
//            writer = new FileWriter( outputFile, false );
//            jsonObject.writeJSONString( writer );
//        } finally {
//            writer.close();
//        }
//    }
//}
