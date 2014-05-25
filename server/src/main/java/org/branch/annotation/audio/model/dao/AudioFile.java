package org.branch.annotation.audio.model.dao;

import org.branch.common.data.Identifiable;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 *
 */
@Entity
public class AudioFile implements Identifiable {
    @Lob
    private byte[] bytes;

    @Id
    @GeneratedValue( generator= "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
