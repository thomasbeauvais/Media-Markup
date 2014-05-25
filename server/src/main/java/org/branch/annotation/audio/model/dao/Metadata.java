package org.branch.annotation.audio.model.dao;

import org.branch.common.data.Identifiable;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Metadata extends UuidIdentifiable implements Identifiable<String>
{
    private String targetUuid;

    @ElementCollection
    @MapKeyColumn(name="key")
    @Column(name="value")
    @CollectionTable(name="metadata_values", joinColumns=@JoinColumn(name="metadata_value_id"))
    private Map<String, String> metadataValues = new HashMap<String, String>();

    public Metadata()
    {
    }

    public Metadata(String uuid, Map<String, String> metadataMap)
    {
        this.targetUuid = uuid;
        this.metadataValues = metadataMap;
    }

    public void setTargetUuid(String targetUuid)
    {
        this.targetUuid = targetUuid;
    }

    public void setMetadataValues(Map<String, String> metadataValues)
    {
        this.metadataValues = metadataValues;
    }

    public String getTargetUuid()
    {
        return targetUuid;
    }

    public Map<String, String> getMetadataValues()
    {
        return metadataValues;
    }
}
