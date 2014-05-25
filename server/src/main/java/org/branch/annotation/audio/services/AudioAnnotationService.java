package org.branch.annotation.audio.services;

import org.branch.annotation.audio.model.jpa.Comment;
import org.branch.annotation.audio.model.jpa.IndexSummary;
import org.branch.annotation.audio.model.VisualData;
import org.branch.annotation.audio.model.VisualParameters;

/**
 * Created with IntelliJ IDEA.
 * User: tbeauvais
 * Date: 7/4/12
 * Time: 1:26 AM
 * To change this template use File | Settings | File Templates.
 */
public interface AudioAnnotationService
{
    VisualData loadVisualData(String idIndexFile, VisualParameters visualParameters);

    IndexSummary[] loadAll();

    IndexSummary loadIndexSummary(String uid);

    void save( IndexSummary indexSummary );

    void deleteAnnotation(String uid);

    Comment loadAnnotation(String idAnnotation);
}
