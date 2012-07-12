package com.company.annotation.audio.io;

import com.company.annotation.audio.api.IPersistenceEngine;
import com.company.common.dao.Identifiable;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
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
    private static EntityManager entityManager = Persistence.createEntityManagerFactory("com.company.annotation.audio").createEntityManager();

    @Override
    public void save(Identifiable obj) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(obj);
        } finally {
            entityManager.getTransaction().commit();
        }
    }

    @Override
    public <T extends Identifiable> T load(String id, Class<T> clazz) {
        try{
            entityManager.getTransaction().begin();
            return entityManager.find( clazz, id );
        } finally {
            entityManager.getTransaction().commit();
        }
    }

    @Override
    public <T extends Identifiable> T[] loadAll(Class<T> objectClazz) {
        try {
            entityManager.getTransaction().begin();
            List<T> result = entityManager.createQuery( "from " + objectClazz.getSimpleName(), objectClazz ).getResultList();
            return result.toArray((T[]) Array.newInstance(objectClazz, 0));
        } finally {
            entityManager.getTransaction().commit();
        }
    }
}
