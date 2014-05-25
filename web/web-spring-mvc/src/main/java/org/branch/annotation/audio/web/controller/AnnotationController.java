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
package org.branch.annotation.audio.web.controller;

import org.apache.log4j.Logger;
import org.branch.annotation.audio.dao.AnnotationRepository;
import org.branch.annotation.audio.dao.IndexSummaryRepository;
import org.branch.annotation.audio.model.dao.Annotation;
import org.branch.annotation.audio.model.dao.IndexSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AnnotationController extends DefaultSpringController
{
    private static Logger logger = Logger.getLogger("org.branch.annotation.audio");

    @Autowired
    private AnnotationRepository annotationRepository;

    @Autowired
    private IndexSummaryRepository indexSummaryRepository;

    @ModelAttribute("indexFiles")
    public List<IndexSummary> allIndexFiles()
    {
        return indexSummaryRepository.findAll();
    }

    @RequestMapping(value = "annotations", method = RequestMethod.GET)
    @Transactional
    public ModelAndView allAnnotations(@RequestParam String id)
    {
        final IndexSummary indexSummary = indexSummaryRepository.findOne(id);
        final List<Annotation> annotations = indexSummary.getAnnotations();

        //TODO:  Fix lazy loading so that this works..
        // Sort based on how many comments are in a thread
//        Collections.sort(comments, new Comparator<Comment>() {
//            public int compare(Comment commentGraph2, Comment commentGraph1) {
//                if ( commentGraph1.size() == commentGraph2.size() ) {
//                    return commentGraph1.getDate().compareTo(commentGraph1.getDate());
//                }
//
//                return ((Integer) commentGraph1.size() ).compareTo( commentGraph2.size() );
//            }
//        });

        Map modelMap = new HashMap();
        modelMap.put("annotations", annotations);

        return new ModelAndView("annotations", modelMap);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView showIndexFiles()
    {
        return new ModelAndView("main");
    }

    @RequestMapping(value = "indexList", method = RequestMethod.GET)
    public ModelAndView indexFiles()
    {
        return new ModelAndView("indexList");
    }

    @RequestMapping(value = "annotations/remove", method = RequestMethod.POST)
    @Transactional
    public
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void removeAnnotation(
            @RequestParam String id
    )
    {

        logger.info("**** removing annotation for file=" + id);

        final Annotation audioAnnotation = annotationRepository.findOne(id);

        final String indexSummaryId = audioAnnotation.getIndexSummary().getUid();
        final IndexSummary indexSummary = indexSummaryRepository.findOne(indexSummaryId);
        indexSummary.getAnnotations().remove(audioAnnotation);

        indexSummaryRepository.save(indexSummary);
    }

    @RequestMapping(value = "annotations/add", method = RequestMethod.POST)
    @Transactional
    public
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void addAnnotation(
            @RequestParam String id,
            @RequestParam String text,
            @RequestParam int startX,
            @RequestParam int endX
    )
    {

        logger.info("**** adding comment for file=" + id + " region(" + startX + "," + endX + ") with text=" + text);

        final IndexSummary indexSummary = indexSummaryRepository.findOne(id);
        indexSummary.getAnnotations().add(new Annotation(text, Math.min(startX, endX), Math.max(startX, endX)));

        indexSummaryRepository.save(indexSummary);
    }
}
