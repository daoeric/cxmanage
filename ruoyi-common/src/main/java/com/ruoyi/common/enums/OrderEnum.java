package com.ruoyi.common.enums;

import lombok.Getter;

/**
 * 操作状态
 * 
 * @author qin-chat
 *
 */
@Getter
public enum OrderEnum implements BaseEnum
{
    PENDDING(1,"等待"),SUCCESS(2,"成功"),FAIL(3,"失败");

    int value;
    String message;

    OrderEnum(int code, String message) {
        this.value = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return value;
    }
}
