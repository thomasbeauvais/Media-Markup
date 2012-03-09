package com.company.annotation.audio.api;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

/**
 * Created by IntelliJ IDEA.
 * User: tbeauvais
 * Date: 11/6/11
 * Time: 9:41 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IFilePersistenceConnector {
    void writeObject( @NotNull String id, @NotNull Object obj, @NotNull Writer writer) throws IOException;

    void writeObject( @NotNull String id, @NotNull Object obj, @NotNull File outputFile ) throws IOException;

    <T extends Object> T readObject( @NotNull String id, @NotNull InputStream inputSTream, @NotNull Class<T> objectClazz ) throws IOException;
}
