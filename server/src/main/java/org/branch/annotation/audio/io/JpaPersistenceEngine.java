package org.branch.annotation.audio.io;

import org.branch.annotation.audio.api.IPersistenceEngine;
import org.branch.common.dao.Identifiable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.lang.reflect.Array;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tbeauvais
 * Date: 7/11/12
 * Time: 11:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class JpaPersistenceEngine implements IPersistenceEngine {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public <T extends Identifiable> T save(T obj) {
        try {
            T t = entityManager.merge(obj);
//            entityManager.persist(obj);
            entityManager.flush();

            return t;
        } finally {
        }
    }

    @Override
    public <T extends Identifiable> T load(String id, Class<T> objectClazz) {
        try{
            return entityManager.find(objectClazz, id);
        } finally {
        }
    }

    @Override
    public <T extends Identifiable> T[] loadAll(Class<T> objectClazz) {
        try {
            List<T> result = entityManager.createQuery( "from " + objectClazz.getSimpleName(), objectClazz ).getResultList();
            return result.toArray((T[]) Array.newInstance(objectClazz, 0));
        } finally {
        }
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED )
    public <T extends Identifiable> void delete(Class<T> clazz, String uid) {
        try {
            final T entity = entityManager.find( clazz, uid );
            entityManager.remove( entity );
            entityManager.flush();
        } finally {
        }
    }
}
