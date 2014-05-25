package org.branch.annotation.audio.web.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: tbeauvais
 * Date: 7/7/12
 * Time: 5:08 PM
 * To change this template use File | Settings | File Templates.
 */
@RequestMapping(value = "/error")
public class ErrorServlet extends HttpServlet
{
    private static Logger logger = Logger.getLogger(ErrorServlet.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        // You can log the exception, send to email, etc
//        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
//
//        logger.error(throwable);
//
//        throwable.printStackTrace();
//
//        throw throwable;
    }
}
