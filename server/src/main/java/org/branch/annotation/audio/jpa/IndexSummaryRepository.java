package org.branch.annotation.audio.jpa;

import org.branch.annotation.audio.model.jpa.IndexSummary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndexSummaryRepository extends JpaRepository<IndexSummary, String>
{

}
