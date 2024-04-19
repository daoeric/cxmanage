package com.ruoyi.report.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 渠道统计对象 t_report_channel_day
 * 
 * @author ruoyi
 * @date 2023-10-04
 */
@Data
public class TReportChannelDay
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 商户号 */
    @Excel(name = "通道ID")
    private Long channelId;

    /** 用户名 */
    @Excel(name = "通道名")
    private String channelName;

    /** 代付笔数 */
    @Excel(name = "代付笔数")
    private Long withdrawCount;

    /** 代付总额 */
    @Excel(name = "代付总额")
    private BigDecimal withdrawAmount;

    /** 成功次数 */
    @Excel(name = "成功次数")
    private Long withdrawSuccessCount;

    /** 代付成功金额 */
    @Excel(name = "代付成功金额")
    private BigDecimal withdrawSuccessAmount;

    /** 手续费 */
    @Excel(name = "手续费")
    private BigDecimal withdrawFee;

    /** 报表时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "报表时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reportDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;


}
