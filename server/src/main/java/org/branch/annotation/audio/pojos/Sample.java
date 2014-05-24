package org.branch.annotation.audio.pojos;
import java.io.Serializable;

public class Sample implements Serializable {

	private long pos;
    private float timeStamp;
    private short value;

    public Sample() {

    }

	public Sample(float timeStamp, long pos, short shortValue) {
		this.timeStamp = timeStamp;
		this.pos = pos;
		this.value = shortValue;
	}

	public long getPosition() {
		return pos;
	}

	public void setPosition(long pos) {
		this.pos = pos;
	}

	public float getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(float timeStamp) {
		this.timeStamp = timeStamp;
	}

    public short getValue() {
        return this.value;
    }

	public void setValue(short shortValue) {
		this.value = shortValue;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sample sample = (Sample) o;

        if (value != sample.value) return false;
        if (pos != sample.pos) return false;
        if (Float.compare(sample.timeStamp, timeStamp) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) value;
        result = 31 * result + (int) ( pos ^ ( pos >>> 32 ) );
        result = 31 * result + ( timeStamp != +0.0f ? Float.floatToIntBits( timeStamp ) : 0 );
        return result;
    }
}
