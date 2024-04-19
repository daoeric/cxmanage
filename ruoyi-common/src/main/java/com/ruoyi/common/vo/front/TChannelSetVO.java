package com.ruoyi.common.vo.front;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class TChannelSetVO {

    /** $column.columnComment */
    private Long id;

    /** 产品编码 */
    private String productId;

    private String productName;


    /** 1开启 2-关闭 */
    private Integer status;


    /** 费率 */
    private BigDecimal rate;

    private BigDecimal maxAmount;

    private BigDecimal minAmount;

    private String fixAmount;

}
