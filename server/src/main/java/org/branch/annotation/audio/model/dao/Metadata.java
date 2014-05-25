package org.branch.annotation.audio.model.dao;

import org.branch.common.data.Identifiable;

import javax.persistence.Entity;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Metadata extends UuidIdentifiable implements Identifiable<String>
{
    private String targetUuid;

    private Map<String, Object> metadataValues = new HashMap<String, Object>();

    public Metadata()
    {
    }

    public Metadata(String uuid, Map<String, Object> metadataMap)
    {
        this.targetUuid = uuid;
        this.metadataValues = metadataMap;
    }

    public String getTargetUuid()
    {
        return targetUuid;
    }

    public Map<String, Object> getMetadataValues()
    {
        return metadataValues;
    }
}
