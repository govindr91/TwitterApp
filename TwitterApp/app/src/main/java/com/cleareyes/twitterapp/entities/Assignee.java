package com.cleareyes.twitterapp.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Assignee implements Serializable {

    @SerializedName("login")
    private String assigneeLogin;

    public String getAssigneeLogin() {
        return assigneeLogin;
    }

    public void setAssigneeLogin(String assigneeLogin) {
        this.assigneeLogin = assigneeLogin;
    }
}
