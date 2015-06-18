package com.ponin.hwp.domain;

import java.io.File;

/**
 * Created by Administrator on 2015-06-08.
 */
public class ImageBean {
    File path;
    int x;
    int y;
    int width;
    int height;

    public ImageBean(File path, int x, int y, int width, int height) {
        this.path = path;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public File getPath() {
        return path;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "ImageBean{" +
                "path=" + path +
                ", x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
