package com.ruoyi.common.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 通道配置对象 t_channel_set
 * 
 * @author yf
 * @date 2022-05-22
 */
@Data
public class ChannelSetVO
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 产品编码 */
    private String productId;

    private String productName;


    /** 结算ID */
    private Integer planId;

    /** 1开启 2-关闭 */
    private Integer status;

    /** 1-单独  2-轮询 */
    private Integer mode;

    /** 轮询权重 */
    private String weight;

    /** 费率 */
    private BigDecimal rate;

    /** 1-普通商户 2-代理 */
    private Integer type;

    /** 绑定的通道id */
    private String channelIds;

    // TODO 后期可能会加通道轮询
    private String channelName;

    private Long customerId;

    private Integer fixMode;

}
