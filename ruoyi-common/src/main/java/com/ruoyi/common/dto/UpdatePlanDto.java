package com.ruoyi.common.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdatePlanDto {

    @NotNull(message = "商户号不能为空")
    private Long id;

    @NotNull(message = "结算方式不能为空")
    private Integer planId;


}
