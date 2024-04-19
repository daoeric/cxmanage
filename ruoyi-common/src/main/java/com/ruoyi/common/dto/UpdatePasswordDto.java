package com.ruoyi.common.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdatePasswordDto {

    @NotEmpty(message = "旧密码不能为空")
    private String oldPassword;
    @NotEmpty(message = "新密码不能为空")
    private String newPassword;
    /**
     * 1-修改登录密码  2-修改提现密码
     */
    @NotNull(message = "密码模式不能为空")
    private Integer passwordMode;

    private String code;
}
