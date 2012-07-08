package com.company.annotation.audio.web.controller;

import com.company.annotation.audio.pojos.VisualData;
import com.company.annotation.audio.pojos.VisualParameters;
import com.company.annotation.audio.services.IAnnotationService;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: tbeauvais
 * Date: 7/4/12
 * Time: 1:02 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/visualData")
public class VisualDataController extends DefaultSpringController {

    @Autowired
    private IAnnotationService audioAnnotationService;

    private static Logger logger = Logger.getLogger( "com.company.annotation.audio" );

    @RequestMapping( method = RequestMethod.GET )
    public @ResponseBody String doGet( @RequestParam String idIndexFile, @RequestParam Integer width, @RequestParam Integer height ) throws Exception {
//        if ( !System.getenv().containsKey( MEDIA_PROJECT_DIR ) ) {
//            throw new RuntimeException( "Must have environment variable " + MEDIA_PROJECT_DIR + " that points to the base " +
//                    "for this project. For instance, 'export " + MEDIA_PROJECT_DIR + "=/home/user/Media-Markup'" );
//        }

        //TODO:  Pull this file from the url of the request..
//        idIndexFile    = "test-file-large";
//        final String idIndexFile    = "test-file-large";
//        final String idIndexFile    = "test-file-small";

        System.out.println( " ***** Loading visual data for: " + idIndexFile );
        System.out.println( " *** Parameters: width=" + width + ", height=" + height );

        final VisualParameters visualParameters = new VisualParameters();
        visualParameters.setHeight( height );
        visualParameters.setWidth( width );

        final VisualData visualData =  audioAnnotationService.loadVisualData(idIndexFile, visualParameters);

        System.out.println( " ***** LOADING VISUAL DATA " );
        System.out.println( " ***** LOADING VISUAL DATA " );

        final JSONObject jsonObject = new JSONObject();

        final JSONArray jsonSamples     = new JSONArray();
        final JSONArray jsonPositions    = new JSONArray();

        int[] visualSamples     = visualData.getVisualSamples();
        long[] visualPositions  = visualData.getVisualPositions();
        for (int i = 0, visualSamplesLength = visualSamples.length; i < visualSamplesLength; i++) {
            int value       = visualSamples[i];
            long position   = visualPositions[i];

            jsonSamples.add(value);
            jsonPositions.add(position);
        }

        jsonObject.put( "samples", jsonSamples );
        jsonObject.put( "positions", jsonPositions );

        return jsonObject.toJSONString();
    }
}
