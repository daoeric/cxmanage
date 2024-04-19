package com.ruoyi.common.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class WithdrawalAddDto {

    /**
     * 银行名称
     */
    @NotEmpty(message = "银行名称或虚拟币协议不能为空")
    private String bankName;

    @NotEmpty(message = "开户姓名或虚拟币收款地址不能为空")
    private String accountName;

    private String bankNo;

    /**
     * 订单金额
     */
    @NotNull(message = "金额不能为空")
    private BigDecimal amount;

    @NotNull(message = "货币类型不能为空")
    private Integer currencyType;

    @NotEmpty(message = "提现密码不能为空")
    private String password;

    @NotEmpty(message = "谷歌验证码不能为空")
    private String code;
}
