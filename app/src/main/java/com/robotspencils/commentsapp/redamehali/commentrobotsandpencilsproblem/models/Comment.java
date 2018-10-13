package com.robotspencils.commentsapp.redamehali.commentrobotsandpencilsproblem.models;

/**
 * Created by Reda Mehali on 10/12/18.
 *
 * Class for Comment Data Object Model
 * Contains 4 data elements for now.
 *
 * * Project: Robots & Pencils Technical Problem
 */
public class Comment {

    private String userName;
    private long commentPostedDate;
    private String userPhotoUrl;
    private String commentText;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getCommentPostedDate() {
        return commentPostedDate;
    }

    public void setCommentPostedDate(long commentPostedDate) {
        this.commentPostedDate = commentPostedDate;
    }

    public String getUserPhotoUrl() {
        return userPhotoUrl;
    }

    public void setUserPhotoUrl(String userPhotoUrl) {
        this.userPhotoUrl = userPhotoUrl;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}
