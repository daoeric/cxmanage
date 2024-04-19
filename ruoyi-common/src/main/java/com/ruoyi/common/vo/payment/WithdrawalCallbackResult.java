package com.ruoyi.common.vo.payment;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithdrawalCallbackResult {

    private int status; // 1-回调失败 2-代付成功 3-代付失败

    /**
     * 本系统订单号
     */
    private String orderNo;

    /**
     * 上游订单号
     */
    private String upOrderNo;

    private BigDecimal realAmount;

    private String errMsg;

    /**
     * 返回给上游的字符串
     */
    private String successMsg;




}
