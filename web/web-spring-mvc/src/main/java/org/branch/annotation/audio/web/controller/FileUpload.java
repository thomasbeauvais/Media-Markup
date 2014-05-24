package org.branch.annotation.audio.web.controller;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created with IntelliJ IDEA.
 * User: tbeauvais
 * Date: 8/3/12
 * Time: 12:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class FileUpload {

    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
