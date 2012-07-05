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

import com.company.annotation.audio.pojos.IndexSummary;
import com.company.annotation.audio.services.AudioAnnotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
public class AudioIndexController {
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private AudioAnnotationService audioAnnotationService;

    @ModelAttribute("allIndexFiles")
    public List<IndexSummary> allIndexFiles() {
        return Arrays.asList(this.audioAnnotationService.loadAll());
    }

    @RequestMapping({"/"})
    public String showkalsjdf() {
        return "audio-annotation";
    }

//    @RequestMapping("/hello")
//    public ModelAndView helloWorld() {
//        return new ModelAndView("hello", "message", "Spring MVC Demo");
//    }

    @RequestMapping(value = "/time", method = RequestMethod.GET)
    public @ResponseBody String getTime(@RequestParam String name) {
        String result = "Time for " + name + " is " + new Date().toString();
        return result;
    }
}
