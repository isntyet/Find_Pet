package com.ks.hihi.haha.utill;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jo on 2017-05-15.
 */

public class Preferences {

    protected SharedPreferences preferences;
    protected SharedPreferences.Editor editor;

    public Preferences(Context context) {
        this.preferences = context.getSharedPreferences("FindPet", context.MODE_PRIVATE);
        this.editor = this.preferences.edit();
    }

    public boolean getBoolean(String key) {
        return this.preferences.getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return this.preferences.getBoolean(key, defValue);
    }

    public void putBoolean(String key, boolean value) {
        this.editor.putBoolean(key, value);
        this.editor.commit();
    }

    public String getString(String key) {
        return this.preferences.getString(key, "");
    }

    public String getString(String key, String defValue) {
        return this.preferences.getString(key, defValue);
    }

    public void putString(String key, String value) {
        this.editor.putString(key, value);
        this.editor.commit();
    }

    public int getInt(String key) {
        return this.preferences.getInt(key, 0);
    }

    public int getInt(String key, int defValue) {
        return this.preferences.getInt(key, defValue);
    }

    public void putInt(String key, int value) {
        this.editor.putInt(key, value);
        this.editor.commit();
    }
    /////////////////////////////////////////////////////////

    //밑에꺼 참조하셈
    //쓰는법

    //public Preferences sp;
    //sp = new Preferences(this);

    //넣을때
    //sp.setKakaoProfileUrl(profileUrl);

    //뺄때
    //sp.getKakaoProfileUrl();

    //밑에꺼 지워도됨

    //////////////////////////////////////////////////////////////////////////////////////////

    public String getUserId() {
        return this.getString("USER_ID", "");
    }

    public void setUserId(String value) {
        this.putString("USER_ID", value);
    }

    public String getUserName() {
        return this.getString("USER_NAME", "");
    }

    public void setUserName(String value) {
        this.putString("USER_NAME", value);
    }

    public String getUserToken() {
        return this.getString("USER_TOKEN", "");
    }

    public void setUserToken(String value) {
        this.putString("USER_TOKEN", value);
    }

    public String getKakaoNickname() {
        return this.getString("KAKAO_NICKNAME", "");
    }

    public void setKakaoNickname(String value) {
        this.putString("KAKAO_NICKNAME", value);
    }

    public String getKakaoProfileUrl() {
        return this.getString("KAKAO_PROFILE_URL", "");
    }

    public void setKakaoProfileUrl(String value) {
        this.putString("KAKAO_PROFILE_URL", value);
    }

    public String getKakaoThumbUrl() {
        return this.getString("KAKAO_THUMB_URL", "");
    }

    public void setKakaoThumbUrl(String value) {
        this.putString("KAKAO_THUMB_URL", value);
    }
}

