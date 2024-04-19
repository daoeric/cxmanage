package com.ruoyi.common.vo.front;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithdrawalRequestQueryVO {

    private String orderNo;

    private String billNo;

    private BigDecimal amount;

    private Integer status;

}
