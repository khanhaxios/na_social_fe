package com.kt.na_social.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Feed {

    private int id;
    private String caption;
    private int likeCount;
    private int commentCount;
    private String createdAt;
    private String image;
    private String author;
    private String authorAvatar;
    private List<String> userReacted = new ArrayList<>();

    public Feed() {
    }

    public Feed(int id, String caption, int likeCount, int commentCount, String createdAt, String image, String author, String authorAvatar, List<String> userReacted) {
        this.id = id;
        this.caption = caption;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.createdAt = createdAt;
        this.image = image;
        this.author = author;
        this.authorAvatar = authorAvatar;
        this.userReacted = userReacted;
    }

    public List<String> getUserReacted() {
        return userReacted;
    }

    public void setUserReacted(List<String> userReacted) {
        this.userReacted = userReacted;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorAvatar() {
        return authorAvatar;
    }

    public void setAuthorAvatar(String authorAvatar) {
        this.authorAvatar = authorAvatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
