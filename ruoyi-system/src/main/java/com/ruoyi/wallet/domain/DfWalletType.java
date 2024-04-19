package com.ruoyi.wallet.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.DfBaseEntity;
import lombok.Data;

@Data
public class DfWalletType extends DfBaseEntity {

    private Long id;

    /** 币种 */
    @Excel(name = "币种")
    private String walletType;

    /** 协议 */
    @Excel(name = "协议")
    private String walletAgree;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;
}
