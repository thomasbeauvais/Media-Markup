package com.company.annotation.audio;

import com.company.annotation.audio.api.IPersistenceEngine;
import com.company.annotation.audio.pojos.Comment;
import com.company.annotation.audio.pojos.IndexWithSamples;
import com.company.annotation.audio.pojos.IndexSummary;
import com.company.annotation.audio.pojos.SampleList;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: tbeauvais
 * Date: 7/11/12
 * Time: 11:50 AM
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration( "classpath*:applicationContext.xml" )
@RunWith( SpringJUnit4ClassRunner.class )
public class JpaPersistenceEngineTest {
    @Autowired
    private IPersistenceEngine persistenceEngine;

    @Test
    public void testSave() {
        final IndexWithSamples summary = new IndexWithSamples();
        summary.setName( "test summary one");

        final List<Comment> comments = new Vector<Comment>();
        comments.add( new Comment( summary, "test comment one", 0, 0 ) );
        comments.add( new Comment( summary, "test comment two", 0, 0 ) );

        summary.setComments( comments );

        persistenceEngine.save( summary );
    }

    @Test
    public void testLoadSamples() {
        final IndexWithSamples[] indexSummaries = persistenceEngine.loadAll( IndexWithSamples.class );

        Assert.assertNotNull( indexSummaries );
        Assert.assertNotSame( 0, indexSummaries.length );

        final SampleList sampleList = indexSummaries[ 0 ].getSampleList();
        Assert.assertNotNull( sampleList );
        Assert.assertNotNull( sampleList.getSamples() );
    }

    @Test
    public void testLoadAll() {
        final IndexSummary[] summaries = persistenceEngine.loadAll( IndexSummary.class );

        Assert.assertNotNull( summaries );
        Assert.assertNotSame( 0, summaries.length );
    }
}
