package org.branch.annotation.audio.model.dao;

import org.branch.common.data.Identifiable;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: tbeauvais
 * Date: 11/5/11
 * Time: 10:30 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class IndexSummary extends UuidIdentifiable implements Identifiable
{
    private int numChannels;
    private float time;
    private long dateUploaded;
    private String name;
    private String description;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ElementCollection(targetClass = Annotation.class)
    private List<Annotation> annotations = new Vector<Annotation>();

    private String audioFileUid;
    private String originalFilename;

    public IndexSummary()
    {

    }

    public IndexSummary(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setChannels(int numChannels)
    {
        this.numChannels = numChannels;
    }

    public int getNumChannels()
    {
        return numChannels;
    }

    public void setNumChannels(int numChannels)
    {
        this.numChannels = numChannels;
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

    public List<Annotation> getAnnotations()
    {
        return annotations;
    }

    public void setAnnotations(List<Annotation> annotations)
    {
        this.annotations = annotations;
    }

    public void setAudioFileUid(String audioFileUid)
    {
        this.audioFileUid = audioFileUid;
    }

    public String getAudioFileUid()
    {
        return audioFileUid;
    }

    public void setOriginalFilename(String originalFilename)
    {
        this.originalFilename = originalFilename;
    }

    public String getOriginalFilename()
    {
        return originalFilename;
    }
}
