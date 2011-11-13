package com.company.common.api;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * Created by IntelliJ IDEA.
 * User: tbeauvais
 * Date: 11/6/11
 * Time: 9:41 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IFileInputOutput {
    void writeObject(Object obj, Writer writer) throws IOException;

    <T extends Object> T readObject(Reader reader, Class<T> objectClazz) throws IOException;
}
