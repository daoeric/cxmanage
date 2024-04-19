package com.ruoyi.common.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 代付dto
 */
@Data
public class WithdrawalFanchaDto extends WithdrawalDto implements Serializable {
    @NotEmpty(message = "反查地址不能为空")
   private String withdrawQueryUrl;
    @NotEmpty(message = "反查token不能为空")
   private String callToken;
}
