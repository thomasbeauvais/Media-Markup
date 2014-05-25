package org.branch.annotation.audio.model.dao;

import org.branch.common.data.Identifiable;

import javax.persistence.Entity;
import javax.persistence.Lob;

/**
 * Used to store files in a JPA enabled database.
 *
 * @deprecated This isn't recommended.  A file store service such as S3 is the preferred file storage method.
 */
@Entity
@Deprecated
public class AudioFile extends UuidIdentifiable implements Identifiable
{
    @Lob
    private byte[] bytes;

    public byte[] getBytes()
    {
        return bytes;
    }

    public void setBytes(byte[] bytes)
    {
        this.bytes = bytes;
    }
}
