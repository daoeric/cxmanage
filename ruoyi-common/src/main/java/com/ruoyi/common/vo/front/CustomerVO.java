package com.ruoyi.common.vo.front;

import lombok.Data;

import java.math.BigDecimal;

/**
 *  管理端用户展示VO
 */
@Data
public class CustomerVO {

    /** 商户号 */
    private Long id;

    private String username;

    /**
     * 秘钥
     */
    private String priKey;

    /**
     * 单笔最小提现
     */
    private BigDecimal minWithdrawalAmount;

    /**
     * 单笔最大提现
     */
    private BigDecimal maxWithdrawalAmount;

    /**
     * 代收费率
     */
    private BigDecimal rate;

    /**
     * 单笔代付手续费
     */
    private BigDecimal fee;

    /** 可用余额 */
    private BigDecimal balance;

    /** 冻结余额 */
    private BigDecimal lockBalance;

    /** IP白名单 */
    private String ipWhiteList;

    /**
     * 谷歌验证模式
     */
    private Integer safeMode;

    /**
     * 通道额度
     */
    private BigDecimal channelLimit;

    /**
     * 总使用额度
     */
    private BigDecimal totalUsed;

    /**
     * 谷歌密钥
     */
    private String googleCode;

    /**
     * 谷歌二维码
     */
    private String googleUrl;

    private Integer userType;

}
