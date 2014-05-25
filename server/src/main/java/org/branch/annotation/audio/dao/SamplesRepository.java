package org.branch.annotation.audio.dao;

import org.branch.annotation.audio.model.dao.Samples;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "samples")
public interface SamplesRepository extends JpaRepository<Samples, String>
{

}
