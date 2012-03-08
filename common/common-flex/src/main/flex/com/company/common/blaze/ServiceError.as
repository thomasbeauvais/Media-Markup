/**
 * Created by IntelliJ IDEA.
 * User: tbeauvais
 * Date: 3/6/12
 * Time: 3:09 PM
 * To change this template use File | Settings | File Templates.
 */
package com.company.common.blaze {
public class ServiceError {
    public const message:String = "ERROR_MESSAGE";
    public var errorTicketId:String = "TICKET_ID";
    public const errorID:String = "ERROR_ID";
    public var serverStackTrace:String = "ERR_STACK_TRACE";

    public function ServiceError() {
    }
}
}
