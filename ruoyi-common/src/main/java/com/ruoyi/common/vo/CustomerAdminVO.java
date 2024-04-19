package com.ruoyi.common.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 *  管理端用户展示VO
 */
@Data
public class CustomerAdminVO {

    /** 商户号 */
    private Long id;

    /** 可用余额 */
    private BigDecimal balance;

    /** 冻结余额 */
    private BigDecimal lockBalance;

    private BigDecimal totalBalance;

    /** 最后登录时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date lastLoginTime;

    /** 结算方案ID */
    private Long planId;

    /**
     * 结算方案名称
     */
    private String planName;

    private String lastLoginAddress;

    private String status;

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
     * 0-商户 1-代理
     */
    private Integer userType;

    /**
     * 代理ID
     */
    private Long agentId;

    /**
     * 代理用户名
     */
    private String agentName;

    /**
     * 商户白名单
     */
    private String ipWhiteList;

}
