package com.cleareyes.twitterapp.entities;

import com.cleareyes.twitterapp.misc.Enums;

import java.io.Serializable;

public class AsyncReturnType implements Serializable {

    private String response;
    private Enums.CallType callType;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Enums.CallType getCallType() {
        return callType;
    }

    public void setCallType(Enums.CallType callType) {
        this.callType = callType;
    }

}
