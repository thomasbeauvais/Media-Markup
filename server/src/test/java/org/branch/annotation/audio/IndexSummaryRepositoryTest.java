package org.branch.annotation.audio;

import com.mysema.query.jpa.impl.JPAQuery;
import org.branch.annotation.audio.jpa.IndexSummaryRepository;
import org.branch.annotation.audio.model.jpa.Comment;
import org.branch.annotation.audio.model.jpa.IndexSamples;
import org.branch.annotation.audio.model.jpa.IndexSummary;
import org.branch.annotation.audio.model.jpa.QIndexSamples;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Vector;

import static org.junit.Assert.*;

/**
 * TODO:  Please document properly all classes and methods using the Silbury JavaDoc Guidelines
 * TODO:    provided on the Silbury Confluence under Silbury JavaDoc Standards Guidelines.
 *
 * @author Silbury Solutions, Deutschland - Thomas Beauvais (thomas.beauvais@silbury.de)
 * @since 25.05.14
 */
public class IndexSummaryRepositoryTest extends AbstractSpringTest
{
    @Autowired
    IndexSummaryRepository<IndexSummary> indexSummaryRepository;

    @Autowired
    EntityManager entityManager;

    private String uid;

    @Before
    public void preConditions()
    {
        assertNotNull(indexSummaryRepository);
    }

    @Test
    public void save()
    {
        final String expected_name = "test summary one";
        final String expected_comment0 = "test comment one";
        final String expected_comment1 = "test comment two";

        final IndexSamples local = new IndexSamples();
        local.setName(expected_name);

        // create some nested objects - in the case, org.branch.annotation.audio.model.jpa.Comment
        final List<Comment> comments = new Vector<Comment>();
        comments.add(new Comment(expected_comment0, 1, 100));
        comments.add(new Comment(expected_comment1, 2, 200));

        local.setComments(comments);

        uid = indexSummaryRepository.save(local).getUid();

        assertNotNull(uid);

        final IndexSamples persisted = getPersisted();

        assertNotNull(persisted);
        assertNotNull(persisted.getName());
        assertEquals(expected_name, persisted.getName());

        assertNotNull(persisted.getComments());
        assertEquals(2, persisted.getComments().size());

        final Comment comment0 = persisted.getComments().get(0);
        assertNotNull(comment0);
        assertEquals(expected_comment0, comment0.getText());
        assertEquals(1, comment0.getStartPos());
        assertEquals(100, comment0.getEndPos());

        final Comment comment1 = persisted.getComments().get(1);
        assertNotNull(comment1);
        assertEquals(expected_comment1, comment1.getText());
        assertEquals(2, comment1.getStartPos());
        assertEquals(200, comment1.getEndPos());
    }

    @Test
    public void delete()
    {
        save();

        final IndexSamples persisted = getPersisted();

        indexSummaryRepository.delete(persisted);

        final IndexSummary expected = indexSummaryRepository.findOne(uid);

        assertNull("IndexSamples should have been deleted", expected);
    }

    @Test
    @Ignore("Failing assertion, even on flush.")
    public void update()
    {
        save();

        final String expected_name = "modified";

        final IndexSummary persisted = indexSummaryRepository.findOne(uid);

        assertNotNull(persisted);

        persisted.setName(expected_name);

        indexSummaryRepository.save(persisted);

        final IndexSamples modified = getPersisted();

        assertEquals(expected_name, modified.getName());
    }

    public IndexSamples getPersisted()
    {
        // use a query to bypass the jpa repository
        final QIndexSamples indexSamples = QIndexSamples.indexSamples;
        final JPAQuery query = new JPAQuery(entityManager);
        final IndexSamples persisted = query.from(indexSamples)
                                            .where(indexSamples.uid.eq(uid))
                                            .uniqueResult(indexSamples);

        assertNotNull(persisted);

        return persisted;
    }
}
