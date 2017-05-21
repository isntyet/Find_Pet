package com.ks.hihi.haha.items;

/**
 * Created by jo on 2017-05-19.
 */

public class Code {

    private String kind;
    private String kind_num;

    public Code(String kind, String kind_num){
        this.kind = kind;
        this.kind_num = kind_num;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getKind_num() {
        return kind_num;
    }

    public void setKind_num(String kind_num) {
        this.kind_num = kind_num;
    }
}
