//package com.company.common;
//
//import com.google.appengine.api.blobstore.BlobKey;
//import com.google.appengine.api.users.User;
//
//import javax.jdo.annotations.*;
//import java.util.Date;
//
///**
// * Created by IntelliJ IDEA.
// * User: thomasbeauvais
// * Date: Aug 30, 2010
// * Time: 10:32:12 PM
// * To change this template use File | Settings | File Templates.
// */
//@PersistenceCapable(identityType = IdentityType.APPLICATION)
//public class AudioFile {
//    @PrimaryKey
//    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
//    Long id;
//
//    @Persistent
//    User user;
//
//    @Persistent
//    private BlobKey blobKey;
//
//    @Persistent
//    private String filename;
//
//    @Persistent
//    private Long size;
//
//    @Persistent
//    private Date dateUpload;
//
//    @Persistent
//    private Long indexFileId;
//
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public void setBlobKeyString(String blobKeyString) {
//        this.blobKey = new BlobKey( blobKeyString );
//    }
//
//    public String getBlobKeyString() {
//        return null == blobKey ? null : blobKey.getKeyString();
//    }
//
//    public String getFilename() {
//        return filename;
//    }
//
//    public void setFilename(String filename) {
//        this.filename = filename;
//    }
//
//    public Long getSize() {
//        return size;
//    }
//
//    public void setSize(Long size) {
//        this.size = size;
//    }
//
//    public Date getDateUpload() {
//        return dateUpload;
//    }
//
//    public void setDateUpload(Date dateUpload) {
//        this.dateUpload = dateUpload;
//    }
//
//    public void setIndexFileId(Long id) {
//        indexFileId = id;
//    }
//
//    public Long getIndexFileId() {
//        return indexFileId;
//    }
//
//    public BlobKey getBlobKey() {
//        return blobKey;
//    }
//
//    public void setBlobKey( BlobKey blobKey ) {
//        this.blobKey = blobKey;
//    }
//}
