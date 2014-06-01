package org.branch.annotation.audio.services;

import org.branch.annotation.audio.dao.AnnotationRepository;
import org.branch.annotation.audio.dao.SamplesRepository;
import org.branch.annotation.audio.model.Sample;
import org.branch.annotation.audio.model.VisualData;
import org.branch.annotation.audio.model.VisualParameters;
import org.branch.annotation.audio.model.VisualRegion;
import org.branch.annotation.audio.model.dao.Annotation;
import org.branch.annotation.audio.model.dao.Samples;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Vector;

@Service
public class DefaultVisualDataService implements VisualDataService
{
    @Autowired
    private SamplesRepository samplesRepository;

    @Autowired
    private AnnotationRepository annotationRepository;

    @Override
    @Transactional
    public VisualData loadAllVisualData(String summaryId)
    {
        final Samples indexSamples = samplesRepository.findBySummaryId(summaryId);
        if (indexSamples == null)
        {
            throw new RuntimeException("Couldn't find samples entity for summary: " + summaryId);
        }

        if (indexSamples.getSamples() == null)
        {
            throw new RuntimeException("Found samples entity but it had no sample data: " + indexSamples.getId());
        }

        final VisualData visualData = new VisualData();

        final Sample[] samples = indexSamples.getSamples();

        final long[] visualPositions = new long[samples.length];
        final float[] visualSamples = new float[samples.length];

        for (int i = 0; i < samples.length; i++)
        {
            final Sample sample = samples[i];

            visualPositions[i] = samples[i].getPosition();
            visualSamples[i] = ((float) sample.getValue() / Short.MAX_VALUE);
        }

        visualData.setVisualPositions(visualPositions);
        visualData.setVisualSamples(visualSamples);

        final List<VisualRegion> visualRegions = new Vector<VisualRegion>();
        visualData.setVisualRegions(visualRegions.toArray(new VisualRegion[visualRegions.size()]));

        return visualData;
    }

    @Override
    @Transactional
    public VisualData loadVisualData(String summaryId, VisualParameters visualParameters)
    {
        final Samples indexSamples = samplesRepository.findBySummaryId(summaryId);
        if (indexSamples == null)
        {
            throw new RuntimeException("Couldn't find samples entity for summary: " + summaryId);
        }

        if (indexSamples.getSamples() == null)
        {
            throw new RuntimeException("Found samples entity but it had no sample data: " + indexSamples.getId());
        }

        final VisualData visualData = new VisualData();

        final int width = visualParameters.getWidth();
        final int zoom = visualParameters.getZoom();

        final long[] visualPositions = new long[width];
        final float[] visualSamples = new float[width];

        final Sample[] samples = indexSamples.getSamples();

        // the zoom and start position determine the samples that need to be loaded
        final double scale = 1d / zoom;
        final int set = Math.max(width, (int) (samples.length * scale));

        final int center = set / 2;
        final int start = set / 2 - center;

        final int step = set / width;

        for (int i = start, x = 0; x < width && i < samples.length; x++)
        {
            int max = 0;
            int min = 0;

            visualPositions[x] = samples[i].getPosition();

            for (int s = 0; s < step && i < samples.length; s++, i++)
            {
                max = Math.max(max, samples[i].getValue());
                min = Math.min(min, samples[i].getValue());
            }

            visualPositions[x] = samples[i].getPosition();
            visualSamples[x] = ((float) (max + (min * -1))) / Short.MAX_VALUE;
//            visualSamples[x] = ((float)(max + (min * -1))) / max;
        }

        final List<VisualRegion> visualRegions = new Vector<VisualRegion>();
        final List<Annotation> annotations = annotationRepository.findAllForSummary(indexSamples.getSummary().getId());
        for (Annotation annotation : annotations)
        {
            int startX = 0;
            int endX = 0;
            for (int i = 0, samplePositionsLength = visualPositions.length; i < samplePositionsLength; i++)
            {
                long samplePosition = visualPositions[i];
                if (startX == 0 && annotation.getStartPos() < samplePosition)
                {
                    startX = i;
                }
                else if (endX == 0 && annotation.getEndPos() < samplePosition)
                {
                    endX = i;
                }
            }

            // If the end was never smaller, then it went to the entire end.. strange I know.. but a possible case
            if (endX == 0)
            {
                endX = width;
            }

            visualRegions.add(new VisualRegion(annotation.getId(), startX, endX, 0, 0, 0));
        }


        visualData.setVisualSamples(visualSamples);
        visualData.setVisualPositions(visualPositions);
        visualData.setVisualRegions(visualRegions.toArray(new VisualRegion[visualRegions.size()]));

        return visualData;
    }
}
