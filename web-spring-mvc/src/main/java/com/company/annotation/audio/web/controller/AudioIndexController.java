/*
 * =============================================================================
 * 
 *   Copyright (c) 2011, The THYMELEAF team (http://www.thymeleaf.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */
package com.company.annotation.audio.web.controller;

import com.company.annotation.audio.pojos.Annotation;
import com.company.annotation.audio.pojos.IndexSummary;
import com.company.annotation.audio.services.AudioAnnotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class AudioIndexController {
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private AudioAnnotationService audioAnnotationService;

    private static List<Annotation> s_annotations =  new Vector<Annotation>();

    @ModelAttribute("indexFiles")
    public List<IndexSummary> allIndexFiles() {
        return Arrays.asList(this.audioAnnotationService.loadAll());
    }

    @ModelAttribute("annotation")
    public Annotation newAnnotation() {
        return new Annotation();
    }

    @RequestMapping( value = "/annotations", method = RequestMethod.GET )
    public ModelAndView getAnnotations() {
        // Sort based on how many comments are in a thread
        Collections.sort( s_annotations, new Comparator<Annotation>() {
            public int compare(Annotation annotationGraph1, Annotation annotationGraph2 ) {
                if ( annotationGraph1.size() == annotationGraph2.size() ) {
                    return annotationGraph1.getDate().compareTo(annotationGraph1.getDate());
                }

                return ((Integer) annotationGraph1.size() ).compareTo( annotationGraph2.size() );
            }
        });

        Map modelMap = new HashMap();
        modelMap.put( "annotations", s_annotations );

        return new ModelAndView( "annotations", modelMap );
    }

    @RequestMapping( value = "/", method = RequestMethod.GET )
    public ModelAndView showIndexFiles( ) {
        return new ModelAndView( "main" );
    }

    @RequestMapping( value = "/annotations/add", method = RequestMethod.POST )
    public ModelAndView addAnnotation( @ModelAttribute("annotation") Annotation annotation ) {
        System.out.println( "ID of Index File:" + annotation.getIdIndexFile() );
        System.out.println( "Annotation Text:" + annotation.getText() );

        s_annotations.add( annotation );

        return new ModelAndView( "redirect:/annotations");
    }
}
