package com.ruoyi.common.vo.payment;

import lombok.Data;

@Data
public class CreateResult {
    private boolean result;
    private String msg;
    private String bankNo;
    private String accountName;
    private Integer index;
}
