package com.ruoyi.common.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserIdBody {

    @NotNull(message = "商户号不能为空")
    private Long id;

}
