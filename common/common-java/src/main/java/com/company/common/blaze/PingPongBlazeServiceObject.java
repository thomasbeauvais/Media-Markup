package com.company.common.blaze;

import org.jetbrains.annotations.Nullable;

/**
 * Created by IntelliJ IDEA.
 * User: tbeauvais
 * Date: 3/6/12
 * Time: 11:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class PingPongBlazeServiceObject {

    public static final String PONG = "pong";

    public String ping() {
        return PONG;
    }

    public String ping( String stamp ) {
        if ( stamp != null ) {
            return "pong:" +stamp;
        }

        return ping();
    }
}
