package org.branch.common.blaze;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This class is used to wrap results returned from a blaze service call.  Unfortunately, exceptions
 * throws by blaze services are not simply serialized and passed over to the client.  Instead, the exception
 * is wrapped in a {@link flex.messaging.io.amf.client.exceptions.ServerStatusException} (in the case of java-to-java)
 * and the actual exception is
 * instantiated on the client with all of its fields zero'd out.  The problem with this is that we use custom
 * exceptions that maintain extra information (status codes, error messages, etc.)  To get around this we're going
 * to write our blaze services such that they never throw exceptions.  Instead, they package the exception
 * in an {@link AServiceError} and communicate them to the client.  The client can unpack the error and
 * throw an appropriate exception.
 * </p>
 * Note, it might be tempted to simply serialize the exception as the error payload of this result.  In testing
 * I've found that the blaze library treats the serialization of exceptions differently, regardless of
 * whether the exception is thrown from a service call, or is a payload of a response.  In short, don't try to
 * serialize an exception over blaze.
 */
public class BlazeResult {
    private Object m_result;
//    private AServiceError m_svcError;

    /**
     * No-arg constructor for serialization.
     */
    public BlazeResult() {
    }

    /**
     * Set the result value for the service call.
     *
     * @param result (nullable) The result from the service call.  This value must be serializable!
     */
    public void setResult( Object result ) {
        m_result = result;
    }

    /**
     * Get the result value for the service call.
     *
     * @return (nullable) The result value returned by the service call.
     */
    public Object getResult() {
        return m_result;
    }
//
//    /**
//     * Set an error if an error occurred on the server.
//     *
//     * @param svcError (not null) The service error.
//     */
//    public void setServiceError( @NotNull AServiceError svcError ) {
//        m_svcError = svcError;
//    }
//
//    /**
//     * If an error occurred on the server then the return value from this method will be non-null.
//     *
//     * @return (nullable) The error that occurred on the server, if an error occurred.
//     */
//    @Nullable
//    public AServiceError getServiceError() {
//        return m_svcError;
//    }

}
