package org.branch.annotation.audio.services;

import org.branch.annotation.audio.api.PersistenceEngine;
import org.branch.annotation.audio.model.Sample;
import org.branch.annotation.audio.model.VisualData;
import org.branch.annotation.audio.model.VisualParameters;
import org.branch.annotation.audio.model.VisualRegion;
import org.branch.annotation.audio.model.jpa.Annotation;
import org.branch.annotation.audio.model.jpa.IndexSamples;
import org.branch.annotation.audio.model.jpa.IndexSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: tbeauvais
 * Date: 3/6/12
 * Time: 3:25 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class DefaultAnnotationService implements AnnotationService
{

    private Map<String, IndexSamples> cache = new HashMap<String, IndexSamples>();

    public DefaultAnnotationService()
    {
    }

    private PersistenceEngine persistenceEngine;

    @Autowired
    public void setPersistenceEngine(PersistenceEngine persistenceEngine)
    {
        this.persistenceEngine = persistenceEngine;
    }

    public IndexSummary[] loadAll()
    {
        return persistenceEngine.loadAll(IndexSummary.class);
    }

    public IndexSamples loadSamples(String indexFileUid)
    {
        return persistenceEngine.load(indexFileUid, IndexSamples.class);
    }

    public void save(IndexSummary indexSummary)
    {
        persistenceEngine.save(indexSummary);
    }

    @Override
    public void deleteAnnotation(String uid)
    {
        persistenceEngine.delete(Annotation.class, uid);
    }

    @Override
    public Annotation loadAnnotation(String idAnnotation)
    {
        return persistenceEngine.load(idAnnotation, Annotation.class);
    }

    public VisualData loadVisualData(String idContentFile, VisualParameters visualParameters)
    {
        final VisualData visualData = new VisualData();

        final IndexSamples indexSamples = (IndexSamples) loadIndexSummary(idContentFile);

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
        final List<Annotation> annotations = indexSamples.getAnnotations();
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

            visualRegions.add(new VisualRegion(annotation.getUid(), startX, endX, 0, 0, 0));
        }

        visualData.setVisualRegions(visualRegions.toArray(new VisualRegion[0]));

        return visualData;
    }

    public IndexSummary loadIndexSummary(String uid)
    {
        return persistenceEngine.load(uid, IndexSummary.class);
    }
}
