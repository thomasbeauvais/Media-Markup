//package org.branch.annotation.audio.web.controller;
//
//import org.branch.annotation.audio.api.IIndexEngine;
//import org.branch.annotation.audio.api.IPersistenceEngine;
//import org.branch.annotation.audio.pojos.AudioFile;
//import org.branch.annotation.audio.pojos.IndexWithSamples;
//import org.branch.annotation.audio.pojos.SampleList;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.mvc.SimpleFormController;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.*;
//import java.net.BindException;
//
//import static org.branch.annotation.audio.util.StringUtils.convertMillis;
//
///**
// * This controller will be used to upload the MP3 files to the database and index them.
// *
// * TODO:  Should this be abstracted?  Are there other places that make more sense to store the bytes?
// */
//@Controller
//@RequestMapping("/upload2")
//public class UploadController extends SimpleFormController {
//    private IPersistenceEngine indexSamplesRepository;
//
//    private static Logger logger = Logger.getLogger( "org.branch.annotation.audio" );
//
//    @Autowired
//    public void setPersistenceEngine( IPersistenceEngine indexSamplesRepository ) {
//        this.indexSamplesRepository = indexSamplesRepository;
//    }
//
//    private IIndexEngine indexEngine;
//
//    @Autowired
//    public void setIndexEngine( IIndexEngine indexEngine ) {
//        this.indexEngine = indexEngine;
//    }
//
//    public UploadController() {
//        setCommandClass( FileUpload.class );
//    }
//
//    @RequestMapping( method = RequestMethod.GET )
//    public ModelAndView doGet() {
//        return new ModelAndView( "upload" );
//    }
//
//    @RequestMapping( value = "print", method = RequestMethod.GET )
//    public @ResponseBody String doGet2(@RequestParam(defaultValue = "" ) String uid ) {
//        final AudioFile audioFile = indexSamplesRepository.load( uid, AudioFile.class );
//        final StringBuffer strBuff = new StringBuffer();
//        byte[] bytes = audioFile.getBytes();
//        for ( int i = 0; i < bytes.length; i++ ) {
//            strBuff.append( (char) bytes[ i ] );
//        }
//
//        return strBuff.toString();
//    }
//
//    @RequestMapping( method = RequestMethod.POST )
//    @Transactional
//    public void onSubmit( HttpServletRequest request,
//                        HttpServletResponse response,
//                        @ModelAttribute("objectToShow") Object command,
//                        BindException errors)
//            throws Exception
//    {
//
//        FileUpload file = (FileUpload) command;
//
//        MultipartFile multipartFile = file.getFile();
//
//        String fileName="";
//
//        if(multipartFile!=null){
//            final String name   = "test-" + System.currentTimeMillis();
//            final long start    = System.currentTimeMillis();
//            fileName            = multipartFile.getOriginalFilename();
//
//            InputStream inputStream             = null;
//
//            try {
//                inputStream                     = new ByteArrayInputStream( multipartFile.getBytes() );
//
//                final SampleList sampleList     = indexEngine.createIndex( inputStream, name );
//
//                final IndexWithSamples indexSummary = sampleList.getIndexSummary();
//
//                logger.info("*** Created SampleList: " + name);
//
//                logger.info("*** Creating AudioFile: " + name);
//
//                final AudioFile audioFile = new AudioFile();
//                audioFile.setBytes( multipartFile.getBytes() );
//
//                logger.info("*** Created AudioFile: " + name);
//
//                logger.info("*** Attempting to save AudioFile: " + name);
//
//                final AudioFile saved = indexSamplesRepository.save( audioFile );
//
//                // TODO:  Add this
////                indexSummary.setOriginalFilename( fileName );
//                indexSummary.setAudioFileUid( saved.getUid() );
//
//                logger.info("*** Attempting to save SampleList: " + name);
//
//                indexSamplesRepository.save( indexSummary );
//
//                logger.info("*** Creation of index file complete for: " + name);
//            } finally {
//                logger.info("Time to upload " + name + " was " + convertMillis(start - System.currentTimeMillis()));
//
//                try {
//                    inputStream.close();
//                } catch( Exception e ) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    @ModelAttribute("objectToShow")
//    public Object createCommandObject() throws IllegalAccessException, InstantiationException {
//        return getCommandClass().newInstance();
//    }
//}
