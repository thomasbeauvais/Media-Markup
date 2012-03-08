package com.company.annotation.audio.services;

import com.company.annotation.audio.api.IPersistenceEngine;
import com.company.annotation.audio.pojos.IndexObject;
import com.company.annotation.audio.pojos.IndexSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingExclude;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * User: tbeauvais
 * Date: 3/6/12
 * Time: 3:25 PM
 * To change this template use File | Settings | File Templates.
 */
@Service("audioAnnotationService")
@RemotingDestination(channels={"my-amf"})
public class AudioAnnotationService {

    public AudioAnnotationService() {
    }

    private IPersistenceEngine persistenceEngine;

    @Autowired
    public void setPersistenceEngine( IPersistenceEngine persistenceEngine ) {
        this.persistenceEngine = persistenceEngine;
    }

    @RemotingInclude
    public IndexSummary[] loadAll() {
        return persistenceEngine.loadAll( IndexSummary.class );
    }

    @RemotingInclude
    public IndexObject load( String id ) {
        return persistenceEngine.load( id, IndexObject.class );
    }
}
