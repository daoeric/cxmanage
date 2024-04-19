package com.ruoyi.common.dto;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 批量代付
 */
@Data
public class DaifuBatchDto {

    @Excel(name = "序号（选填）")
    private String xuhao;

    @Excel(name = "收款方姓名（必填）")
    private String realName;

    @Excel(name = "收款方银行卡号（必填）")
    private String bankNo;

    @Excel(name = "金额（必填，单位：元）")
    private BigDecimal amount;

    @Excel(name = "附言（选填）")
    private String attach;

    @Excel(name = "收款人手机号（选填）")
    private String phone;

    @Excel(name = "银行名称（必填）")
    private String bankName;

    private String msg;

    private Integer index;

    private boolean result;

}
