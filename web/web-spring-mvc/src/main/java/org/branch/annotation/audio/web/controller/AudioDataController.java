package org.branch.annotation.audio.web.controller;

import org.apache.log4j.Logger;
import org.branch.annotation.audio.io.FileStore;
import org.branch.annotation.audio.jpa.IndexSamplesRepository;
import org.branch.annotation.audio.model.jpa.IndexSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

import static org.apache.commons.io.IOUtils.*;

/**
 * Created with IntelliJ IDEA.
 * User: tbeauvais
 * Date: 7/4/12
 * Time: 1:02 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/audioData")
public class AudioDataController extends DefaultSpringController
{

    @Autowired
    public IndexSamplesRepository indexSamplesRepository;

    @Autowired
    private FileStore fileStore;

    private static Logger logger = Logger.getLogger("org.branch.annotation.audio");
    private static final int BUFF_SIZE = 4096;

    @RequestMapping(value = "/music", method = RequestMethod.GET)
    public ModelAndView showIndexFiles()
    {
        return new ModelAndView("music");
    }

    @RequestMapping(method = RequestMethod.GET)
    @Transactional
    public void doGet(@RequestParam String id, @RequestParam(defaultValue = "0") int startPos, HttpServletResponse response) throws Exception
    {
        final IndexSummary indexSummary = indexSamplesRepository.findOne(id);

        final InputStream inputStream = fileStore.getInputStream(indexSummary.getAudioFileUid());

        final int len = inputStream.available();

        response.setContentType("audio/mpeg");
        response.setContentLength(len);

        copy(inputStream, response.getOutputStream());
    }
}
