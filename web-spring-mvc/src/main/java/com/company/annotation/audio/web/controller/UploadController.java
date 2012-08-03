package com.company.annotation.audio.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.BindException;

/**
 * This controller will be used to upload the MP3 files to the database and index them.
 *
 * TODO:  Should this be abstracted?  Are there other places that make more sense to store the bytes?
 */
@Controller
@RequestMapping("/upload")
public class UploadController extends SimpleFormController {
    public UploadController() {
        setCommandClass( FileUpload.class );
    }

    @RequestMapping( method = RequestMethod.GET )
    public ModelAndView doGet() {
        return new ModelAndView( "upload" );
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

            //do whatever you want



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
