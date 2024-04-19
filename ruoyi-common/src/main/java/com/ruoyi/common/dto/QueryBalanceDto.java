package com.ruoyi.common.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 代付dto
 */
@Data
public class QueryBalanceDto implements Serializable {

    /**
     * 商户号
     */
    @NotNull(message = "商户号不能为空")
    private Long mchId;


    @NotEmpty(message = "接口调用时间")
    private String applyTime;

    @NotEmpty(message = "签名不能为空")
    private String sign;



}
