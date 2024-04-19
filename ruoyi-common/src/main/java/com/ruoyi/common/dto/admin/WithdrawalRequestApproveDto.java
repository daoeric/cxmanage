package com.ruoyi.common.dto.admin;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class WithdrawalRequestApproveDto {

    @NotEmpty(message = "单号不能为空")
    private String withdrawId;

    @NotEmpty(message = "谷歌验证码不能为空")
    private String googleCode;

    private String remark;

    @NotNull(message = "审核状态不能为空")
    private Integer status;



}
