package org.branch.annotation.audio.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: tbeauvais
 * Date: 11/6/11
 * Time: 10:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class AudioServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //TODO:  Add this to the application context in the web projects

//        final String idIndexFile    = "test-file-large";
        final String idIndexFile    = "test-file-small";

        final String strFilePath    = idIndexFile + ".mp3";
        final String strBaseDir     = "/home/tbeauvais/Code/audio-annotation-html5/server/data";

        final File inputFile        = new File( strBaseDir, strFilePath );

        System.out.println( inputFile.getAbsolutePath() );

        resp.setContentLength( (int) inputFile.length() );
        resp.addHeader("Content-Disposition", "attachment; filename=fileName");
        resp.setContentType("audio/mpeg");

        Writer writer               = resp.getWriter();

        FileInputStream input       = new FileInputStream(inputFile);
        BufferedInputStream buf     = new BufferedInputStream(input);
        int readBytes = 0;

        //read from the file; write to the ServletOutputStream
        while ((readBytes = buf.read()) != -1) {
            writer.write(readBytes);

        }

        writer.flush();

//        final FileReader fileReader = new FileReader( inputFile );
//        final char[] buffer         = new char[ 1024 ];
//        int read                    = 0;
//        while ( ( read = fileReader.read( buffer ) ) > 0 ) {
//            writer.write( buffer, 0, read );
//
//        }
//
//        writer.flush();
    }
}
