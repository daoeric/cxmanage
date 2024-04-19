package com.ruoyi.common.vo.front;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商户充值订单对象 t_payment_request
 * 
 * @author yf
 * @date 2022-04-17
 */
@Data
public class AgentPaymentRequestVO extends TPaymentRequestVO
{

    @Excel(name = "代理佣金")
    private BigDecimal agentProfit;



}
