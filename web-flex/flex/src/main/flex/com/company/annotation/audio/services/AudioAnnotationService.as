package com.company.annotation.audio.services {
import com.company.common.blaze.BlazeResponder;

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

    public function load( id:String, successCallback:Function, failCallback:Function = null ):void {
        var responder:BlazeResponder = new BlazeResponder(
                null,
                successCallback,
                failCallback
        );

        m_serviceObject.load( id ).addResponder( responder );
    }

}
}
