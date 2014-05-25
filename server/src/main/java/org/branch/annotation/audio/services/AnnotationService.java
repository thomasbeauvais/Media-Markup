package org.branch.annotation.audio.services;

import org.branch.annotation.audio.model.jpa.Annotation;

/**
 */
public interface AnnotationService
{
    void deleteAnnotation(String uid);

    Annotation loadAnnotation(String uid);
}
