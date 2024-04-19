package com.ruoyi.common.core.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 用户登录对象
 * 
 * @author ruoyi
 */
@Data
public class LoginBody
{
    /**
     * 用户名
     */
    @NotBlank(message = "用户名称不能为空")
    @Size(min = 6, max = 20, message = "用户名称要在6到20个字符之间")
    private String username;

    /**
     * 用户密码
     */
    @NotBlank(message = "用户密码不能为空")
    @Size(min = 6, max = 20, message = "用户密码要在6到20个字符之间")
    private String password;

    /**
     * 验证码
     */
    private String code;

    private String uuid;

    @JsonIgnore
    public String getUuid() {
        return uuid;
    }

    @JsonProperty
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
