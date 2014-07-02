package org.branch.annotation.audio.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PassthruController
{
    @RequestMapping(value="/view/{passthru}")
    public String passthru(@PathVariable String passthru)
    {
        return passthru;
    }

}
