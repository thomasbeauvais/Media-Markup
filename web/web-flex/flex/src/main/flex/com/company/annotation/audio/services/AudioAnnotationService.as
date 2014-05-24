package org.branch.annotation.audio.services {
import org.branch.annotation.audio.pojos.IndexSummary;
import org.branch.common.blaze.BlazeResponder;

import mx.rpc.remoting.RemoteObject;

public class AudioAnnotationService {

    private var m_serviceObject:RemoteObject;

    private static const SVC_DESTINATION:String = "audioAnnotationService";

    public function AudioAnnotationService() {
        initializeRemoteObject( SVC_DESTINATION );
    }

    private function initializeRemoteObject( destination:String ):void {
        m_serviceObject = new RemoteObject( destination );

        // record source of server call
        var sourceError:Error = new Error();
    }

    public function loadAll( successCallback:Function, failCallback:Function = null ):void {
        var responder:BlazeResponder = new BlazeResponder(
                null,
                successCallback,
                failCallback
        );

        m_serviceObject.loadAll().addResponder( responder );
    }

    public function loadSamples( id:String, successCallback:Function, failCallback:Function = null ):void {
        var responder:BlazeResponder = new BlazeResponder(
                null,
                successCallback,
                failCallback
        );

        m_serviceObject.loadSamples( id ).addResponder( responder );
    }

    public function save( id:String, indexSummary:IndexSummary, successCallback:Function, failCallback:Function = null ):void {
        var responder:BlazeResponder = new BlazeResponder(
                null,
                successCallback,
                failCallback
        );

        m_serviceObject.save( id, indexSummary ).addResponder( responder );
    }
}
}
