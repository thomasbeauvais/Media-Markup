package com.company.annotation.audio.web.controller;

import com.company.annotation.audio.pojos.VisualData;
import com.company.annotation.audio.pojos.VisualParameters;
import com.company.annotation.audio.pojos.VisualRegion;
import com.company.annotation.audio.services.IAnnotationService;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: tbeauvais
 * Date: 7/4/12
 * Time: 1:02 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/audioData")
public class AudioDataController extends DefaultSpringController {

    @Autowired
    private IAnnotationService audioAnnotationService;

    private static Logger logger = Logger.getLogger( "com.company.annotation.audio" );

    @RequestMapping( value = "/music", method = RequestMethod.GET )
    public ModelAndView showIndexFiles( ) {
        return new ModelAndView( "music" );
    }

    @RequestMapping( method = RequestMethod.GET )
    @Transactional
    public void doGet( @RequestParam(defaultValue = "0" ) long startPos, HttpServletResponse response ) throws Exception {
        final File song = new File( "/home/tbeauvais/Development/personal/Media-Markup/server/data/songs/test-file-large.mp3" );
        final InputStream inputStream = new FileInputStream( song );

        response.setContentType( "audio/mpeg" );
        response.setContentLength( inputStream.available() );

        final long skip = startPos;

        byte[] buff = new byte[ 4000 ];
        int read = -1;
        long count = 0;
        while ( (read = inputStream.read(buff) ) > -1 ) {
            count += read;
            if ( count > skip ) {
                response.getOutputStream().write( buff, 0, read );
                response.flushBuffer();
            }
        }
    }
}
