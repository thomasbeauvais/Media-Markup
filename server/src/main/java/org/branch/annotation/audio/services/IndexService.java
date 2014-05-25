package org.branch.annotation.audio.services;

import org.branch.annotation.audio.model.jpa.IndexSummary;

/**
 */
public interface IndexService
{
    /**
     * Retrieves all light weight {@link org.branch.annotation.audio.model.jpa.IndexSummary objects that are available for the current user}.
     *
     * @return
     */
    IndexSummary[] loadAll();

    /**
     *
     * @param uid
     * @return
     */
    IndexSummary loadIndexSummary(String uid);

    /**
     *
     * @param indexSummary
     */
    void save(IndexSummary indexSummary);
}
