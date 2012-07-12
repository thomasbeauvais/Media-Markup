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
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

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
@TransactionConfiguration(defaultRollback=true)
@RunWith( SpringJUnit4ClassRunner.class )
public class JpaPersistenceEngineTest {
    @Autowired
    private IPersistenceEngine persistenceEngine;

    @Test
    @Transactional
    public void testSaveToExisting() {
        final IndexSummary indexSummary = persistenceEngine.loadAll(IndexSummary.class)[0];
        Assert.assertNotNull( indexSummary );
        Assert.assertNotNull( indexSummary.getComments() );

        Assert.assertSame( 2, indexSummary.getComments().size() );

        final Comment comment = new Comment( "text", 0, 0 );
        indexSummary.getComments().add( comment );

        persistenceEngine.save( indexSummary );

        final Comment comment1              = persistenceEngine.load( comment.getUid(), Comment.class );
        Assert.assertNotNull( comment1 );
        Assert.assertEquals(comment1, comment);

        final IndexSummary indexSummary1    = persistenceEngine.load( indexSummary.getUid(), IndexSummary.class );
        Assert.assertNotNull( indexSummary1 );
        Assert.assertNotNull( indexSummary1.getComments() );

        Assert.assertSame( 3, indexSummary1.getComments().size() );
    }

    @Test
    public void testSave() {
        final IndexWithSamples summary = new IndexWithSamples();
        summary.setName( "test summary one");

        final List<Comment> comments = new Vector<Comment>();
        comments.add( new Comment( "test comment one", 0, 0 ) );
        comments.add( new Comment( "test comment two", 0, 0 ) );

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
