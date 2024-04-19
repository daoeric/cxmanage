package com.ruoyi.common.vo.front;

import lombok.Data;

import java.math.BigDecimal;

/**
 *  管理端用户展示VO
 */
@Data
public class BalanceVO {

    /**
     * 可用额度
     */
    private BigDecimal balance;

    /**
     * 冻结额度
     */
    private BigDecimal lockBalance;

    /**
     * 总使用额度
     */
    private BigDecimal totalUsed;


}
