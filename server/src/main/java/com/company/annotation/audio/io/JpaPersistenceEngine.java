package com.company.annotation.audio.io;

import com.company.annotation.audio.api.IPersistenceEngine;
import com.company.common.dao.Identifiable;
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
}
