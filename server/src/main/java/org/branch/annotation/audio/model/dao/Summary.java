package org.branch.annotation.audio.model.dao;

import org.branch.common.data.Identifiable;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: tbeauvais
 * Date: 11/5/11
 * Time: 10:30 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Summary extends UuidIdentifiable implements Identifiable
{
    private float time;
    private long dateUploaded;
    private String name;
    private String description;

    private long size;

    private String audioFileUid;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Annotation> annotations;

    public Summary()
    {

    }

    public Summary(String uuid)
    {
        setId(uuid);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public float getTime()
    {
        return time;
    }

    public void setTime(float time)
    {
        this.time = time;
    }

    public Date getDateUploaded()
    {
        return new Date(dateUploaded);
    }

    public void setDateUploaded(long dateUploaded)
    {
        this.dateUploaded = dateUploaded;
    }

    public void setDateUploaded(@NotNull Date dateUploaded)
    {
        this.dateUploaded = dateUploaded.getTime();
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setAudioFileUid(String audioFileUid)
    {
        this.audioFileUid = audioFileUid;
    }

    public String getAudioFileUid()
    {
        return audioFileUid;
    }

    public void setAnnotations(List<Annotation> annotations)
    {
        this.annotations = annotations;
    }

    public List<Annotation> getAnnotations()
    {
        return annotations;
    }

    public void setSize(long size)
    {
        this.size = size;
    }

    public long getSize()
    {
        return size;
    }
}
