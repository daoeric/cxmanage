package com.ruoyi.customer.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商户信息对象 t_customer
 * 
 * @author yf
 * @date 2022-04-17
 */
@Data
public class TCustomer
{
    private static final long serialVersionUID = 1L;

    /** 商户号 */
    @TableId( type = IdType.AUTO)
    private Long id;

    /** 可用余额 */
    @Excel(name = "可用余额")
    private BigDecimal balance;

    /** 冻结余额 */
    @Excel(name = "冻结余额")
    private BigDecimal lockBalance;

    /** 待结算余额 */
    @Excel(name = "待结算余额")
    private BigDecimal waitBalance;

    /** 结算方式 */
    @Excel(name = "结算方式")
    private Integer settlementType;

    /** 手机认证 */
    @Excel(name = "手机认证")
    private Integer phoneFlag;

    /** 邮箱认证 */
    @Excel(name = "邮箱认证")
    private Integer emailFlag;

    /** 用户等级 */
    @Excel(name = "用户等级")
    private Integer level;

    /** 秘钥 */
    @Excel(name = "秘钥")
    private String priKey;

    /** 谷歌验证码 */
    @Excel(name = "谷歌验证码")
    private String googleCode;

    /** IP白名单 */
    @Excel(name = "IP白名单")
    private String ipWhiteList;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 登录密码 */
    @Excel(name = "登录密码")
    private String password;

    /** 提款密码 */
    @Excel(name = "提款密码")
    private String withdrawPassword;

    /** 最后登录时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后登录时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastLoginTime;

    /** 结算方案ID */
    @Excel(name = "结算方案ID")
    private Integer planId;

    /** 代理ID */
    @Excel(name = "代理ID")
    private Long agentId;

    /** 是否为代理 0-普通用户 1-代理用户 */
    @Excel(name = "是否为代理 0-普通用户 1-代理用户")
    private Integer isagent;

    private String lastLoginAddress;

    private Integer status;

    private String phone;

    private String email;

    private String username;

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

    /**
     * 谷歌验证模式
     */
    private Integer safeMode;

    /**
     * 通道ID
     */
    private Integer channelId;

    /**
     * 通道额度
     */
    private BigDecimal channelLimit;

    /**
     * 总使用额度
     */
    private BigDecimal totalUsed;

    /**
     * 0-商户 1-代理
     */
    private Integer userType;


}
