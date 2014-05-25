package org.branch.annotation.audio.model.dao;

import org.branch.annotation.audio.model.Sample;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

/**
 * Heavy weight representation of the {@link Summary} that also includes the {@link org.branch.annotation.audio.model.Sample samples}.
 */
@Entity
public class Samples extends UuidIdentifiable
{
    @Lob
    @ElementCollection
    private List<Sample> samples = new Vector<Sample>();

    private short min;
    private short max;
    private float time;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Summary summary;

    public Samples()
    {

    }

    public short getMax()
    {
        return this.max;
    }

    public short getMin()
    {
        return this.min;
    }

    public void setMin(short min)
    {
        this.min = min;
    }

    public void setMax(short max)
    {
        this.max = max;
    }

    public Sample[] getSamples()
    {
        return this.samples.toArray(new Sample[this.samples.size()]);
    }

    public void setSamples(Sample[] samples)
    {
        this.samples = new ArrayList<Sample>(Arrays.asList(samples));
    }

    public int size()
    {
        return samples.size();
    }

    public void updateBoundsForValue(Sample sample)
    {
        short value = sample.getValue();

        this.max = (short) Math.max(value, this.max);
        this.min = (short) Math.min(value, this.min);
    }

    public void addSample(final Sample sample)
    {
        addSample(sample, false);
    }

    public void addSample(final Sample sample, final boolean update)
    {
        this.samples.add(sample);

        if (update)
        {
            updateBoundsForValue(sample);
        }
    }

    public void updateBounds()
    {
        for (Sample sample : samples)
        {
            updateBoundsForValue(sample);
        }
    }

    public void setTime(float time)
    {
        this.time = time;
    }

    public float getTime()
    {
        return time;
    }

    public Summary getSummary()
    {
        return summary;
    }

    public void setSummary(Summary summary)
    {
        this.summary = summary;
    }
}
