package com.ruoyi.wallet.vo;

import lombok.Data;

@Data
public class WalletUserVo{

    /** 钱包ID */
    private Long walletId;

    /** 币种 */
    private String walletType;

    /** 协议 */
    private String walletAgree;

    /** 钱包地址 */
    private String address;
}
