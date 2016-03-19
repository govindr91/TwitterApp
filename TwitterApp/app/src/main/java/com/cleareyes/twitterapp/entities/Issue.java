package com.cleareyes.twitterapp.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Issue implements Serializable {

    @SerializedName("comments_url")
    private String commentsURL;
    @SerializedName("id")
    private long issueID;
    @SerializedName("number")
    private long issueNumber;
    @SerializedName("title")
    private String issueTitle;

    @SerializedName("user")
    private User userDetails;

    @SerializedName("assignee")
    private Assignee assignee;

    @SerializedName("comments")
    private int comments;

    @SerializedName("created_at")
    private String createdAtTimeStamp;
    @SerializedName("updated_at")
    private String updatedAtTimeStamp;

    @SerializedName("body")
    private String body;

    private Date convertedDate;

    public String getCommentsURL() {
        return commentsURL;
    }

    public void setCommentsURL(String commentsURL) {
        this.commentsURL = commentsURL;
    }

    public long getIssueID() {
        return issueID;
    }

    public void setIssueID(long issueID) {
        this.issueID = issueID;
    }

    public long getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(long issueNumber) {
        this.issueNumber = issueNumber;
    }

    public String getIssueTitle() {
        return issueTitle;
    }

    public void setIssueTitle(String issueTitle) {
        this.issueTitle = issueTitle;
    }

    public User getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(User userDetails) {
        this.userDetails = userDetails;
    }

    public Assignee getAssignee() {
        return assignee;
    }

    public void setAssignee(Assignee assignee) {
        this.assignee = assignee;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public String getCreatedAtTimeStamp() {
        return createdAtTimeStamp;
    }

    public void setCreatedAtTimeStamp(String createdAtTimeStamp) {
        this.createdAtTimeStamp = createdAtTimeStamp;
    }

    public String getUpdatedAtTimeStamp() {
        return updatedAtTimeStamp;
    }

    public void setUpdatedAtTimeStamp(String updatedAtTimeStamp) {
        this.updatedAtTimeStamp = updatedAtTimeStamp;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getConvertedDate() {
        return convertedDate;
    }

    public void setConvertedDate(Date convertedDate) {
        this.convertedDate = convertedDate;
    }
}
