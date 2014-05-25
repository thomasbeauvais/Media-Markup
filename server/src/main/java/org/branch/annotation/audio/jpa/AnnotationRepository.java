package org.branch.annotation.audio.jpa;

import org.branch.annotation.audio.model.jpa.Annotation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnotationRepository extends JpaRepository<Annotation, String>
{

}
