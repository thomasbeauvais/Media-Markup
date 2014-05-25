package org.branch.annotation.audio.model.dao;

import org.branch.common.data.Identifiable;
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
public class Annotation implements Identifiable {
    private String text;
    private long startPos;
    private long endPos;
    private Date date;

    @OneToMany( cascade = CascadeType.ALL )
    private List<Annotation> children = new Vector<Annotation>();


    @Id
    @GeneratedValue( generator= "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String uid;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinTable( name = "IndexSummary_Comment", joinColumns = {@JoinColumn( name = "indexSummary_uid", updatable = false, insertable = false)})
    private IndexSummary indexSummary;

    public Annotation() {

    }

    public Annotation(String text, int start, int end) {
        this( text, start, end, new Date() );
    }

    public Annotation(String text, int start, int end, Date date) {
        this.text = text;
        this.startPos = start;
        this.endPos = end;
        this.date = date;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid( String uid ) {
        this.uid = uid;
    }

    public IndexSummary getIndexSummary() {
        return indexSummary;
    }

    public void setIndexSummary(IndexSummary indexSummary) {
        this.indexSummary = indexSummary;
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

    public List<Annotation> getChildren() {
        return children;
    }

    public void setChildren(List<Annotation> children) {
        this.children = children;
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
        int size = children.size();

        if ( recursive ) {
            for ( Annotation childAnnotation : children) {
                size += childAnnotation.size( true );
            }
        }

        return size;
    }
}
