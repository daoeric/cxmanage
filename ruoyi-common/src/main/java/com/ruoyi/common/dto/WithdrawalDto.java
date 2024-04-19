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
public class WithdrawalDto implements Serializable {

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


    //@NotEmpty(message = "同步回调地址不能为空")
//    private String returnUrl;

    /**
     * 银行名称
     */
    @NotEmpty(message = "银行名称不能为空")
    private String bankName;

    @NotEmpty(message = "银行卡号不能为空")
    private String bankNo;

    @NotEmpty(message = "支行名称不能为空")
    private String bankBranch;

//    @NotEmpty(message = "开户省不能为空")
//    private String provice;
//
//    @NotEmpty(message = "开户城市不能为空")
//    private String city;

    @NotEmpty(message = "开户姓名不能为空")
    private String accountName;

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

    private String ip;


}
