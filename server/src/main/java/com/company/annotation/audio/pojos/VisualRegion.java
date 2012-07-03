package com.company.annotation.audio.pojos;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: tbeauvais
 * Date: 5/27/12
 * Time: 7:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class VisualRegion {

    public int start;
    public int stop;
    public int r;
    public int g;
    public int b;

    public VisualRegion(int start, int stop, int r, int g, int b) {
        this.start = start;
        this.stop = stop;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getStop() {
        return stop;
    }

    public void setStop(int stop) {
        this.stop = stop;
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
}

