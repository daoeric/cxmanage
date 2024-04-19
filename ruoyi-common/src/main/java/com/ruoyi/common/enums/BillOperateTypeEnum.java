package com.ruoyi.common.enums;

/**
 *  资金操作
 * 
 * @author yf
 */
public enum BillOperateTypeEnum
{
    DEPOSIT(1,"存款"),WITHDRAWAL(2,"提款"),MANUAL_IN(3,"手动入款"),MANUAL_OUT(4,"手动出款"),REJECT(5,"代付驳回"),
    COMMISSION(6,"佣金");

    private final int code;
    private final String info;

    BillOperateTypeEnum(int code, String info)
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
