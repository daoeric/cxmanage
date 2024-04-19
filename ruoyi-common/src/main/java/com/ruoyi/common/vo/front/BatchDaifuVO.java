package com.ruoyi.common.vo.front;

import lombok.Data;

import java.util.List;

@Data
public class BatchDaifuVO {

    //成功几条
    private Integer successCount;

    private Integer failCount;

    private Integer totalCount;

    private List<BatchDaifuVO> messageList;


}
