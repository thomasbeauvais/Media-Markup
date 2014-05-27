package org.branch.annotation.audio.dao;

import org.branch.annotation.audio.model.dao.Samples;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

@RepositoryRestResource(path = "samples")
public interface SamplesRepository extends JpaRepository<Samples, String>
{

    @Transactional
    @Query("select samples from Samples samples where samples.summary.uuid = ?1")
    Samples findSamplesForSummary(String summaryId);
}
