package com.ruoyi.report.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 日报统计对象 t_report_day
 * 
 * @author yf
 * @date 2022-07-06
 */
@Data
public class TReportDay
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 商户号 */
    @Excel(name = "商户号")
    private Long mchId;

    /** 用户名 */
    @Excel(name = "用户名")
    private String username;

    /** 充值次数 */
    @Excel(name = "充值次数")
    private Long depositCount;

    /** 充值总额 */
    @Excel(name = "充值总额")
    private BigDecimal depositAmount;

    /** 代付笔数 */
    @Excel(name = "代付笔数")
    private Long withdrawCount;

    /** 代付总额 */
    @Excel(name = "代付总额")
    private BigDecimal withdrawAmount;

    /** USDT汇率利润 */
    @Excel(name = "USDT汇率利润")
    private BigDecimal usdtProfit;

    /** 代付手续费利润 */
    @Excel(name = "代付手续费利润")
    private BigDecimal daifuProfit;

    /** 充值利润 */
    @Excel(name = "充值利润")
    private BigDecimal rechargeProfit;

    /** 报表时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "报表时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reportDate;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Excel(name = "USDT数量")
    private BigDecimal usdtIncome;

}
