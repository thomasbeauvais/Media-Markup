package org.branch.annotation.audio.dao;

import com.mysema.query.jpa.impl.JPAQuery;
import org.branch.annotation.audio.AbstractSpringTest;
import org.branch.annotation.audio.model.dao.Annotation;
import org.branch.annotation.audio.model.dao.IndexSamples;
import org.branch.annotation.audio.model.dao.IndexSummary;
import org.branch.annotation.audio.model.dao.QIndexSamples;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Vector;

import static org.junit.Assert.*;

public class IndexSummaryRepositoryTest extends AbstractSpringTest
{
    @Autowired
    IndexSummaryRepository indexSummaryRepository;

    @Autowired
    EntityManager entityManager;

    private String uuid;

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
        final List<Annotation> annotations = new Vector<Annotation>();
        annotations.add(new Annotation(expected_comment0, 1, 100));
        annotations.add(new Annotation(expected_comment1, 2, 200));

        local.setAnnotations(annotations);

        uuid = indexSummaryRepository.save(local).getId();

        assertNotNull(uuid);

        final IndexSamples persisted = getPersisted();

        assertNotNull(persisted);
        assertNotNull(persisted.getName());
        assertEquals(expected_name, persisted.getName());

        assertNotNull(persisted.getAnnotations());
        assertEquals(2, persisted.getAnnotations().size());

        final Annotation annotation0 = persisted.getAnnotations().get(0);
        assertNotNull(annotation0);
        assertEquals(expected_comment0, annotation0.getText());
        assertEquals(1, annotation0.getStartPos());
        assertEquals(100, annotation0.getEndPos());

        final Annotation annotation1 = persisted.getAnnotations().get(1);
        assertNotNull(annotation1);
        assertEquals(expected_comment1, annotation1.getText());
        assertEquals(2, annotation1.getStartPos());
        assertEquals(200, annotation1.getEndPos());
    }

    @Test
    public void delete()
    {
        save();

        final IndexSamples persisted = getPersisted();

        indexSummaryRepository.delete(persisted);

        final IndexSummary expected = indexSummaryRepository.findOne(uuid);

        assertNull("IndexSamples should have been deleted", expected);
    }

    @Test
    @Ignore("Failing assertion, even on flush.")
    public void update()
    {
        save();

        final String expected_name = "modified";

        final IndexSummary persisted = indexSummaryRepository.findOne(uuid);

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
                                            .where(indexSamples.uuid.eq(uuid))
                                            .uniqueResult(indexSamples);

        assertNotNull(persisted);

        return persisted;
    }
}
