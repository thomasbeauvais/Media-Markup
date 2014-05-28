package org.branch.annotation.audio.model;
import java.io.Serializable;

public class Sample implements Serializable {

	private long position;
    private float timeStamp;
    private short value;

    public Sample() {

    }

	public Sample(float timeStamp, long position, short shortValue) {
		this.timeStamp = timeStamp;
		this.position = position;
		this.value = shortValue;
	}

	public long getPosition() {
		return position;
	}

	public void setPosition(long pos) {
		this.position = pos;
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
}
