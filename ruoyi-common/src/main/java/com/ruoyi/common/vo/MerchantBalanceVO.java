package com.ruoyi.common.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MerchantBalanceVO {


    /**
     *  余额
     */
    private BigDecimal balance;

    /**
     * 商户号
     */
    private Long mchId;





}
