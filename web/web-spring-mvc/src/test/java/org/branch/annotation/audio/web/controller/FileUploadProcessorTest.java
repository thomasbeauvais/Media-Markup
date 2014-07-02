package org.branch.annotation.audio.web.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:META-INF/spring-test.xml"})
public class FileUploadProcessorTest
{
    @Autowired
    FileUploadProcessor fileUploadProcessor;

    @Value("${test.auto.upload}")
    public String originalFilename;

    @Test
    public void testUploadFile() throws Exception
    {
        final Map<String, String> metadata = new HashMap<String, String>();
        metadata.put("originalFilename", originalFilename);

        fileUploadProcessor.uploadFile(metadata, new FileInputStream(originalFilename));
    }
}