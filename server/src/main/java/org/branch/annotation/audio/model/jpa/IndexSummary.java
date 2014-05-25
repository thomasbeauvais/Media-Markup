package org.branch.annotation.audio.model.jpa;

import org.branch.common.data.Identifiable;
import org.hibernate.annotations.GenericGenerator;
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
public class IndexSummary implements Identifiable
{
    private int numChannels;
    private float time;
    private long dateUploaded;
    private String name;
    private String description;
    private List<Annotation> annotations = new Vector<Annotation>();

    private String uid;

    private String audioFileUid;
    private String originalFilename;

    public IndexSummary()
    {

    }

    public IndexSummary(String name)
    {
        this.name = name;
    }

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    public String getUid()
    {
        return this.uid;
    }

    public void setUid(String uid)
    {
        this.uid = uid;
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<Annotation> getAnnotations()
    {
        return annotations;
    }

    public void setAnnotations(List<Annotation> annotations)
    {
        this.annotations = annotations;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        IndexSummary that = (IndexSummary) o;

        if (uid != null ? !uid.equals(that.uid) : that.uid != null)
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        return uid != null ? uid.hashCode() : 0;
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
