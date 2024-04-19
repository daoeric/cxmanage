package com.ruoyi.common.dto.admin;

import lombok.Data;

@Data
public class CustomerSearchDto {

    private String mchId;

    private Integer status;

    /**
     * 0-商户 1-代理
     */
    private Integer userType;


}
