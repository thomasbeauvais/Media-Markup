package org.branch.annotation.audio.web.controller;

import org.branch.annotation.audio.dao.IndexSummaryRepository;
import org.branch.annotation.audio.model.dao.IndexSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("index")
public class IndexController
{
    @Autowired
    private IndexSummaryRepository indexSummaryRepository;

    @RequestMapping("all")
    @ResponseBody
    public List<IndexSummary> getAllIndexSummaries()
    {
        return indexSummaryRepository.findAll();
    }
}
