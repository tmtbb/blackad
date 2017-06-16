package com.yundian.blackcard.android.model;

import java.util.List;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-16 13:53
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class DynamicModel {

    private long createTime;
    private String nickName;
    private String message;
    private long userId;
    private String headUrl;
    private String id;
    private int isTop;
    private int isLike;
    private int likeNum;
    private int commentNum;
    private List<CircleMessageImgs> circleMessageImgs;


    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsTop() {
        return isTop;
    }

    public void setIsTop(int isTop) {
        this.isTop = isTop;
    }

    public int getIsLike() {
        return isLike;
    }

    public void setIsLike(int isLike) {
        this.isLike = isLike;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public List<CircleMessageImgs> getCircleMessageImgs() {
        return circleMessageImgs;
    }

    public void setCircleMessageImgs(List<CircleMessageImgs> circleMessageImgs) {
        this.circleMessageImgs = circleMessageImgs;
    }

    private class CircleMessageImgs {
        private String img;
        private String size;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }
    }
}
