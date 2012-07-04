package com.company.common.blaze {

import mx.core.FlexGlobals;
import mx.rpc.AsyncResponder;
import mx.rpc.AsyncToken;
import mx.rpc.Fault;
import mx.rpc.events.FaultEvent;
import mx.rpc.events.ResultEvent;

/**
 * New model of Blaze Responder that generalizes and helps to eliminate excess boilerplate code.
 * This model uses the idea of a delegated callback function, which is used in cases where the callback function
 * is going to result in something trivial/null.
 * If there is actual work/massaging to do with the result, then use the primarySuccessCallback, if not, set it to null
 */
public class BlazeResponder extends AsyncResponder {

    private var m_delegatedSuccessCallback:Function;
    private var m_primarySuccessCallback:Function;
    private var m_failureCallback:Function;
    private var m_sourceError:Error;
    private var m_errorHandler:ErrorHandler;

    /**
     * Responder constructor.
     * New model of Blaze Responder that generalizes and helps to eliminate excess boilerplate code.
     * This model uses the idea of a delegated callback function, which is used in cases where the callback function
     * is going to result in something trivial/null.
     * If there is actual work/massaging to do with the result, then use the primarySuccessCallback, if not,
     * set it to null
     *
     * * NOTE:  The failureCallback, if specified, will need the signature: function( serverFault:FaultEvent, sourceStack:Error, token:Object);
     *
     * @param primarySuccessCallback The main success function. This function can be null unless there is massaging
     * or something else that needs to be done with the result. Often the function will be void, so there is no point
     * in passing it back here
     *
     * @param delegatedSuccessCallback
     *
     * @param failCallback The failure callback function. This should almost always be null (unless you are
     * absolutely certain you need a failure callback) <b>function( serverFault:FaultEvent, sourceStack:Error, token:Object)</b>
     *
     * @param token See the API for AsyncResponder. Almost always defaults to null.
     */
    public function BlazeResponder(primarySuccessCallback:Function, delegatedSuccessCallback:Function, failCallback:Function = null, token:Object = null) {
        super(primarySuccessCallback, failCallback, token);

        m_primarySuccessCallback = primarySuccessCallback;
        m_delegatedSuccessCallback = delegatedSuccessCallback;
        m_failureCallback = failCallback;

        // Used to record the source of the call because of the services asynchronous nature
        m_sourceError = new Error();
    }

    /**
     * Result event for the Blaze responder. In many cases, this will be null, and we just want to use the async result
     * path to pass down the callbacks. But, in the case where there is an actual BlazeResult (which could be
     * a serviceError), we pass the event and the event token down to onSuccess to be handled.
     * @param info
     */
    override public function result(info:Object):void {
        var resultEvent:ResultEvent = info as ResultEvent;

        // Depends on the 'null' cast because there might not be a BlazeResult as the ResultEvent.result
        var blazeResult:BlazeResult = resultEvent.result as BlazeResult;

        if (blazeResult != null && blazeResult.serviceError == null) {
            // A successful callback from Blaze with a BlazeResult..
            onSuccess(blazeResult, resultEvent.token);
        } else if (blazeResult != null && blazeResult.serviceError != null) {
            // Otherwise, we have a BlazeResult, but a provided error message from the service layer
            onBlazeResultServiceError(blazeResult, resultEvent.token);

        } else {
            // If there was no BlazeResult, then we just use the normal async result path but delegate to the
            //   assigned callbacks.
            if (null != m_primarySuccessCallback) {
                trace("The BlazeResult was null and there was no error.  Calling the primary callback: " + m_primarySuccessCallback);

                try {
                    m_primarySuccessCallback(resultEvent);
                } catch (e:Error) {
                    if (m_errorHandler) {
                        m_errorHandler.onError(e);
                    }
                }

            } if ( null != m_delegatedSuccessCallback ) {
                trace("The BlazeResult was null, there was no error and the primary callback was null.  Calling the 'delegated callback' with the result: " + resultEvent.result);

                // If there was a return
                if ( resultEvent.result ) {
                    m_delegatedSuccessCallback( resultEvent.result );
                } else {
                    // Otherwise, it is void..
                    m_delegatedSuccessCallback();
                }
            } else {
                trace("The BlazeResult was null, there was no error and the primary callback was null.  Calling the super with info: " + info);

                super.result(info);
            }

        }

    }

    /**
     * The error handler for BlazeResultServiceErrors. If the BlazeResult brought back a service error, we
     * need to figure out what type of error it is and deal with it then get the details back to the client.
     *
     * @param blazeResult (not null) The BlazeResult being passed down, we already know that this result has
     * a service error, we just need to get the specifics
     *
     * @param token See the API for AsyncResponder. Almost always defaults to null.
     */
    private function onBlazeResultServiceError(blazeResult:BlazeResult, token:AsyncToken = null):void {
        var faultString:String = blazeResult.serviceError.message;

        //create an error message containing the errorTicketId and the actual service error message
        if (blazeResult.serviceError.errorTicketId) {
            // The service error has a errorTicketId that needs to be tacked on to the fault message
            faultString = "An error occurred in the server, please contact your administrator with error ticket id "
                    + blazeResult.serviceError.errorTicketId + "; details follow:\n" + faultString;
        }

        //pass the fault event back to the fault handler
        var faultEvent:FaultEvent = new FaultEvent(FaultEvent.FAULT, false, true,
                new Fault(blazeResult.serviceError.errorID.toString(), faultString, blazeResult.serviceError.serverStackTrace), token);

        onFault(faultEvent);
    }

    /**
     * If the Blaze request was successful, send the result back through the primary success callback function
     * If the primary callback function was null, then use the delegated success callback function
     *
     * @param resultEvent the Blaze {@link ResultEvent}
     */
    protected function onSuccess(blazeResult:BlazeResult, token:AsyncToken = null):void {
        var returnResult:Object = blazeResult == null ? null : blazeResult.result;

        if (null != m_primarySuccessCallback) {
            // Call the primary returning a result..
            returnResult = m_primarySuccessCallback( returnResult );

        }

        // Note: if the return result is null, the callback will be called with
        //       no parameters, so you must ensure that your
        //       callback has a defaultNull annotation set.
        if (null != m_delegatedSuccessCallback && returnResult) {
            // If there is a delegatedSuccessCallback.. then call it..
            m_delegatedSuccessCallback(returnResult);

        } else if (null != m_delegatedSuccessCallback) {
            // Otherwise, in the case in which no parameters are provided (hint: a 'void' return)
            // then just call the delegatedSuccessCallback with no parameters
            m_delegatedSuccessCallback();

        } else {
            // You should really never get here....
            trace("Warning: There was no delegatedSuccessCallback provided token: " + returnResult + "(token=" + token + ")");
        }
    }

    override public function fault( info:Object ):void {
        if ( info is FaultEvent ) {
            onFault( FaultEvent(info) );
        } else {
            trace( "Received fault info from BlazeDS.  This should be handled by either the SavannaBlazeResponder or the caller: " + info );
            trace( "Stack Track of Caller: " + m_sourceError.getStackTrace() );
        }
    }

    /**
     * If the Blaze request resulted in a fault, send it back through the failure callback function or if the
     * failureCallback is null then it will delegate to the super in AsyncResponder.
     *
     * NOTE:  The failureCallback, if specified, will need the signature: function( serverFault:FaultEvent, sourceStack:Error, token:Object);
     *
     * @param faultEvent The Blaze {@link FaultEvent}
     */
    protected function onFault(faultEvent:FaultEvent):void {
        if (null != m_failureCallback) {
            m_failureCallback(faultEvent, m_sourceError, faultEvent.token);

        } else if (null != m_errorHandler) {
            m_errorHandler.onFault(faultEvent, sourceError, faultEvent.token);

        } else {
            trace(m_sourceError.getStackTrace());

            super.fault(faultEvent);

        }
    }

    /**
     * The handler for the failureCallback, this will almost always be null, because the client doesn't really
     * need a callback, the source error and stack trace should be enough.
     */
    public function get failureCallback():Function {
        return m_failureCallback;
    }

    /**
     * An error in the context of the stack when the call was made, as opposed to when the response came back
     */
    public function get sourceError():Error {
        return m_sourceError;
    }

    /**
     * The 'success callback' of the secondary handler.  It is usually, in the case of a service call, the
     * caller of the service method.
     *
     * For example,
     *
     * <code>
     * class MyStuffComponent {
     *      function loadMyStuffFromService():void {
     *          m_graphService.getMyStuff( onSuccess_loadMyStuffFromService ); // No faultHandler necessary
     *      }
     *
     *      function onSuccess_loadMyStuffFromService( myStuffGraph:MyStuff ) {
     *          ....
     *          this.refreshMyStuff( myStuffGraph ); // Refresh with the result from the service
     *          ....
     *      }
     * }
     * </code>
     *
     * Below is 'psuedo code':
     * <code>
     * class GraphService {
     *      function getMyStuff( successCallback:Function, failureCallback:Function = null ):void {
     *          // Make the actual call to the server
     *          // TODO:  Move the code from SavannaBlazeResponder when all usages migrate to this pattern
     *          m_remoteObject.getMyStuff(
     *              new BlazeResponder(
     *                  function( result:Object ) {
     *                      // Massage the result from the service: see BlazeResponder.result()
     *                      // The result of 'this' or 'primaryCallback' will be the
     *                      // input into the 'secondary' or delegated callback (shown above).
     *
     *                      // Cast for input into onSuccess_loadMyStuffFromService() (shown above)
     *                      return processResult( result ) as MyStuff;
     *                  },
     *                  successCallback,
     *                  failureCallback
     *              )
     *          );
     *      }
     * }
     * </code>
     *
     * This code path describes the service intercepting the result from the service and processing the data
     * from the services (such as a ResolvedRsrcSet) before it's returned to the caller, in this case
     * MyStuffComponent.onSuccess_loadMyStuffFromService( myStuff:MyStuff ).
     *
     * @param value the delegated success callback function
     */
    public function set delegatedSuccessCallback(value:Function):void {
        m_delegatedSuccessCallback = value;
    }
}
}
