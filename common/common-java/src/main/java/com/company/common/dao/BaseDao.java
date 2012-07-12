package com.company.common.dao;

import java.util.List;

public interface BaseDao<B extends Identifiable> {
    B getNewInstance();

    List<B> getAll();

    B getById(String uid);

    void save(B obj);

    void update(B obj);

    void saveOrUpdate(B obj);

    void delete(B obj);

    int getCountAll();

    int getCountByExample(B example);

    List<B> getPageOfDataAll(PaginationInfo pageInfo);

    List<B> getPageOfDataByExample(B example, PaginationInfo pageInfo);

    List<B> getByExample(B example);
}
