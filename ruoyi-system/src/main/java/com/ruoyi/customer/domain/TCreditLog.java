package com.ruoyi.customer.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 额度变更对象 t_credit_log
 * 
 * @author ruoyi
 * @date 2022-04-17
 */
@Data
public class TCreditLog
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @Excel(name = "订单号")
    private String id;

    /** 商户号 */
    @Excel(name = "商户号")
    private Long mchId;

    /** 操作类型 */
    @Excel(name = "操作类型" , readConverterExp = "1=存款,2=提款,3=手动入款,4=手动出款,5=代付驳回")
    private Integer opearteType;

    /** 操作金额，可以为负数 */
    @Excel(name = "操作金额")
    private BigDecimal opearteAmount;

    /** 操作前金额 */
    @Excel(name = "操作前金额")
    private BigDecimal preBalance;

    /** 操作后金额 */
    @Excel(name = "操作后金额")
    private BigDecimal postBalance;

    /** 关联ID */
    @Excel(name = "关联ID")
    private String refId;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间",width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @Excel(name = "手续费",scale = 2)
    private BigDecimal fee;

    private String remark;

    @Excel(name = "商户号")
    private String mchNo;


}
