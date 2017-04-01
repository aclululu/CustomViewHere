package com.shli.here.customview.slip;

/**
 * 路径  标题
 * Created by shli on 2016-09-02.
 */
public class BaseEntry {
    public String pathurl;
    public String title;
    public BaseEntry(){
    }

    public BaseEntry(String title, String pathurl) {
        this.title = title;
        this.pathurl = pathurl;
    }

    @Override
    public String toString() {
        return "BaseEntry{" +
                "pathurl='" + pathurl + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
