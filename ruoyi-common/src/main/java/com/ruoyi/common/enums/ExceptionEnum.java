package com.ruoyi.common.enums;

import lombok.Getter;

@Getter
public enum ExceptionEnum {
    AVAILABLE_BALANCE_NOT_ENOUGH(1001,"余额不足"),
    MERCHANT_NOT_EXIST(1002,"商户号不存在"),
    SIGN_ERROR(1003,"签名错误"),
    ERROR_500(500,"系统错误"),
    BALANCE_ERROR(1004,"额度没有清空,无法更换上游通道"),
    NO_CHANNEL_CONFIG(1005,"商户没有配置通道，请联系客服"),
    NO_PLAN_CONFIG(1006,"商户没有配置结算方式，请联系客服"),
    ORDER_NO_EXIST(1007,"订单号不存在"),
    DAIFU_ERROR(1008,"代付异常，事务回滚"),
    PARAMS_ERROR(1009,"参数异常"),
    GOOGLE_CODE_ERROR(1010,"谷歌验证码错误"),
    TIME_ERROR(1011,"时间参数过期"),
    CHANNEL_BALANCE_NOT_ENOUGH(1012,"通道额度已达到上限，无法进行代付"),
    ORDER_REPEART_ERROR(1013,"订单号重复"),
    NO_CHANNEL_CONFIG_ERROR(1014,"没有绑定通道，请联系客服"),
    ;

    private Integer code;

    private String message;


    ExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
