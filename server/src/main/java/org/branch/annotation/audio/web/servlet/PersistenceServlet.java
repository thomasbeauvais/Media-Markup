package org.branch.annotation.audio.web.servlet;

import org.branch.annotation.audio.api.PersistenceEngine;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: tbeauvais
 * Date: 11/6/11
 * Time: 10:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class PersistenceServlet extends HttpServlet {
    public static final String MEDIA_PROJECT_DIR = "MEDIA_PROJECT_DIR";

    private PersistenceEngine persistenceEngine;

    @Autowired
    public void setPersistenceEngine( PersistenceEngine persistenceEngine ) {
        this.persistenceEngine = persistenceEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ( !System.getenv().containsKey( MEDIA_PROJECT_DIR ) ) {
            throw new RuntimeException( "Must have environment variable " + MEDIA_PROJECT_DIR + " that points to the base " +
                    "for this project. For instance, 'export " + MEDIA_PROJECT_DIR + "=/home/user/Media-Markup'" );
        }

        //TODO:  Pull this file from the url of the request..
        final String idIndexFile    = "test-file-large";
//        final String idIndexFile    = "test-file-small";

        final String strProjectDir  = System.getenv( MEDIA_PROJECT_DIR );
        final String strBaseDir     = "server"
                + File.separator
                + "data"
                + File.separator
                + "index";

        final String strFilePath    = strBaseDir
                + File.separator
                + idIndexFile
                + ".index";

        final File inputFile        = new File( strProjectDir, strFilePath );

        System.out.println( inputFile.getAbsolutePath() );

        final FileReader fileReader = new FileReader( inputFile );
        final char[] buffer         = new char[ 1024 ];
        int read                    = 0;
        while ( ( read = fileReader.read( buffer ) ) > 0 ) {
            resp.getWriter().write( buffer, 0, read );

        }
    }
}

