package org.branch.annotation.audio.web.controller;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class DefaultSpringController
{

    private static Logger logger = Logger.getLogger("org.branch.annotation.audio");

    @ExceptionHandler(Exception.class)
    public void processException(Exception exception)
    {
        logger.log(Priority.WARN, "Error occurred: ", exception);

//        exception.printStackTrace();
    }
}
