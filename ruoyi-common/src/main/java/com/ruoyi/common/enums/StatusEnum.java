package com.ruoyi.common.enums;

/**
 * 用户状态
 * 
 * @author qin-chat
 */
public enum StatusEnum
{
    OK(0, "正常"), DISABLE(1, "停用");

    private final int code;
    private final String info;

    StatusEnum(int code, String info)
    {
        this.code = code;
        this.info = info;
    }

    public int getCode()
    {
        return code;
    }

    public String getInfo()
    {
        return info;
    }
}
