package com.pein.teacher.common;

/**
 * Created by qiuwei on 2017/2/27.
 */
public class OcrResponse {
    private String origin;

    private String dest;

    private String content;

    public OcrResponse(String origin, String dest, String content) {
        this.origin = origin;
        this.dest = dest;
        this.content = content;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
