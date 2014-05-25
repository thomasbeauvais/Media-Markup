package org.branch.annotation.audio.web.controller;

import org.branch.annotation.audio.dao.SummaryRepository;
import org.branch.annotation.audio.model.dao.Summary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("index")
public class IndexController
{
    @Autowired
    private SummaryRepository indexSummaryRepository;

    @RequestMapping("all")
    @ResponseBody
    @Transactional
    public List<Summary> getAllIndexSummaries()
    {
        return indexSummaryRepository.findAll();
    }
}
