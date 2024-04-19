package com.ruoyi.wallet.vo;

import lombok.Data;

import java.util.List;

@Data
public class WalletBankerVo {

    private String walletType;

    private List<WalletUserVo> walletList;

    public WalletBankerVo(String walletType, List<WalletUserVo> walletList) {
        this.walletType = walletType;
        this.walletList = walletList;
    }
}
