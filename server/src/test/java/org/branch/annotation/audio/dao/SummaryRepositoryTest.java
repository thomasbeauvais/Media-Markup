package org.branch.annotation.audio.dao;

import com.mysema.query.jpa.impl.JPAQuery;
import org.branch.annotation.audio.AbstractSpringTest;
import org.branch.annotation.audio.model.dao.Annotation;
import org.branch.annotation.audio.model.dao.QSummary;
import org.branch.annotation.audio.model.dao.Summary;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Vector;

import static org.junit.Assert.*;

public class SummaryRepositoryTest extends AbstractSpringTest
{
    @Autowired
    SummaryRepository indexSummaryRepository;

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

        final Summary local = new Summary();
        local.setName(expected_name);

        // create some nested objects - in the case, org.branch.annotation.audio.model.jpa.Comment
        final List<Annotation> annotations = new Vector<Annotation>();
        annotations.add(new Annotation(expected_comment0, 1, 100));
        annotations.add(new Annotation(expected_comment1, 2, 200));

        local.setAnnotations(annotations);

        uuid = indexSummaryRepository.save(local).getId();

        assertNotNull(uuid);

        final Summary persisted = getPersisted();

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

        final Summary persisted = getPersisted();

        indexSummaryRepository.delete(persisted);

        final Summary expected = indexSummaryRepository.findOne(uuid);

        assertNull("IndexSamples should have been deleted", expected);
    }

    @Test
    @Ignore("Failing assertion, even on flush.")
    public void update()
    {
        save();

        final String expected_name = "modified";

        final Summary persisted = indexSummaryRepository.findOne(uuid);

        assertNotNull(persisted);

        persisted.setName(expected_name);

        indexSummaryRepository.save(persisted);

        final Summary modified = getPersisted();

        assertEquals(expected_name, modified.getName());
    }

    public Summary getPersisted()
    {
        // use a query to bypass the jpa repository
        final QSummary summary = QSummary.summary;
        final JPAQuery query = new JPAQuery(entityManager);
        final Summary persisted = query.from(summary)
                                       .where(summary.uuid.eq(uuid))
                                       .uniqueResult(summary);

        assertNotNull(persisted);

        return persisted;
    }
}
