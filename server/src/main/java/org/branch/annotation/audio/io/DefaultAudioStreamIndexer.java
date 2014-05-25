package org.branch.annotation.audio.io;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.SampleBuffer;
import org.apache.log4j.Logger;
import org.branch.annotation.audio.model.Sample;
import org.branch.annotation.audio.model.dao.IndexSamples;
import org.branch.common.utils.HumanReadable;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;

/**
 *
 */
public class DefaultAudioStreamIndexer implements AudioStreamIndexer
{
    private static final Logger logger = Logger.getLogger(AudioStreamIndexer.class);

    public IndexSamples createIndex(@NotNull InputStream input)
    {

        final long start = System.currentTimeMillis();

        try
        {
            final Bitstream bitstream = new Bitstream(input);
            final Decoder decoder = new Decoder();
            final IndexSamples index = new IndexSamples();

            float timeStamp = 0;
            long pos = 0;
            Header h = null;
            SampleBuffer output = null;
            short[] samples = null;

            while ((h = bitstream.readFrame()) != null)
            {
                pos = bitstream.pos();
                output = (SampleBuffer) decoder.decodeFrame(h, bitstream);

                samples = getSamples(output.getBuffer(), output.getChannelCount());

                index.addSample(new Sample(timeStamp, pos, samples[0]), false);

                timeStamp += ((float) output.getBufferLength()) / output.getChannelCount() / output.getSampleFrequency();
                bitstream.closeFrame();
            }

            index.setTime(timeStamp);
            index.setChannels(output.getChannelCount());
            index.updateBounds();

            final long end = System.currentTimeMillis();

            logger.info("Audio stream of size(" + HumanReadable.convertBytes(pos) + ") indexed in " + (end - start) / 1000. + " seconds.");

            return index;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private short[] getSamples(short[] buffer, int numChannels)
    {
        short[] shorts = new short[numChannels];
        for (int i = 0; i < numChannels; i++)
        {
            shorts[i] = buffer[i];
        }

        return shorts;
    }

}