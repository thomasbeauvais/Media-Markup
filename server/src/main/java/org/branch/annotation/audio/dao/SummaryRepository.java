package org.branch.annotation.audio.dao;

import org.branch.annotation.audio.model.dao.Summary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SummaryRepository extends JpaRepository<Summary, String>
{

}
