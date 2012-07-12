package com.company.annotation.audio.api;

import com.company.common.dao.Identifiable;

/**
 *
 */
public interface IPersistenceEngine {
    <T extends Identifiable> void save(T obj);

    <T extends Identifiable> T load( String uid, Class<T> objectClazz );

    <T extends Identifiable> T[] loadAll( Class<T> objectClazz );
}
