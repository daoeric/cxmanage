package com.ruoyi.channel.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 结算方案对象 t_settlement_plan
 * 
 * @author ruoyi
 * @date 2022-04-23
 */
@Data
public class TSettlementPlan
{
    private static final long serialVersionUID = 1L;

    @TableId( type = IdType.AUTO)
    private Integer planId;

    /** 方案名称 */
    @Excel(name = "方案名称")
    private String planName;

    /** 最小结算限额 */
    @Excel(name = "最小单次结算限额")
    private BigDecimal minAmount;

    @Excel(name = "最大单次结算限额")
    private BigDecimal limitPerAmount;


    /** 最大结算限额 */
    @Excel(name = "最大累计结算限额")
    private BigDecimal maxAmount;

    /** 当日最大提款上限 */
    @Excel(name = "当日最大提款上限")
    private BigDecimal dayLimitTotal;

    /** 单日提款次数上限 */
    @Excel(name = "单日提款次数上限")
    private Integer dayLimitCount;

    /** 单笔代付手续费 */
    @Excel(name = "单笔代付手续费")
    private BigDecimal fee;

    /** 代收单笔费率 */
    @Excel(name = "代收单笔费率")
    private BigDecimal depositRate;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 备注 */
    private String remark;
}
