package com.ruoyi.channel.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 通道配置对象 t_channel_set
 * 
 * @author yf
 * @date 2022-05-22
 */
@Data
public class TChannelSet
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 产品编码 */
    @Excel(name = "产品编码")
    private String productId;

    /** 结算ID */
    @Excel(name = "结算ID")
    private Integer planId;

    /** 1开启 2-关闭 */
    @Excel(name = "1开启 2-关闭")
    private Integer status;

    /** 1-单独  2-轮询 */
    @Excel(name = "1-单独  2-轮询")
    private Integer mode;

    /** 轮询权重 */
    @Excel(name = "轮询权重")
    private String weight;

    /** 费率 */
    @Excel(name = "费率")
    private BigDecimal rate;

    /** 1-普通商户 2-代理 */
    @Excel(name = "1-普通商户 2-代理")
    private Integer type;

    /** 绑定的通道id */
    @Excel(name = "绑定的通道id")
    private String channelIds;

    private Long customerId;

    private Integer fixMode;

    private BigDecimal daifuFee;

    private BigDecimal daifuMaxAmount;

    private BigDecimal daifuMinAmount;

}
