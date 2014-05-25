package org.branch.annotation.audio.api;

import org.branch.common.data.Identifiable;

/**
 *
 */
public interface PersistenceEngine
{
    <T extends Identifiable> T save(T obj);

    <T extends Identifiable> T load( String uid, Class<T> objectClazz );

    <T extends Identifiable> T[] loadAll( Class<T> objectClazz );

    <T extends Identifiable> void delete( Class<T> clazz, String uid );
}
