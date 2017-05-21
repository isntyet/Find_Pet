package com.ks.hihi.haha.items;

import java.io.Serializable;

/**
 * Created by jo on 2017-05-05.
 */

public class BaseObj implements Serializable {
    private String _id;
    private String title;
    private String img_org;
    private String img_std;
    private String kind;
    private String kind_detail;
    private String date;
    private String sex;
    private String age;
    private String place;
    private String place_detail;
    private String color;
    private String feature;
    private String process;
    private String regis_num;
    private String rfid_cd;
    private String insert_date;
    private String lat;
    private String lng;
    private String user_id;
    private String user_name;

    public BaseObj(){

    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg_org() {
        return img_org;
    }

    public void setImg_org(String img_org) {
        this.img_org = img_org;
    }

    public String getImg_std() {
        return img_std;
    }

    public void setImg_std(String img_std) {
        this.img_std = img_std;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getKind_detail() {
        return kind_detail;
    }

    public void setKind_detail(String kind_detail) {
        this.kind_detail = kind_detail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPlace_detail() {
        return place_detail;
    }

    public void setPlace_detail(String place_detail) {
        this.place_detail = place_detail;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getRegis_num() {
        return regis_num;
    }

    public void setRegis_num(String regis_num) {
        this.regis_num = regis_num;
    }

    public String getRfid_cd() {
        return rfid_cd;
    }

    public void setRfid_cd(String rfid_cd) {
        this.rfid_cd = rfid_cd;
    }

    public String getInsert_date() {
        return insert_date;
    }

    public void setInsert_date(String insert_date) {
        this.insert_date = insert_date;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
