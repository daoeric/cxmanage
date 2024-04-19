package com.ruoyi.common.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ResetPwdBody {

    @NotNull(message = "商户号不能为空")
    private Long id;

    @NotNull(message = "密码不能为空")
    private String password;

    @NotNull(message = "密码类型不能为空 0-登录密码 1-体现密码")
    private Integer type;

}
