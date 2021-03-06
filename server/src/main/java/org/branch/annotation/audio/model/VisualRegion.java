package org.branch.annotation.audio.model;

/**
 * Created with IntelliJ IDEA.
 * User: tbeauvais
 * Date: 5/27/12
 * Time: 7:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class VisualRegion {

    public int startX;
    public int endX;
    public int r;
    public int g;
    public int b;

    private String uid;

    public VisualRegion() {

    }

    public VisualRegion( String uid, int startX, int endX, int r, int g, int b) {
        this.uid = uid;
        this.startX = startX;
        this.endX = endX;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartY(int startX) {
        this.startX = startX;
    }

    public int getEndX() {
        return endX;
    }

    public void setEndY(int endX) {
        this.endX = endX;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public String getUid() {
        return this.uid;
    }
}

