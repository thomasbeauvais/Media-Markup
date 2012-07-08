package com.company.annotation.audio.services;

import com.company.annotation.audio.api.IPersistenceEngine;
import com.company.annotation.audio.pojos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: tbeauvais
 * Date: 3/6/12
 * Time: 3:25 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
@RemotingDestination(channels={"my-amf"})
public class AudioAnnotationService implements IAnnotationService {

    private Map<String, SampleList> cache = new HashMap<String, SampleList>();

    public AudioAnnotationService() {
    }

    private IPersistenceEngine persistenceEngine;

    @Autowired
    public void setPersistenceEngine( IPersistenceEngine persistenceEngine ) {
        this.persistenceEngine = persistenceEngine;
    }

    @RemotingInclude
    public IndexSummary[] loadAll() {
        return persistenceEngine.loadAll( IndexSummary.class );
    }

    @RemotingInclude
    public SampleList loadSamples( String id ) {
        return persistenceEngine.load( id, SampleList.class );
    }

    @RemotingInclude
    public void save( String id, IndexSummary indexSummary ) {
        persistenceEngine.save( id, indexSummary );
    }

    @RemotingInclude
    public VisualData loadVisualData( String idContentFile, VisualParameters visualParameters ) {
        try {
            Thread.sleep( 100 );
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        final VisualData visualData = new VisualData();

        if ( cache.get( idContentFile ) == null ) {
            cache.put( idContentFile, loadSamples( idContentFile ) );
        }

        final SampleList sampleList     = cache.get( idContentFile );

        final int[] visualSamples       = new int[ visualParameters.getWidth() ];
        final long[] samplePositions    = new long[ visualParameters.getWidth() ];

        visualData.setVisualSamples( visualSamples );
        visualData.setVisualPositions( samplePositions );

        if ( sampleList != null && sampleList.getSamples() != null ) {
            int startX              = 0;

            Sample[] sampleArray    = sampleList.getSamples();

            int step                = sampleArray.length / visualParameters.getWidth();
            double scale            = sampleList.getMax() / visualParameters.getHeight();

            for ( int i = 0; i < sampleArray.length; i++, startX++ ) {
                int max = 0;
                int min = 0;

                samplePositions[ startX ]   = sampleArray[ i ].getPosition();

                for ( int s = 0; s < step && i < sampleArray.length; s++, i++ ) {
                    max             = Math.max( max, sampleArray[i].getValue() );
                    min             = Math.min( min, sampleArray[i].getValue() );
                }

                int value           = (int) ((max + (min * -1) ) / scale);
                int y               = value / 2;

                visualSamples[ startX ] = y;
            }
        }

        return visualData;
    }

    public VisualRegion[] loadVisualRegions(String indexName, VisualParameters visualParameters) {
        return new VisualRegion[]{
                new VisualRegion( 100, 200, 0, 0, 255 ),
                new VisualRegion( 150, 300, 0, 0, 255 ),
                new VisualRegion( 600, 700, 0, 0, 255 ),
        };
    }

    public IndexSummary loadIndexSummary(String indexName) {
        return persistenceEngine.load( indexName, IndexSummary.class );
    }
}
