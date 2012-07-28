package com.company.annotation.audio.pojos;

import com.company.common.dao.Identifiable;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: tbeauvais
 * Date: 7/6/12
 * Time: 5:00 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Comment implements Identifiable {
    private String text;
    private long startPos;
    private long endPos;
    private Date date;

    private List<Comment> childComments = new Vector<Comment>();
    private String uid;

    public Comment() {

    }

    public Comment(String text, int start, int end) {
        this( text, start, end, new Date() );
    }

    public Comment(String text, int start, int end, Date date) {
        this.text = text;
        this.startPos = start;
        this.endPos = end;
        this.date = date;
    }

    @Id
    @GeneratedValue( generator= "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    public String getUid() {
        return this.uid;
    }

    public void setUid( String uid ) {
        this.uid = uid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate( Date date ) {
        this.date = date;
    }

    @OneToMany
    public List<Comment> getChildComments() {
        return childComments;
    }

    public void setChildComments(List<Comment> childComments) {
        this.childComments = childComments;
    }

    public long getStartPos() {
        return startPos;
    }

    public void setStartPos(long startPos) {
        this.startPos = startPos;
    }

    public long getEndPos() {
        return endPos;
    }

    public void setEndPos(long endPos) {
        this.endPos = endPos;
    }

    public int size() {
        return size( true );
    }

    public int size( boolean recursive ) {
        int size = childComments.size();

        if ( recursive ) {
            for ( Comment childComment : childComments) {
                size += childComment.size( true );
            }
        }

        return size;
    }
}