package com.ruoyi.common.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;


@Data
public class DaifuBatchTotalDto {

    private List<DaifuBatchDto> list;

    @NotEmpty(message = "谷歌验证码不能为空")
    private String code;
}
