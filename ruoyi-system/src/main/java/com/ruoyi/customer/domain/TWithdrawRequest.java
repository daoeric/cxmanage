package com.ruoyi.customer.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 代付提现对象 t_withdraw_request
 * 
 * @author yf
 * @date 2022-04-17
 */
@Data
public class TWithdrawRequest
{
    private static final long serialVersionUID = 1L;

    @TableId(type=IdType.INPUT)
    @Excel(name = "系统订单号")
    private String withdrawId;

    /** 提现金额 */
    @Excel(name = "提现金额")
    private BigDecimal withdrawAmount;

    /** 商户号 */
    @Excel(name = "商户号")
    private Long mchId;

    /** 状态 */
    @Excel(name = "状态" ,readConverterExp = "1=等待,2=成功,3=失败")
    private Integer status;

    /** 手续费 */
    @Excel(name = "手续费")
    private BigDecimal fee;

    /** $column.columnComment */
//    @Excel(name = "回调地址")
    private String notifyUrl;

    /** $column.columnComment */
//    @Excel(name = "返回地址")
    private String callbackUrl;

    private BigDecimal realAmount;

    @Excel(name = "商户订单号")
    private String outOrderNo;

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

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    /**
     * 银行名称
     */
    @Excel(name = "银行名称")
    private String bankName;

    /**
     * 银行卡号
     */
    @Excel(name = "银行卡号")
    private String bankNo;

    /**
     * 持有人真实姓名
     */
    @Excel(name = "真实姓名")
    private String bankRealname;

    /**
     * 回调状态
     */
    private Integer callbackStatus;

    /**
     * 回调时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "回调时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Excel.Type.EXPORT)
    private Date successTime;

    /**
     * 通道ID
     */
    private Integer channelId;

    /**
     * 附加
     */
    @Excel(name = "attach")
    private String attach;

    /**
     * 上游订单号
     */
    private String upOrderNo;

    /**
     * 货币类型
     */
    private Integer currencyType;

    /**
     * 订单类型 1-接口 2-手动发起
     */
    private Integer orderType;

    private BigDecimal channelFee;

    /**
     * 费率手续费
     */
    private BigDecimal rateFee;


}
