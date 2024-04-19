package com.ruoyi.common.core.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class DfBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 创建时间 */
    //@JsonFormat(pattern = "yyyy-MM-dd")
    //@Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Long gmtCreate;

    /** 更新时间 */
    //@JsonFormat(pattern = "yyyy-MM-dd")
    //@Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Long gmtModified;

    /** 版本 */
    //@Excel(name = "版本")
    private Long version;

    /** 请求参数 */
    @TableField(exist = false)
    private Map<String, Object> params;

    public Map<String, Object> getParams(){
        if (params == null)
        {
            params = new HashMap<>();
        }
        return params;
    }

    public void setParams(Map<String, Object> params)
    {
        this.params = params;
    }
}
