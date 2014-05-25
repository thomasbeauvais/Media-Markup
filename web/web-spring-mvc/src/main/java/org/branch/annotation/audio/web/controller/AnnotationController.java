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

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.branch.annotation.audio.dao.AnnotationRepository;
import org.branch.annotation.audio.dao.SummaryRepository;
import org.branch.annotation.audio.model.dao.Annotation;
import org.branch.annotation.audio.model.dao.Summary;
import org.branch.common.UncheckedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Controller
@RequestMapping(value = "annotations")
public class AnnotationController extends DefaultSpringController
{
    private static Logger logger = Logger.getLogger("org.branch.annotation.audio");

    @Autowired
    private AnnotationRepository annotationRepository;

    @Autowired
    private SummaryRepository indexSummaryRepository;

    @Transactional
    @RequestMapping
    public String all(@RequestParam String indexId, Model model)
    {
        final List<Annotation> annotations = annotationRepository.findAllForSummary(indexId);

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

        model.addAttribute("annotations", annotations);

        return "annotations";
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Transactional
    public void delete(@RequestParam String id)
    {
        logger.info("**** deleting annotation id=" + id);

        annotationRepository.delete(id);
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Transactional
    public void save(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String indexId,
            @RequestParam String text,
            @RequestParam int startX,
            @RequestParam int endX
    )
    {
        final Annotation annotation;
        if (!StringUtils.isEmpty(id))
        {
            logger.info("**** updating existing annotation for id=" + id + " with region(" + startX + "," + endX + ") with text=" + text);

            annotation = annotationRepository.findOne(id);
        }
        else if (!StringUtils.isEmpty(indexId))
        {
            annotation = new Annotation();
            annotation.setSummary(new Summary());
        }
        else
        {
            throw new UncheckedException("Must supply parameter of either 'indexId' or 'id' to modify/add an annotation");
        }

        annotation.setText(text);
        annotation.setStartPos(startX);
        annotation.setEndPos(endX);

        annotationRepository.save(annotation);
    }
}
