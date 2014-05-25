package org.branch.annotation.audio.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class DefaultController
{
    @RequestMapping
    public String main()
    {
        return "main";
    }
}
