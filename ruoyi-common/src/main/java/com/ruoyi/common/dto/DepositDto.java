package com.ruoyi.common.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 代付dto
 */
@Data
public class DepositDto implements Serializable {

    /**
     * 商户号
     */
    @NotNull(message = "商户号不能为空")
    private Long mchId;

    /**
     * 商户订单号
     */
    @NotEmpty(message = "订单号不能为空")
    private String orderId;

    /**
     * 订单金额
     */
    @NotNull(message = "金额不能为空")
    private BigDecimal amount;

    /**
     * 异步回调地址
     */
    @NotEmpty(message = "异步回调地址不能为空")
    private String notifyUrl;


    private String returnUrl;

    /**
     * 签名
     */
    @NotEmpty(message = "sign不能为空")
    private String sign;

    /**
     * 附加信息，回调会原封不动带上这个参数
     */
    private String attach;

    /**
     * 申请时间 yyyymmddhhmmss格式
     */
    @NotEmpty(message = "申请时间不能为空")
    private String applyTime;

    @NotEmpty(message = "支付通道不能为空")
    private String payChannel;

    private BigDecimal uIncome;

    private BigDecimal channelURate;

    private BigDecimal mchURate;

    /**
     * U 利润
     */
    private BigDecimal usdtProfit;

    /**
     * 上游
     */
    private String alias;

}
