package com.ruoyi.common.dto.admin;

import com.ruoyi.common.core.page.PageDomain;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class CreditLogSearchDto extends PageDomain {

    private Integer mchId;

    private String refId;

    private Integer opearteType;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date begin;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date end;

}
