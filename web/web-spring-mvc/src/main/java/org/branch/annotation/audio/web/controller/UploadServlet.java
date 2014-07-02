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
package org.branch.annotation.audio.web.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Reads an <code>application/octet-stream</code> and writes it to a file.
 *
 * @author John Yeary <jyeary@bluelotussoftware.com>
 * @author Allan O'Driscoll
 * @version 1.0
 */
@Controller
@RequestMapping("/upload")
public class UploadServlet extends DefaultSpringController
{
    private static Logger logger = Logger.getLogger("org.branch.annotation.audio");

    @Autowired
    private FileUploadProcessor fileUploadProcessor;

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @RequestMapping(method = RequestMethod.POST)
    protected void post(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        final PrintWriter writer = response.getWriter();

        final InputStream inputStream = request.getInputStream();

        final String originalFilename = request.getHeader("X-File-Name");

        final Map<String, String> metadata = new HashMap<String, String>();
        metadata.put("originalFilename", originalFilename);

        fileUploadProcessor.uploadFile(metadata, inputStream);

        writer.print("{success: true}");
    }

}