package com.ruoyi.common.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 代付dto
 */
@Data
public class WithdrawalQueryDto implements Serializable {

    /**
     * 商户号
     */
    @NotNull(message = "商户号不能为空")
    private Long mchId;
    /**
     * 商户订单号
     */
    @NotEmpty(message = "订单号不能为空")
    private String orderNo;

    /**
     * 签名
     */
    @NotEmpty(message = "sign不能为空")
    private String sign;


}
