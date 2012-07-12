package com.company.common.dao;

import org.hibernate.dialect.PostgreSQL82Dialect;

/**
 * Created with IntelliJ IDEA.
 * User: tbeauvais
 * Date: 7/11/12
 * Time: 4:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class PostgresWorkAroundDialect extends PostgreSQL82Dialect {
    @Override
    public boolean useInputStreamToInsertBlob() {
        return true;
    }

    @Override
    protected void registerColumnType(int code, String name) {
        super.registerColumnType(code, name);
    }
}
