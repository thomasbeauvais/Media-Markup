package com.company.annotation.audio.web.controller;

import com.company.annotation.audio.api.IIndexEngine;
import com.company.annotation.audio.api.IPersistenceEngine;
import com.company.annotation.audio.pojos.AudioFile;
import com.company.annotation.audio.pojos.Comment;
import com.company.annotation.audio.pojos.IndexWithSamples;
import com.company.annotation.audio.pojos.SampleList;
import org.apache.commons.logging.impl.LogKitLogger;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.BindException;
import java.util.List;
import java.util.Vector;

/**
 * This controller will be used to upload the MP3 files to the database and index them.
 *
 * TODO:  Should this be abstracted?  Are there other places that make more sense to store the bytes?
 */
@Controller
@RequestMapping("/upload")
public class UploadController extends SimpleFormController {
    private IPersistenceEngine persistenceEngine;
    private static final Logger LOGGER = Logger.getLogger( UploadController.class );


    @Autowired
    public void setPersistenceEngine( IPersistenceEngine persistenceEngine ) {
        this.persistenceEngine = persistenceEngine;
    }

    private IIndexEngine indexEngine;

    @Autowired
    public void setIndexEngine( IIndexEngine indexEngine ) {
        this.indexEngine = indexEngine;
    }

    public UploadController() {
        setCommandClass( FileUpload.class );
    }

    @RequestMapping( method = RequestMethod.GET )
    public ModelAndView doGet() {
        return new ModelAndView( "upload" );
    }

    @RequestMapping( value = "print", method = RequestMethod.GET )
    public @ResponseBody String doGet2(@RequestParam(defaultValue = "" ) String uid ) {
        final AudioFile audioFile = persistenceEngine.load( uid, AudioFile.class );
        final StringBuffer strBuff = new StringBuffer();
        byte[] bytes = audioFile.getBytes();
        for ( int i = 0; i < bytes.length; i++ ) {
            strBuff.append( (char) bytes[ i ] );
        }

        return strBuff.toString();
    }

    @RequestMapping( method = RequestMethod.POST )
    @Transactional
    public void onSubmit( HttpServletRequest request,
                        HttpServletResponse response,
                        @ModelAttribute("objectToShow") Object command,
                        BindException errors)
            throws Exception
    {

        FileUpload file = (FileUpload) command;

        MultipartFile multipartFile = file.getFile();

        String fileName="";

        if(multipartFile!=null){
            fileName = multipartFile.getOriginalFilename();

            InputStream inputStream             = null;

            try {
                inputStream                     = new ByteArrayInputStream( multipartFile.getBytes() );

                final String name = "test-" + System.currentTimeMillis();
                final SampleList sampleList     = indexEngine.createIndexForAudioStream( inputStream, name );

                final IndexWithSamples indexSummary = sampleList.getIndexSummary();

                LOGGER.info( "*** Created SampleList: " + name );

                LOGGER.info( "*** Creating AudioFile: " + name );

                final AudioFile audioFile = new AudioFile();
                audioFile.setBytes( multipartFile.getBytes() );

                LOGGER.info( "*** Created AudioFile: " + name );

                LOGGER.info( "*** Attempting to save AudioFile: " + name );

                persistenceEngine.save( audioFile );

                indexSummary.setAudioFileUid( audioFile.getUid() );

                LOGGER.info( "*** Attempting to save SampleList: " + name );

                persistenceEngine.save( indexSummary );

                LOGGER.info( "*** Creation of index file complete for: " + name );
            } finally {
                try {
                    inputStream.close();
                } catch( Exception e ) {
                    e.printStackTrace();
                }
            }
        }
    }

    @ModelAttribute("objectToShow")
    public Object createCommandObject() throws IllegalAccessException, InstantiationException {
        return getCommandClass().newInstance();
    }

//    @Override
//    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
//            throws ServletException {
//
//        // Convert multipart object to byte[]
//        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
//
//    }
}
