package com.company.annotation.audio.io.exceptions;

/**
 * Created by IntelliJ IDEA.
 * User: tbeauvais
 * Date: 11/6/11
 * Time: 9:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class PersistenceException extends RuntimeException {
    public PersistenceException(String s, Throwable e) {
        super( s, e );
    }

    public PersistenceException(String s) {
        super( s ) ;
    }
}
