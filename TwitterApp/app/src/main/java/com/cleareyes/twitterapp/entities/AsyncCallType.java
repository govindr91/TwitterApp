package com.cleareyes.twitterapp.entities;

import com.cleareyes.twitterapp.misc.Enums;

import java.io.Serializable;

public class AsyncCallType implements Serializable {

    private String url;
    private Enums.CallType callType;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Enums.CallType getCallType() {
        return callType;
    }

    public void setCallType(Enums.CallType callType) {
        this.callType = callType;
    }
}
