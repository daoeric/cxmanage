package com.ruoyi.common.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class QueryDto {

    @NotNull(message = "商户号不能为空")
    private Long mchid;

    @NotEmpty(message = "订单号不能为空")
    private String orderid;

    @NotEmpty(message = "sign不能为空")
    private String sign;
}
