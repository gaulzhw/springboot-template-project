package com.template.springboot.util;

public enum ServiceStatusEnum {
    //未登录
    UNLOGIN("0001"),
    //非法的token
    ILLEGAL_TOKEN("0002");

    public String code;

    private ServiceStatusEnum(String code) {
        this.code = code;
    }
}
