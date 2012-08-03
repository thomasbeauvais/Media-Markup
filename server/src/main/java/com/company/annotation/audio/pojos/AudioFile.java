package com.company.annotation.audio.pojos;

import com.company.common.dao.Identifiable;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 * Created with IntelliJ IDEA.
 * User: tbeauvais
 * Date: 8/3/12
 * Time: 3:27 PM
 * To change this template use File | Settings | File Templates.
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
