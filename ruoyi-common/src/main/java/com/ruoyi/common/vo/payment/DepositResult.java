package com.ruoyi.common.vo.payment;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositResult {

    public int code;

    public String payUrl;

    public String msg;

    public String orderNo;

    private BigDecimal realAmount;

}
