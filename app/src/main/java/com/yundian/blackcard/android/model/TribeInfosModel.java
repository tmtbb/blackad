package com.yundian.blackcard.android.model;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2017-06-21 14:30
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class TribeInfosModel extends BaseModel {

    private TribeInfoModel tribeInfo;
    private MemberInfoModel memberInfo;

    public TribeInfoModel getTribeInfo() {
        return tribeInfo;
    }

    public void setTribeInfo(TribeInfoModel tribeInfo) {
        this.tribeInfo = tribeInfo;
    }

    public MemberInfoModel getMemberInfo() {
        return memberInfo;
    }

    public void setMemberInfo(MemberInfoModel memberInfo) {
        this.memberInfo = memberInfo;
    }

    public class MemberInfoModel extends BaseModel{


        /**
         * status : 1
         * identity : 0
         */

        private int status;
        private int identity;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getIdentity() {
            return identity;
        }

        public void setIdentity(int identity) {
            this.identity = identity;
        }
    }
    public class TribeInfoModel  extends BaseModel{
        /**
         * province : 浙江省
         * city : 杭州市
         * name : test1
         * industry : 电商
         * coverUrl : http://xxx.com/static/tribe/201706/20170619213848948000.jpg
         * status : 1
         * memberNum : 0
         * id : 1BAE46344BB54586A97ACF691703C398
         * description : test1
         */

        private String province;
        private String city;
        private String name;
        private String industry;
        private String coverUrl;
        private int status;
        private int memberNum;
        private String id;
        private String description;

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        public String getCoverUrl() {
            return coverUrl;
        }

        public void setCoverUrl(String coverUrl) {
            this.coverUrl = coverUrl;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getMemberNum() {
            return memberNum;
        }

        public void setMemberNum(int memberNum) {
            this.memberNum = memberNum;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
