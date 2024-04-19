package com.ruoyi.common.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateAgentDto {
    @NotNull(message = "商户ID不能为空")
    private Long id;
    private Long agentId;
}
