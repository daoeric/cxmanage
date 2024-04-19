package com.ruoyi.common.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ListDto extends QueryPage{

    private String orderNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date begin;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date end;

    private Integer status;

    /**
     * 商户订单号
     */
    private String outOrderNo;

    private String productId;

    private Long mchId;

    /**
     * 银行卡号
     */
    private String bankNo;

    /**
     * 银行真实姓名
     */
    private String bankRealname;

}

