package org.branch.annotation.audio.model;

/**
 * Created with IntelliJ IDEA.
 * User: tbeauvais
 * Date: 5/27/12
 * Time: 11:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class VisualParameters {
    private int width;
    private int zoom;
    private int center;

    public int getWidth() {
        return width;
    }

    public int getZoom() {
        return zoom;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setZoom(int height) {
        this.zoom = height;
    }

    public void setCenter(int center)
    {
        this.center = center;
    }

    public int getCenter()
    {
        return center;
    }
}
