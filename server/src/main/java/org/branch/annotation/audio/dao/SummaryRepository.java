package org.branch.annotation.audio.dao;

import org.branch.annotation.audio.model.dao.Summary;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "summary")
public interface SummaryRepository extends PagingAndSortingRepository<Summary, String>
{

}
