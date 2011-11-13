package com.company.audio.annotation.web.servlet;

import com.company.common.api.IPersistenceEngine;
import com.company.common.pojos.IndexObject;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: tbeauvais
 * Date: 11/6/11
 * Time: 10:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class PersistenceServlet extends HttpServlet {
    private IPersistenceEngine persistenceEngine;

    @Autowired
    public void setPersistenceEngine( IPersistenceEngine persistenceEngine ) {
        this.persistenceEngine = persistenceEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String idIndexFile    = "test-file-large";
//        final String idIndexFile    = "test-file-small";

        final String strFilePath    = idIndexFile + ".index";
        final String strBaseDir     = "/home/tbeauvais/Code/audio-annotation-html5";

        final File inputFile        = new File( strBaseDir, strFilePath );

        System.out.println( inputFile.getAbsolutePath() );

        final FileReader fileReader = new FileReader( inputFile );
        final char[] buffer         = new char[ 1024 ];
        int read                    = 0;
        while ( ( read = fileReader.read( buffer ) ) > 0 ) {
            resp.getWriter().write( buffer, 0, read );

        }
    }
}
