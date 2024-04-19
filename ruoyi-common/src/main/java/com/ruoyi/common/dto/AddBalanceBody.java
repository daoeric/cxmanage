package com.ruoyi.common.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class AddBalanceBody {

    @NotNull(message = "商户好不能为空")
    private Long id;

    @NotNull(message = "添加金额不能为空")
    private BigDecimal amount;

    @NotEmpty(message = "")
    private String remark;

    @NotEmpty(message = "谷歌验证码不能为空")
    private String googleCode;



}
