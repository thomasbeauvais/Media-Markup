package org.branch.annotation.audio.io.exceptions;

public class PersistenceException extends RuntimeException {
    public PersistenceException(String s, Throwable e) {
        super( s, e );
    }

    public PersistenceException(String s) {
        super( s ) ;
    }
}
