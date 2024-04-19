package com.ruoyi.customer.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商户充值订单对象 t_payment_request
 * 
 * @author yf
 * @date 2022-04-17
 */
@Data
public class TPaymentRequest
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @TableId(type = IdType.INPUT)
    @Excel(name = "系统订单号")
    private String requestId;

    /** 商户号 */
    @Excel(name = "商户号")
    private Long mchId;

    /** 系统订单号 */
//    @Excel(name = "系统订单号")
//    private String orderNo;

    /** 支付状态 */
    @Excel(name = "支付状态" ,readConverterExp = "1=等待,2=成功,3=失败")
    private Integer status;

    /** 订单金额 */
    @Excel(name = "订单金额")
    private BigDecimal orderAmount;

    /** 渠道ID */
    private Integer channelId;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "订单时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Excel.Type.EXPORT)
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @Excel(name = "商户订单号")
    private String outOrderNo;

    private BigDecimal realAmount;

    /**
     * 成功时间
     */
    private Date successTime;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;


    /**
     * 商户回调地址
     */
    private String notifyUrl;

    /**
     * 回调状态
     */
    private Integer callbackStatus;

    private String attach;

    @Excel(name = "手续费")
    private BigDecimal fee;

    /**
     * 通道成本费率
     */
    private BigDecimal channelRate;

    /**
     *  利润
     */
    private BigDecimal profit;

    /**
     * 商户费率
     */
    private BigDecimal mchRate;
    /**
     * 代理费率
     */
    private BigDecimal agentRate;
    /**
     * 代理花费
     */
    private BigDecimal agentCost;
    /**
     * 通道成本花费
     */
    private BigDecimal channelCost;

    private String productId;

    /**
     * 代理ID
     */
    private Long agentId;

    /**
     * 成本U费率
     */
    private BigDecimal channelURate;

    /**
     * 商户U费率
     */
    private BigDecimal mchURate;


    /**
     * USDT 入金
     */
    private BigDecimal uIncome;


    /**
     * usdt 利润
     */
    private BigDecimal usdtProfit;

    /**
     * 上游
     */
    private String alias;



}
