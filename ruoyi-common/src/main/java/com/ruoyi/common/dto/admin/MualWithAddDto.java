package com.ruoyi.common.dto.admin;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class MualWithAddDto {

    @NotNull(message = "商户号不能为空")
    @Positive(message = "商户号必须为数字")
    private Long customerId;

    @NotEmpty(message = "开户姓名不能为空")
    private String accountName;

    @NotNull(message = "提现金额不能为空")
    private BigDecimal amount;

    @NotEmpty(message = "银行卡号不能为空")
    private String bankNo;

    @NotEmpty(message = "银行名称不能为空")
    private String bankName;

    @NotEmpty(message = "谷歌验证码不能为空")
    private String googleCode;

    @NotNull(message = "请选中货币类型")
    private Integer currencyType;


}
