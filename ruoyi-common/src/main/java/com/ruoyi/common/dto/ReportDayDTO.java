package com.ruoyi.common.dto;

import com.ruoyi.common.core.page.PageDomain;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 日报统计对象 t_report_day
 * 
 * @author yf
 * @date 2022-07-06
 */
@Data
public class ReportDayDTO extends PageDomain
{
    private static final long serialVersionUID = 1L;

    /** 商户号 */
    private Long mchId;

    /** 用户名 */
    private String username;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date begin;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date end;

}
