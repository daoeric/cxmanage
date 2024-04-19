package com.ruoyi.common.vo.payment;

import lombok.Data;

@Data
public class WithdrawalResult {

    public int code;

    public String msg;

    /**
     * 本系统订单号
     */
    public String orderNo;

    /**
     * 上游订单号
     */
    public String upOrderNo;

}
