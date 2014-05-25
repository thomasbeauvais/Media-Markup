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

import java.util.List;
import java.util.Vector;

@Service
public class DefaultVisualDataService implements VisualDataService
{
    @Autowired
    private SamplesRepository samplesRepository;

    @Autowired
    private AnnotationRepository annotationRepository;

    public VisualData loadVisualData(String id, VisualParameters visualParameters)
    {
        final Samples indexSamples = samplesRepository.findOne(id);

        final VisualData visualData = new VisualData();

        final int width = visualParameters.getWidth();
        final int[] visualSamples = new int[width];
        final long[] samplePositions = new long[width];

        visualData.setVisualSamples(visualSamples);
        visualData.setVisualPositions(samplePositions);

        if (indexSamples != null && indexSamples.getSamples() != null)
        {
            Sample[] sampleArray = indexSamples.getSamples();

            int step = sampleArray.length / width;
            double scale = indexSamples.getMax() / visualParameters.getHeight();

            for (int i = 0, pixelX = 0; pixelX < width && i < sampleArray.length; pixelX++)
            {
                int max = 0;
                int min = 0;

                samplePositions[pixelX] = sampleArray[i].getPosition();

                for (int s = 0; s < step && i < sampleArray.length; s++, i++)
                {
                    max = Math.max(max, sampleArray[i].getValue());
                    min = Math.min(min, sampleArray[i].getValue());
                }

                if (i >= sampleArray.length)
                {
                    System.out.print("");
                }

                int value = (int) ((max + (min * -1)) / scale);
                int y = value / 2;

                visualSamples[pixelX] = y;
            }
        }

        final List<VisualRegion> visualRegions = new Vector<VisualRegion>();
        final List<Annotation> annotations = annotationRepository.findAllForSummary(indexSamples.getSummary().getId());
        for (Annotation annotation : annotations)
        {
            int startX = 0;
            int endX = 0;
            for (int i = 0, samplePositionsLength = samplePositions.length; i < samplePositionsLength; i++)
            {
                long samplePosition = samplePositions[i];
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

        visualData.setVisualRegions(visualRegions.toArray(new VisualRegion[visualRegions.size()]));

        return visualData;
    }
}
