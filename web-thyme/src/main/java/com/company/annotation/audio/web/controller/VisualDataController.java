package com.company.annotation.audio.web.controller;

import com.company.annotation.audio.pojos.VisualData;
import com.company.annotation.audio.pojos.VisualParameters;
import com.company.annotation.audio.services.IAnnotationService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: tbeauvais
 * Date: 7/4/12
 * Time: 1:02 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class VisualDataController {

    @Autowired
    private IAnnotationService audioAnnotationService;

    @RequestMapping({"/visualData"})
    public @ResponseBody String doGet( @RequestParam Integer width, @RequestParam Integer height ) {
//        if ( !System.getenv().containsKey( MEDIA_PROJECT_DIR ) ) {
//            throw new RuntimeException( "Must have environment variable " + MEDIA_PROJECT_DIR + " that points to the base " +
//                    "for this project. For instance, 'export " + MEDIA_PROJECT_DIR + "=/home/user/Media-Markup'" );
//        }

        //TODO:  Pull this file from the url of the request..
        final String idIndexFile    = "test-file-large";
//        final String idIndexFile    = "test-file-small";

        System.out.println( " ***** Loading visual data for: " + idIndexFile );
        System.out.println( " ***** Parameters: width=" + width + ", height=" + height );

        final VisualParameters visualParameters = new VisualParameters();
        visualParameters.setHeight( height );
        visualParameters.setWidth( width );

        final VisualData visualData =  audioAnnotationService.loadVisualData(idIndexFile, visualParameters);

        System.out.println( " ***** LOADING VISUAL DATA " );
        System.out.println( " ***** LOADING VISUAL DATA " );

        final JSONObject jsonObject = new JSONObject();

        final JSONArray jsonArray   = new JSONArray();
        for ( int i : visualData.getVisualSamples() ) {
            jsonArray.add( i );
        }

        jsonObject.put( "samples", jsonArray );

        return jsonObject.toJSONString();
    }
}
