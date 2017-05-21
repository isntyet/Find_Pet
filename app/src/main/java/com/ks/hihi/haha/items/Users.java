package com.ks.hihi.haha.items;

import java.io.Serializable;

/**
 * Created by DongChul on 2017. 5. 20..
 */

public class Users implements Serializable {
    private String user_id;
    private String user_token;
    private String user_name;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_token() {
        return user_token;
    }

    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
