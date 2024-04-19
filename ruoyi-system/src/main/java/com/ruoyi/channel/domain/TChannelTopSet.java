package com.ruoyi.channel.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 通用费率配置对象 t_channel_top_set
 * 
 * @author cx
 * @date 2023-09-15
 */
@Data
public class TChannelTopSet
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 产品ID */
    private String productId;

    /** 通道ID */
    private String channelIds;

    /** 状态 0-开启 1-关闭 */
    private Integer status;

    /** 1-单独 2-轮询 */
    private Integer mode;

    /** 费率 */
    private BigDecimal rate;

    private BigDecimal daifuFee;

    private BigDecimal daifuMaxAmount;

    private BigDecimal daifuMinAmount;

}
