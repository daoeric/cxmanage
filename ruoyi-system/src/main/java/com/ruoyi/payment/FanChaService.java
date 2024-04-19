package com.ruoyi.payment;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.dto.WithdrawalFanchaDto;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.http.OkHttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class FanChaService {

    public boolean fancha(WithdrawalFanchaDto withdrawalDto) throws Exception {
        String url = withdrawalDto.getWithdrawQueryUrl();
        Map<String,String> params = new HashMap<>();
        params.put("merchantId",withdrawalDto.getMchId().toString());
        params.put("orderNo",withdrawalDto.getOrderId());
        params.put("money",withdrawalDto.getAmount().setScale(2).toString());
        params.put("ownerName",withdrawalDto.getAccountName());
        params.put("token",withdrawalDto.getCallToken());
        params.put("target",withdrawalDto.getBankNo());
        String result = OkHttpUtils.getInstance().doPostForm(url, params);
        if(StringUtils.isNotEmpty(result)){
            JSONObject jsonObject = JSONObject.parseObject(result);
            Integer status = jsonObject.getInteger("status");
            return status!=null&&status==1;
        } else {
            return false;
        }
    }
}