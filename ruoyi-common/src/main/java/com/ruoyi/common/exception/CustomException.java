package com.ruoyi.common.exception;

import com.ruoyi.common.enums.ExceptionEnum;

/**
 * 自定义异常
 * 
 * @author qin-chat
 */
public class CustomException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    private Integer code;

    private String message;

    public CustomException(String message)
    {
        this.message = message;
    }

    public CustomException(ExceptionEnum exceptionEnum)
    {
        this.message = exceptionEnum.getMessage(); this.code = exceptionEnum.getCode();
    }

    public CustomException(String message, Integer code)
    {
        this.message = message;
        this.code = code;
    }

    public CustomException(String message, Throwable e)
    {
        super(message, e);
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return message;
    }

    public Integer getCode()
    {
        return code;
    }

    /**
     *  重写此方法为了自定义异常不打印堆栈信息
     * @return
     */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
