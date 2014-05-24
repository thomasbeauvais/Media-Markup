package org.branch.annotation.audio.web.controller;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created with IntelliJ IDEA.
 * User: tbeauvais
 * Date: 7/7/12
 * Time: 5:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultSpringController {

    private static Logger logger = Logger.getLogger("org.branch.annotation.audio");

    @ExceptionHandler(Exception.class)
    public void processException(Exception exception) {
        logger.log( Priority.WARN, "Error occurred: ", exception );

//        exception.printStackTrace();
    }
}
