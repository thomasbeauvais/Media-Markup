package com.company.drivers;

import com.company.common.api.IDriver;
import com.company.common.api.IIndexEngine;
import com.company.common.api.IPersistenceEngine;
import com.company.common.pojos.IndexObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: tbeauvais
 * Date: 11/6/11
 * Time: 8:56 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class CreateIndexFile implements IDriver {
    private IIndexEngine indexEngine;

    private IPersistenceEngine persistenceEngine;

    @Autowired
    public void setIndexEngine( IIndexEngine indexEngine ) {
        this.indexEngine = indexEngine;
    }

    @Autowired
    public void setPersistenceEngine(IPersistenceEngine persistenceEngine) {
        this.persistenceEngine = persistenceEngine;
    }

    public void go() throws Exception {
        final String strRootPath = new File("").getAbsolutePath();
//        LOGGER.info("The root is: " + strRootPath);

        final String strTest                = "large";
//        final String strTest                = "small";

        // Setting up a test file
        final String strFilePath            = "server/data/" + strTest + ".mp3";
        final File fAudioFile               = new File(strRootPath, strFilePath);
        final String strFileName            = "test-file-" + strTest;

        final InputStream inputStream       = new FileInputStream(fAudioFile);

        assert fAudioFile.exists();

        final IndexObject indexObject       = indexEngine.makeIndexObject( inputStream, strFileName );
        final String idIndexObject          = strFileName;

        persistenceEngine.save( idIndexObject, indexObject );

        persistenceEngine.load( idIndexObject, IndexObject.class );
    }

    public static void main(String[] args) throws Exception {
        final ApplicationContext appContext = new FileSystemXmlApplicationContext( "server/src/main/resources/applicationContext.xml" );

        appContext.getBean( IDriver.class ).go();
    }
}
