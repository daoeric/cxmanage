package com.ruoyi.channel.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 通道信息对象 t_channel
 * 
 * @author ruoyi
 * @date 2022-04-23
 */
@Data
public class TChannel
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId( type = IdType.AUTO)
    private Integer channelId;

    /** 上游商户号（对接用） */
    @Excel(name = "上游商户号", readConverterExp = "对=接用")
    private String merchantId;

    /** 渠道名称 */
    @Excel(name = "渠道名称")
    private String name;

    /** 注入key */
    private String code;

    /** 余额接口状态 */
    private Integer balanceApiStatus;

    /** 单日限额 */
    @Excel(name = "单日限额")
    private BigDecimal limitAmountDay;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 秘钥 */
    @Excel(name = "秘钥")
    private String priKey;

    /** 支付URL */
    @Excel(name = "支付URL")
    private String apiUrl;

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

    /**
     * 通道费率 (成本费率)
     */
    private BigDecimal channelRate;

    /**
     * 上游别名
     */
    private String alias;

    /**
     * 产品ID
     */
    private String productId;

    /**
     * 最小限额
     */
    private BigDecimal minAmount;

    /**
     * 最大限额
     */
    private BigDecimal maxAmount;

    /**
     * 固定限额
     */
    private String fixAmount;

    /**
     * 通道code
     */
    private String channelType;

    /**
     * 代收回调地址
     */
    private String depositNotify;

    /**
     * 代付回调地址
     */
    private String withdrawalNotify;

    /**
     * 成本代付手续费
     */
    private BigDecimal channelFee;
}
