package com.ruoyi.common.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 手动入金dto
 */
@Data
public class ManualDepositDto implements Serializable {

    /**
     * 商户号
     */
    @NotNull(message = "商户号不能为空")
    private Long mchId;

    @NotEmpty(message = "入金通道不能为空")
    private String alias;

    @NotNull(message = "USDT入金不能为空")
    private BigDecimal usdtIncome;

    @NotNull(message = "成本USDT入金费率")
    private BigDecimal channelURate;

    @NotNull(message = "商户USDT入金费率")
    private BigDecimal mchURate;

    @NotEmpty(message = "谷歌验证码不能为空")
    private  String googleCode;

}
