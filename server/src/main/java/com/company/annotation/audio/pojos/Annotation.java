package com.company.annotation.audio.pojos;

import org.jetbrains.annotations.NotNull;

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
public class Annotation {
    private String text;

    private String idIndexFile;

    private Date date;

    private List<Annotation> childAnnotations = new Vector<Annotation>();

    public Annotation() {

    }

    public Annotation( @NotNull String idIndexFile, @NotNull String text, @NotNull Date date ) {
        this.idIndexFile = idIndexFile;
        this.text = text;
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIdIndexFile() {
        return idIndexFile;
    }

    public void setIdIndexFile(String idIndexFile) {
        this.idIndexFile = idIndexFile;
    }

    public Date getDate() {
        return date;
    }

    public void setDate( Date date ) {
        this.date = date;
    }

    public int size() {
        return size( true );
    }

    public int size( boolean recursive ) {
        int size = childAnnotations.size();

        if ( recursive ) {
            for ( Annotation childAnnotation : childAnnotations ) {
                size += childAnnotation.size( true );
            }
        }

        return size;
    }
}
