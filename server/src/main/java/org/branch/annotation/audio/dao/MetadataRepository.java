package org.branch.annotation.audio.dao;

import org.branch.annotation.audio.model.dao.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "metadata")
public interface MetadataRepository extends JpaRepository<Metadata, String>
{
}
