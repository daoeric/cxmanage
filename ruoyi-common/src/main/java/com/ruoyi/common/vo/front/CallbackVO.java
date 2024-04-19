package com.ruoyi.common.vo.front;

import lombok.Data;

import java.math.BigDecimal;

/**
 *  管理端用户展示VO
 */
@Data
public class CallbackVO {

    /**
     * 状态 2 成功 3-失败  1-处理中
     */
    private Integer status;

    private Long mchId;

    private String orderNo;

    private String billNo;

    private BigDecimal amount;

    private String attach;

    private String sign;

    /**
     * 订单备注
     */
    private String remark;

}
