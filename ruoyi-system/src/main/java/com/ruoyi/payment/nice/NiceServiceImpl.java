package com.ruoyi.payment.nice;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.channel.domain.TChannel;
import com.ruoyi.channel.service.ITChannelService;
import com.ruoyi.common.dto.DepositDto;
import com.ruoyi.common.dto.WithdrawalDto;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.MapDataUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.http.OkHttpUtils;
import com.ruoyi.common.vo.payment.BalanceResult;
import com.ruoyi.common.vo.payment.DepositResult;
import com.ruoyi.common.vo.payment.WithdrawalCallbackResult;
import com.ruoyi.common.vo.payment.WithdrawalResult;
import com.ruoyi.customer.domain.TWithdrawRequest;
import com.ruoyi.payment.IPaymentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Service("nice")
public class NiceServiceImpl implements IPaymentService {

    private String mchId = "";

    private String priKey = "";
    
    private static final String keyword = "nice";

    @Autowired
    private ITChannelService channelService;

    @Override
    public boolean queryOrderStatus(TWithdrawRequest withdrawRequest) {
        return false;
    }

    @Override
    public WithdrawalResult withdrawal(WithdrawalDto dto,String requestId) throws Exception {
        //获取配置的通道
        TChannel channel = channelService.getWithdrawalChannel(keyword);
        if (channel == null) {
            throw new CustomException("通道没有配置");
        }
        WithdrawalResult withdrawalResult = new WithdrawalResult();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("OpenId", channel.getMerchantId());
        paramMap.put("OutTradeNo",requestId);
        paramMap.put("Amount",String.format("%.2f",dto.getAmount()));
        paramMap.put("Channel","AliToBank");
        paramMap.put("BankName",dto.getBankName());
        paramMap.put("BankSubBranch",dto.getBankName());
        paramMap.put("BankCardName", dto.getAccountName());
        paramMap.put("BankCardNo",dto.getBankNo());
        paramMap.put("Notifyurl",channel.getWithdrawalNotify());
        paramMap.put("TimeStamp", DateUtils.getNowDate().getTime());
        paramMap.put("Extension",StringUtils.isEmpty(dto.getAttach())?DateUtils.getNowDate().getTime():dto.getAttach());
        //不加入签名
        paramMap.put("Sign", sortSign(paramMap, channel.getPriKey()));
        // 发送支付请求
        String result = OkHttpUtils.getInstance().doPostForm(channel.getApiUrl()+"Gateway/PushApi", MapDataUtil.Object2String(paramMap));
        log.info("奈斯支付发起，参数：{}，结果：{}", paramMap, result);
        if (StringUtils.isNotEmpty(result)) {
            JSONObject jsonObject = JSONObject.parseObject(result);
            Integer code = jsonObject.getInteger("code");
            String msg = jsonObject.getString("msg");
            if (code.equals(200)) {//发起支付成功
                withdrawalResult.setCode(200);
                withdrawalResult.setMsg("发起代付成功");
            } else {//发起支付失败
                withdrawalResult.setCode(500);
                withdrawalResult.setMsg(msg);
            }
            withdrawalResult.setOrderNo(requestId);
            return withdrawalResult;
        } else {
            //无法确定 上游那边情况，这里默认设置成代付成功，
            withdrawalResult.setCode(200);
            withdrawalResult.setMsg("发起代付成功");
        }
        return withdrawalResult;
    }

    @Override
    public DepositResult deposit(DepositDto depositDto, String requestId) throws Exception {
        return null;
    }

    @Override
    public boolean depositCallback(Map<String, Object> parameterMap) throws Exception {
        return false;

    }

    @Override
    public WithdrawalCallbackResult withdrawalCallback(Map<String, Object> parameterMap) throws Exception {
        TChannel channel = channelService.getWithdrawalChannel(keyword);
        if (channel == null) {
            log.info("奈斯代付回调没有可用的通道");
            throw new CustomException("代付回调通道没有配置");
        }
        Map<String, Object> paramMap = new HashMap<>();
        String status =  parameterMap.get("OrderStatus").toString();
        //不加入签名
        String sign = (String) parameterMap.get("Sign");
        String checkSign = sortSign(parameterMap, channel.getPriKey());
        WithdrawalCallbackResult result = new WithdrawalCallbackResult();
//        result.setRealAmount(realAmount);
        result.setOrderNo(parameterMap.get("OutTradeNo").toString());
        result.setUpOrderNo(parameterMap.get("TradeNo").toString());
        if(StringUtils.equals(sign,checkSign)){
            if("2".equals(status)){
                log.info("奈斯代付回调成功：{}",parameterMap);
                result.setStatus(2);
            } else {
                log.info("奈斯代付回调失败：{}",parameterMap);
                result.setErrMsg("代付失败");
                result.setStatus(3);
            }
            result.setSuccessMsg("success");
        } else {
            log.info("奈斯代付回调验签失败：{}",parameterMap);
            result.setStatus(1);
            result.setSuccessMsg("fail");
        }
        return result;
    }

    @Override
    public String getOrderNo(Map<String, Object> parameterMap) {
        return parameterMap.get("OutTradeNo").toString();
    }

    @Override
    public BigDecimal getRealAmount(Map<String, Object> parameterMap) {
        return null;
    }

    @Override
    public String getSuccessMsg() {
        return "SUCCESS";
    }

    @Override
    public BalanceResult getBalance() {
        //获取配置的通道
        TChannel channel = channelService.getWithdrawalChannel(keyword);
        if (channel == null) {
            throw new CustomException("通道没有配置");
        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("OpenId", channel.getMerchantId());
        paramMap.put("TimeStamp", DateUtils.getNowDate().getTime());
        //不加入签名
        paramMap.put("Sign", sortSign(paramMap, channel.getPriKey()));
        // 发送支付请求
        String result = OkHttpUtils.getInstance().doPostForm(channel.getApiUrl()+"Query/AmtInfo", MapDataUtil.Object2String(paramMap));
        log.info("奈斯查询接口余额发起，参数：{}，结果：{}", paramMap, result);
        BalanceResult balanceResult = new BalanceResult();
        if (StringUtils.isNotEmpty(result)) {
            JSONObject jsonObject = JSONObject.parseObject(result);
            Integer code = jsonObject.getInteger("code");
            if (code.equals(200)) {//发起支付成功
                JSONObject data = jsonObject.getJSONObject("data");
                balanceResult.setBalance(data.getString("Usable"));
            } else {//发起支付失败
                balanceResult.setMessage(jsonObject.getString("msg"));
            }
        } else {
            balanceResult.setMessage("当前通道不可用");
        }
        balanceResult.setName("奈斯");
        return balanceResult;
    }


    private String sortSign(Map<String, ? extends Object> nvps, String priKey) {
        nvps.remove("Sign");
        Object[] key = nvps.keySet().toArray();
        Arrays.sort(key);
        StringBuilder buf = new StringBuilder();
        String signatureStr = "";
        for (int i = 0; i < key.length; i++) {
            if (StringUtils.isNotEmpty(nvps.get(key[i]).toString())) {
                buf.append(key[i]).append("=").append(nvps.get(key[i]).toString()).append("&");
            }
        }
        signatureStr = buf.append("key=").append(priKey).toString();
        return StringUtils.upperCase(DigestUtils.md5Hex(signatureStr));
    }
}
