package org.branch.annotation.audio.model.dao;

import org.branch.common.data.Identifiable;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: tbeauvais
 * Date: 11/5/11
 * Time: 10:30 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class IndexSummary extends UuidIdentifiable implements Identifiable
{
    private int numChannels;
    private float time;
    private long dateUploaded;
    private String name;
    private String description;

    private String audioFileUid;

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

    public void setAudioFileUid(String audioFileUid)
    {
        this.audioFileUid = audioFileUid;
    }

    public String getAudioFileUid()
    {
        return audioFileUid;
    }
}
