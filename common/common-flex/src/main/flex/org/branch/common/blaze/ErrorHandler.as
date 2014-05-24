/**
 * Created by IntelliJ IDEA.
 * User: tbeauvais
 * Date: 3/6/12
 * Time: 3:11 PM
 * To change this template use File | Settings | File Templates.
 */
package org.branch.common.blaze {
import mx.controls.Alert;
import mx.rpc.AsyncToken;
import mx.rpc.events.FaultEvent;

public class ErrorHandler {
    public function ErrorHandler() {
    }

    public function onError( e:Error ):void {
        Alert.show( e.getStackTrace() );
    }

    public function onFault( faultEvent:FaultEvent, sourceError:Error, token:AsyncToken ):void {
        Alert.show( "Fault: " + faultEvent.fault.getStackTrace() );
        Alert.show( "Source:" + sourceError.getStackTrace() );
    }
}
}
