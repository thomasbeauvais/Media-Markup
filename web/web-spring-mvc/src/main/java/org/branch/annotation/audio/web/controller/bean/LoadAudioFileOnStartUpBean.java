package org.branch.annotation.audio.web.controller.bean;

import org.apache.log4j.Logger;
import org.branch.annotation.audio.web.controller.FileUploadProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: tbeauvais
 * Date: 9/5/12
 * Time: 10:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class LoadAudioFileOnStartUpBean
{
    private static Logger logger = Logger.getLogger("org.branch.annotation.audio");

    @Autowired
    FileUploadProcessor fileUploadProcessor;

    @Transactional
    public void load() throws Exception
    {
        logger.info("*** Triggering auto import.");

        for (final File file : files)
        {
            final Map<String, String> metadata = new HashMap<String, String>();
            metadata.put("originalFilename", file.getName());

            fileUploadProcessor.uploadFile(metadata, new FileInputStream(file));
        }
    }

    private List<File> files;

    public void setFiles(List<File> files)
    {
        this.files = files;
    }

    public List<File> getFiles()
    {
        return files;
    }
}
