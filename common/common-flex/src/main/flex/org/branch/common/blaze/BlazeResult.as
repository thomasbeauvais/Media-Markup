/**
 * Created by IntelliJ IDEA.
 * User: tbeauvais
 * Date: 3/6/12
 * Time: 3:08 PM
 * To change this template use File | Settings | File Templates.
 */
package org.branch.common.blaze {

[RemoteClass(alias="com.thetus.core.blaze.BlazeResult")]
public class BlazeResult
{
    private var m_result:Object;
    private var m_svcError:ServiceError;

    public function BlazeResult() {
    }

    public function get result():Object {
        return m_result;
    }

	public function set result(value:Object):void{
		m_result = value;
	}

    /**
     * added for use with service stubs  rpm
     * @param r result object
     */
    public function setResult(r:Object):void {
        m_result = r;
    }

    public function get serviceError():ServiceError {
        return m_svcError;
    }

	public function set serviceError(value:ServiceError):void{
		m_svcError = value;
	}
}
}
