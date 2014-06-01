package org.branch.annotation.audio.services;

import org.branch.annotation.audio.model.VisualData;
import org.branch.annotation.audio.model.VisualParameters;
import org.springframework.transaction.annotation.Transactional;

/**
 */
public interface VisualDataService
{
    @Transactional
    VisualData loadAllVisualData(String summaryId);

    /**
     * Retrieves all data need to display the wave form for an {@link org.branch.annotation.audio.model.dao.Samples object} specified by the {@code uid}.
     * <p/>
     * The provided {@link org.branch.annotation.audio.model.VisualParameters visualParameters} will dictate certain return values such as resolution.  Parameters such as the
     * height and width dictate what data will be returned.  Perhaps more logic will be placed in the client to avoid round trips to the service.
     *
     * @param uid
     * @param visualParameters
     * @return
     *
     * @see org.branch.annotation.audio.model.VisualParameters
     * @see org.branch.annotation.audio.model.VisualData
     */
    @Transactional
    VisualData loadVisualData(String uid, VisualParameters visualParameters);
}
