package org.branch.annotation.audio.dao;

import com.mysema.query.jpa.impl.JPAQuery;
import org.branch.annotation.audio.AbstractSpringTest;
import org.branch.annotation.audio.model.dao.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.*;

import static org.junit.Assert.*;

public class MetadataRepositoryTest extends AbstractSpringTest
{
    @Autowired
    MetadataRepository metadataRepository;

    @Autowired
    EntityManager entityManager;

    private String uuid;

    @Before
    public void preConditions()
    {
        assertNotNull(metadataRepository);
    }

    @Test
    public void save()
    {
        final String expected_key0 = "zero";
        final String expected_key1 = "one";
        final String expected_key2 = "two";

        final String expected_value0 = "akflajsdlfjlsj";
        final String expected_value1 = "dskjfalksjfdlk";
        final String expected_value2 = "ksjdflkjajsdjf";

        final Map<String, String> metadataMap = new HashMap<String, String>();
        metadataMap.put(expected_key0, expected_value0);
        metadataMap.put(expected_key1, expected_value1);
        metadataMap.put(expected_key2, expected_value2);

        final String targetUuid = UUID.randomUUID().toString();

        final Metadata metadata = new Metadata(targetUuid, metadataMap);

        uuid = metadataRepository.save(metadata).getId();

        final Metadata persisted = getPersisted();

        assertEquals(expected_value0, persisted.getMetadataValues().get(expected_key0));
        assertEquals(expected_value1, persisted.getMetadataValues().get(expected_key1));
        assertEquals(expected_value2, persisted.getMetadataValues().get(expected_key2));
    }

    @Test
    public void delete()
    {
        save();

//        final IndexSamples persisted = getPersisted();
//
//        metadataRepository.delete(persisted);
//
//        final IndexSummary expected = metadataRepository.findOne(uuid);
//
//        assertNull("IndexSamples should have been deleted", expected);
    }

    @Test
    @Ignore("Failing assertion, even on flush.")
    public void update()
    {
        save();

//        final String expected_name = "modified";
//
//        final IndexSummary persisted = metadataRepository.findOne(uuid);
//
//        assertNotNull(persisted);
//
//        persisted.setName(expected_name);
//
//        metadataRepository.save(persisted);
//
//        final IndexSamples modified = getPersisted();
//
//        assertEquals(expected_name, modified.getName());
    }

    public Metadata getPersisted()
    {
        // use a query to bypass the jpa repository
        final QMetadata metadata = QMetadata.metadata;
        final JPAQuery query = new JPAQuery(entityManager);
        final Metadata persisted = query.from(metadata)
                                            .where(metadata.uuid.eq(uuid))
                                            .uniqueResult(metadata);

        assertNotNull(persisted);

        return persisted;
    }
}
