package com.jarvislin.waterrestrictioninfo.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jarvis Lin on 2015/3/22.
 */
public class DetailNews {
    private String detail;
    private HashMap<String, String> attachment = new HashMap<String, String>();

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public HashMap<String, String> getAttachment() {
        return attachment;
    }

    public void putAttachment(String fileName, String fileUrl){
        this.attachment.put(fileName, fileUrl);
    }
}
