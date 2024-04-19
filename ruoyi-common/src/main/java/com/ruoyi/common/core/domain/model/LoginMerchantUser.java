package com.ruoyi.common.core.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class LoginMerchantUser extends LoginUser {

    /** 商户号 */
    private Long id;

    private String username;

    /** 邮箱 */
    private String email;

    /** 头像 */
    private String avatar;

    /** 手机 */
    private String phone;

    /** 状态 0禁用1启用 */
    private Integer status;

    /** 创建时间 */
    //@JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;

    /** 修改时间 */
    //@JsonFormat(pattern = "yyyy-MM-dd")
    private Date updatedAt;

    private String token;

    private String password;

    private Date lastLoginTime;

    private String googleCode;

    @Override
    @JsonIgnore
    public String getPassword()
    {
        return this.password;
    }


}
