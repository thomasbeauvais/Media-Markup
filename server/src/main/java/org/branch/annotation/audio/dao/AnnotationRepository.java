package org.branch.annotation.audio.dao;

import org.branch.annotation.audio.model.dao.Annotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnnotationRepository extends JpaRepository<Annotation, String>
{
    @Query("select annotation from Annotation annotation where annotation.summary.uuid = ?1")
    List<Annotation> findAllForSummary(String id);
}
