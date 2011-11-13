package com.company.common.pojos;
import java.io.Serializable;
import java.util.Arrays;

public class Sample implements Serializable {

	private short[] samples;
	private long pos;
	private float timeStamp;

	public Sample(float timeStamp, long pos, short[] samples) {
		this.timeStamp = timeStamp;
		this.pos = pos;
		this.samples = samples;
	}

	public short[] getSamples() {
		return this.samples;
	}

	public long getPosition() {
		return pos;
	}

	public void setPos(long pos) {
		this.pos = pos;
	}

	public float getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(float timeStamp) {
		this.timeStamp = timeStamp;
	}

	public void setSamples(short[] samples) {
		this.samples = samples;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sample sample = (Sample) o;

        if (pos != sample.pos) return false;
        if (Float.compare(sample.timeStamp, timeStamp) != 0) return false;
        if (!Arrays.equals(samples, sample.samples)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = samples != null ? Arrays.hashCode(samples) : 0;
        result = 31 * result + (int) (pos ^ (pos >>> 32));
        result = 31 * result + (timeStamp != +0.0f ? Float.floatToIntBits(timeStamp) : 0);
        return result;
    }
}
