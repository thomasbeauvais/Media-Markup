/*
 *  Copyright 2010 Blue Lotus Software, LLC.
 *  Copyright 2010 John Yeary <jyeary@bluelotussoftware.com>.
 *  Copyright 2010 Allan O'Driscoll
 *
 * Dual Licensed MIT and GPL v.2 
 *
 * The MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 *
 * The GNU General Public License (GPL) Version 2, June 1991
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation; version 2 of the License.

 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License along with this program;
 * if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package com.company.annotation.audio.web.controller;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.company.annotation.audio.api.IIndexEngine;
import com.company.annotation.audio.api.IPersistenceEngine;
import com.company.annotation.audio.pojos.AudioFile;
import com.company.annotation.audio.pojos.IndexWithSamples;
import com.company.annotation.audio.pojos.SampleList;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static com.company.annotation.audio.util.StringUtils.getTimeStringFromSeconds;

/**
 * Reads an <code>application/octet-stream</code> and writes it to a file.
 * @author John Yeary <jyeary@bluelotussoftware.com>
 * @author Allan O'Driscoll
 * @version 1.0
 */
@Controller
@RequestMapping("/upload")
public class UploadServlet extends DefaultSpringController {

    private static Logger logger = Logger.getLogger( "com.company.annotation.audio" );

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @RequestMapping( method = RequestMethod.POST )
    protected void doPost(@RequestParam(defaultValue = "") String qqfile, HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        PrintWriter writer = null;

        try {
            writer = response.getWriter();
        } catch (IOException ex) {
            logger.error(UploadServlet.class.getName() + "has thrown an exception: " + ex.getMessage(), ex);
        }

        try {
            uploadFile( request, response );
            response.setStatus(response.SC_OK);
            writer.print("{success: true}");
        } catch (Exception ex) {
            response.setStatus(response.SC_INTERNAL_SERVER_ERROR);
            writer.print("{success: false}");
            logger.error(UploadServlet.class.getName() + "has thrown an exception: " + ex.getMessage(), ex);
        } finally {
        }

        writer.flush();
        writer.close();
    }

    private IPersistenceEngine persistenceEngine;

    @Autowired
    public void setPersistenceEngine( IPersistenceEngine persistenceEngine ) {
        this.persistenceEngine = persistenceEngine;
    }

    private IIndexEngine indexEngine;

    @Autowired
    public void setIndexEngine( IIndexEngine indexEngine ) {
        this.indexEngine = indexEngine;
    }

    public void uploadFile( HttpServletRequest request, HttpServletResponse response ) throws Exception {


        InputStream inputStream = request.getInputStream();

        final String filename   = request.getHeader("X-File-Name");

        final String name       = "test-" + System.currentTimeMillis();
        final long start        = System.currentTimeMillis();

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            IOUtils.copy( inputStream, byteArrayOutputStream);

            final byte[] bytes              = byteArrayOutputStream.toByteArray();
            final SampleList sampleList     = indexEngine.createIndexForAudioStream( new ByteArrayInputStream( bytes ), name );

            final IndexWithSamples indexSummary = sampleList.getIndexSummary();

            logger.info("*** Created SampleList: " + name);

            logger.info("*** Creating AudioFile: " + name);

            final AudioFile audioFile = new AudioFile();
            audioFile.setBytes( bytes );

            logger.info("*** Created AudioFile: " + name);

            logger.info("*** Attempting to save AudioFile: " + name);

            final AudioFile saved = persistenceEngine.save( audioFile );

            // TODO:  Add this
//                indexSummary.setOriginalFilename( fileName );
            indexSummary.setAudioFileUid( saved.getUid() );

            logger.info("*** Attempting to save SampleList: " + name);

            persistenceEngine.save( indexSummary );

            logger.info("*** Creation of index file complete for: " + name);
        } finally {
            logger.info("Time to upload " + name + " was " + getTimeStringFromSeconds(start - System.currentTimeMillis()));
        }
    }
}