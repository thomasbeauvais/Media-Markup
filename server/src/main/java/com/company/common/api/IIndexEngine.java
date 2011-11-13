package com.company.common.api;

import com.company.common.pojos.IndexObject;

import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: tbeauvais
 * Date: 11/6/11
 * Time: 8:54 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IIndexEngine {
    IndexObject makeIndexObject(InputStream inputStream, String strFileName);
}
