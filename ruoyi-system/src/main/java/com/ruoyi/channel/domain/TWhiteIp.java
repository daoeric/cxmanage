package com.ruoyi.channel.domain;

import lombok.Data;

/**
 * IP白名单对象 t_white_ip
 * 
 * @author yf
 * @date 2022-05-30
 */
@Data
public class TWhiteIp
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** $column.columnComment */
    private String alias;

    /** IP白名单 */
    private String ipAddress;

    private String status;

    private String code;

}
