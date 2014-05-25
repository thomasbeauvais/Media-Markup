package org.branch.annotation.audio;

import org.apache.log4j.Logger;
import org.branch.annotation.audio.api.PersistenceEngine;
import org.branch.annotation.audio.api.AudioStreamIndexer;
import org.branch.annotation.audio.model.Sample;
import org.branch.annotation.audio.model.jpa.Comment;
import org.branch.annotation.audio.model.jpa.IndexSamples;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.annotation.Rollback;

import java.io.*;
import java.util.List;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: tbeauvais
 * Date: 3/7/12
 * Time: 2:34 PM
 * To change this template use File | Settings | File Templates.
 */
@Ignore
public class IndexFileGenerator
{

    private static Logger LOGGER = Logger.getLogger(IndexDriver.class);

    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

    //    public static final String PATH_FILE_PERSISTENCE_BASE   = "/home/tbeauvais/Development/personal/Media-Markup/server/data/filePersistenceDir3";
    public static final String PATH_AUDIO_FILES = "/home/tbeauvais/Development/personal/Media-Markup/server/data/songs";

    @BeforeClass
    public static void beforeClass()
    {
//        applicationContext = new ClassPathXmlApplicationContext( "applicationContext.xml" );
//
//        final IFilePersistenceConnector connector = new JSONFilePersistenceConnector();
//        final IFilePersistenceConnector connector = new BinaryPersistenceConnector();
//
//        filePersistenceEngine    = new FilePersistenceEngine( PATH_FILE_PERSISTENCE_BASE );
//        filePersistenceEngine.setFilePersistenceConnector( connector );
    }

    @Test
    public void retrieveAll()
    {
        final PersistenceEngine indexEngine = applicationContext.getBean(PersistenceEngine.class);
        final IndexSamples[] indexSummaries = indexEngine.loadAll(IndexSamples.class);
        final IndexSamples indexSamples = indexSummaries[0];

        for (Sample sample : indexSamples.getSamples())
        {
            System.out.print(sample.getValue());
        }
    }

    @Test
    @Rollback(false)
    public void test()
    {
        final AudioStreamIndexer audioStreamIndexer = applicationContext.getBean(AudioStreamIndexer.class);

        final File audioDirectory = new File(PATH_AUDIO_FILES);
        final File[] audioFiles = audioDirectory.listFiles(new FilenameFilter()
        {
            public boolean accept(File dir, String name)
            {
                return name.endsWith(".mp3");
            }
        });

        for (File audioFile : audioFiles)
        {
            final String uid = audioFile.getName().substring(0, audioFile.getName().length() - 4);

            LOGGER.info("*** Attempting to create index file for: " + audioFile.getAbsolutePath());

            InputStream inputStream = null;

            try
            {
                inputStream = new FileInputStream(audioFile);

                final IndexSamples indexSummary = audioStreamIndexer.createIndex(inputStream);
                indexSummary.setUid(uid);

                final List<Comment> comments = new Vector<Comment>();
                comments.add(new Comment("sample text one", 100, 1000));
                comments.add(new Comment("sample text two", 100, 1000));

                indexSummary.setComments(comments);

                LOGGER.info("*** Created SampleList: " + uid);

                LOGGER.info("*** Attempting to save SampleList: " + uid);

                applicationContext.getBean(PersistenceEngine.class).save(indexSummary);

                LOGGER.info("*** Creation of index file complete for: " + uid);
            }
            catch (FileNotFoundException e)
            {
                LOGGER.warn("*** Error creating index file: " + audioFile.getAbsolutePath(), e);
            }
            finally
            {
                try
                {
                    inputStream.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
