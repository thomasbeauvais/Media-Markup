package com.company.annotation.audio.services;

import com.company.annotation.audio.pojos.VisualData;
import com.company.annotation.audio.pojos.VisualParameters;

/**
 * Created with IntelliJ IDEA.
 * User: tbeauvais
 * Date: 7/4/12
 * Time: 1:26 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IAnnotationService {
    VisualData loadVisualData(String idIndexFile, VisualParameters visualParameters);
}
