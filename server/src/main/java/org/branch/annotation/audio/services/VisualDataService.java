package org.branch.annotation.audio.services;

import org.branch.annotation.audio.model.VisualData;
import org.branch.annotation.audio.model.VisualParameters;

/**
 */
public interface VisualDataService
{
    /**
     * Retrieves all data need to display the wave form for an {@link org.branch.annotation.audio.model.jpa.IndexSamples object} specified by the {@code uid}.
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
    VisualData loadVisualData(String uid, VisualParameters visualParameters);
}
