package com.company.annotation.audio.api;

/**
 * Created by IntelliJ IDEA.
 * User: tbeauvais
 * Date: 11/6/11
 * Time: 9:33 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IPersistenceEngine {
    void save(String id, Object obj);

    <T extends Object> T load( String id, Class<T> objectClazz);

    <T extends Object> T[] loadAll( Class<T> objectClazz );
}
