package com.ruoyi.web.controller.dashborad;

import com.beust.jcommander.internal.Maps;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.OrderEnum;
import com.ruoyi.customer.service.ITPaymentRequestService;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private ITPaymentRequestService paymentRequestService;

    @GetMapping("/info")
    public AjaxResult depositInfo()
    {
        //获取今日存款相关数据
        Map<String,Object> infoMap = paymentRequestService.depositInfo(null);
        Map<String,Object> successMap = paymentRequestService.depositInfo(OrderEnum.SUCCESS.getCode());
        infoMap.putAll(successMap);
        return AjaxResult.success(infoMap);
    }

    @GetMapping("/chat/data")
    public AjaxResult depositChatData(){
        //获取七天内的充值流水
        List<Map<String, Object>> chatDataList = paymentRequestService.depositChatData();
        List<String> dataStrList = Lists.newArrayList();
        List<String> AmountList = Lists.newArrayList();
        for (Map<String, Object> stringObjectMap : chatDataList) {
            dataStrList.add(stringObjectMap.get("dataStr").toString());
            AmountList.add(stringObjectMap.get("successAmount").toString());
        }
        Map<String,Object> resultMap = Maps.newHashMap();
        resultMap.put("dateStr",dataStrList);
        resultMap.put("successAmount",AmountList);
        return AjaxResult.success(resultMap);
    }
}
