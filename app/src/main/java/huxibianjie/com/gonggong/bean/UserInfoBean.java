package huxibianjie.com.gonggong.bean;

import java.io.Serializable;

/**
 * Created by wangtao on 2018/6/22.
 */

public class UserInfoBean implements Serializable {
    /**
     * bgImg :
     * birthday : 2018-06-18
     * constellation : 0
     * description :
     * gender : 2
     * head :
     * location :
     * token : 59255c60-2615-46ca-8dac-ac62d3fc06a5
     * uid : 1132
     */

    private String bgImg;
    private String birthday;
    private int constellation;
    private String description;
    private int gender;
    private String head;
    private String location;
    private String token;
    private int uid;

    public String getBgImg() {
        return bgImg;
    }

    public void setBgImg(String bgImg) {
        this.bgImg = bgImg;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getConstellation() {
        return constellation;
    }

    public void setConstellation(int constellation) {
        this.constellation = constellation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
