package com.ruoyi.wallet.vo;

import lombok.Data;

import java.util.List;

@Data
public class WalletTypeVo {
    /** 币种 */
    private String walletType;

    /** 协议列表 */
    private List<WalletAgreeVo> walletAgreeList;
}
