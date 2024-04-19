package com.ruoyi.common.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class WithdrawalRequestVO {
    private String withdrawId;

    /** 商户号 */
    @Excel(name = "商户号")
    private Long mchId;

    /** 支付状态 */
    @Excel(name = "订单状态" ,readConverterExp = "1=等待,2=成功,3=失败")
    private Integer status;

    /** 订单金额 */
    @Excel(name = "订单金额",scale = 2)
    private BigDecimal withdrawAmount;


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

    @Excel(name = "商户订单号",scale = 2)
    private String outOrderNo;

    @Excel(name = "真实金额",scale = 2)
    private BigDecimal realAmount;

    /**
     * 成功时间
     */
    @Excel(name = "成功时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Excel.Type.EXPORT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date successTime;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    /**
     * 手续费
     */
    @Excel(name = "手续费",scale = 2)
    private BigDecimal fee;

    @Excel(name = "费率手续费",scale = 2)
    private BigDecimal rateFee;

    private BigDecimal rate;

    /**
     * 通道名称
     */
    @Excel(name = "通道名称")
    private String channelName;

    /**
     * 回调状态
     */
    private Integer callbackStatus;

    /**
     * 银行名称
     */
    @Excel(name = "银行名称")
    private String bankName;

    /**
     * 卡号
     */
    @Excel(name = "卡号")
    private String bankNo;

    /**
     * 银行姓名
     */
    @Excel(name = "银行姓名")
    private String bankRealName;

    @Excel(name = "通道成本手续费")
    private BigDecimal channelFee;

    /**
     * 货币类型 1-RMB 2-USDT
     */
    @Excel(name = "货币类型",readConverterExp = "1=RMB,2=USDT")
    private Integer currencyType;

}
