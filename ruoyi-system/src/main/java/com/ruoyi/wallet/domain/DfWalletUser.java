package com.ruoyi.wallet.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.DfBaseEntity;
import lombok.Data;

/**
 * 钱包账户列对象 df_wallet_user
 * 
 * @author ruoyi
 * @date 2022-04-13
 */
@Data
public class DfWalletUser extends DfBaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 钱包类型（0官方钱包 1客户钱包） */
    @Excel(name = "钱包类型", readConverterExp = "0=官方钱包,1=客户钱包")
    private String type;

    /** 客户ID */
    @Excel(name = "客户ID")
    private Long userId;

    /** 协议ID */
    @Excel(name = "协议ID")
    private Long agreeId;

    /** 钱包地址 */
    @Excel(name = "钱包地址")
    private String address;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;
}
