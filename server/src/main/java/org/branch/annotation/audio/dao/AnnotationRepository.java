package org.branch.annotation.audio.dao;

import org.branch.annotation.audio.model.dao.Annotation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnotationRepository extends JpaRepository<Annotation, String>
{

}
