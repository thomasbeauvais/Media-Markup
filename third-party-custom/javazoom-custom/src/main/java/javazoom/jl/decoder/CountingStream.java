package javazoom.jl.decoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

public class CountingStream extends PushbackInputStream {

	private long count = 0;

	public CountingStream(InputStream in) {
		super(in);
	}

	public CountingStream(InputStream in, int size) {
		super(in, size);
	}

	public synchronized int read() throws IOException {
		count ++;
		return super.read();
	}

	public synchronized int read(byte[] b, int off, int len) throws 
	IOException {

		int c= super.read(b, off, len);
		count += c;
		return c;
	}

	public void unread(byte[] b, int off, int len) throws IOException {
		count -= len;
		super.unread(b, off, len);
	}

	public void unread(int b) throws IOException {
		count--;
		super.unread(b);
	}

	public synchronized long skip(long n) throws IOException {
		count += n;
		super.skip(n);
		return n;
	}

	public long getCount() {
		return count;
	}
}
