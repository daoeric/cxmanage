package com.ruoyi.common.vo;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PaymentRequestVO {

    @Excel(name = "系统订单号")
    private String requestId;

    /** 商户号 */
    @Excel(name = "商户号")
    private Long mchId;

    /** 支付状态 */
    @Excel(name = "订单状态" ,readConverterExp = "1=等待,2=成功,3=失败")
    private Integer status;

    /** 订单金额 */
    @Excel(name = "订单金额",scale = 2)
    private BigDecimal orderAmount;

    @Excel(name = "真实金额",scale = 2)
    private BigDecimal realAmount;

    /** 渠道ID */
    private Integer channelId;

    @Excel(name = "通道名称")
    private String channelName;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    @Excel(name = "订单时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Excel.Type.EXPORT)
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    private Date updateTime;

    @Excel(name = "商户订单号")
    private String outOrderNo;

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
     * 结算费率
     */
//    @Excel(name = "结算费率",scale = 2)
//    private BigDecimal rate;

    /**
     * 通道费率
     */
    @Excel(name = "成本费率",scale = 2)
    private BigDecimal channelRate;

    /**
     * 利润
     */
    @Excel(name = "利润",scale = 2)
    private BigDecimal profit;

    /**
     * 收费费
     */
    @Excel(name = "手续费",scale = 2)
    private BigDecimal fee;

    /**
     * 回调砖头盖
     */
    private Integer callbackStatus;

    /**
     * 商户费率
     */
    @Excel(name = "商户费率",scale = 2)
    private BigDecimal mchRate;
    /**
     * 代理费率
     */
    @Excel(name = "代理费率",scale = 2)
    private BigDecimal agentRate;
    /**
     * 代理花费
     */
    @Excel(name = "代理花费",scale = 2)
    private BigDecimal agentCost;
    /**
     * 通道成本花费
     */
    @Excel(name = "成本花费",scale = 2)
    private BigDecimal channelCost;

    /**
     * 产品编码
     */
    @Excel(name = "产品编码")
    private String productId;

    /**
     * 产品名称
     */
    @Excel(name = "产品名称")
    private String productName;

    /**
     * 上游名称
     */
    @Excel(name = "上游通道名称")
    private String alias;

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


}
