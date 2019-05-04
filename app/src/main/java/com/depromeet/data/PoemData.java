package com.depromeet.data;

import com.google.gson.annotations.SerializedName;

public class PoemData {
    @SerializedName("id")
    private int id;

    @SerializedName("wordFirst")
    private String wordFirst;

    @SerializedName("wordSecond")
    private String wordSecond;

    @SerializedName("wordThird")
    private String wordThird;

    @SerializedName("likeCount")
    private int likeCount;

    @SerializedName("username")
    private String userName;

    @SerializedName("like")
    private boolean like;

    public PoemData(int id, String wordFirst, String wordSecond, String wordThird, int likeCount, String userName, boolean like) {
        this.id = id;
        this.wordFirst = wordFirst;
        this.wordSecond = wordSecond;
        this.wordThird = wordThird;
        this.likeCount = likeCount;
        this.userName = userName;
        this.like = like;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWordFirst() {
        return wordFirst;
    }

    public void setWordFirst(String wordFirst) {
        this.wordFirst = wordFirst;
    }

    public String getWordSecond() {
        return wordSecond;
    }

    public void setWordSecond(String wordSecond) {
        this.wordSecond = wordSecond;
    }

    public String getWordThird() {
        return wordThird;
    }

    public void setWordThird(String wordThird) {
        this.wordThird = wordThird;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }
}
