package org.branch.annotation.audio.api;

import org.branch.common.dao.Identifiable;

/**
 *
 */
public interface IPersistenceEngine {
    <T extends Identifiable> T save(T obj);

    <T extends Identifiable> T load( String uid, Class<T> objectClazz );

    <T extends Identifiable> T[] loadAll( Class<T> objectClazz );

    <T extends Identifiable> void delete( Class<T> clazz, String uid );
}
