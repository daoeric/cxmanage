package com.ruoyi.customer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.channel.service.ITChannelService;
import com.ruoyi.channel.service.ITChannelSetService;
import com.ruoyi.channel.service.ITSettlementPlanService;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.dto.DepositDto;
import com.ruoyi.common.dto.ListDto;
import com.ruoyi.common.dto.ManualDepositDto;
import com.ruoyi.common.dto.TChannelDto;
import com.ruoyi.common.dto.admin.DepositSearchDto;
import com.ruoyi.common.enums.BillOperateTypeEnum;
import com.ruoyi.common.enums.ExceptionEnum;
import com.ruoyi.common.enums.OrderEnum;
import com.ruoyi.common.enums.StatusEnum;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.*;
import com.ruoyi.common.utils.http.OkHttpUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.common.utils.uuid.SnowflakeKeyGenerator;
import com.ruoyi.common.vo.PaymentRequestVO;
import com.ruoyi.common.vo.front.CallbackVO;
import com.ruoyi.common.vo.front.TPaymentRequestVO;
import com.ruoyi.common.vo.payment.DepositResult;
import com.ruoyi.customer.domain.TCreditLog;
import com.ruoyi.customer.domain.TCustomer;
import com.ruoyi.customer.domain.TPaymentRequest;
import com.ruoyi.customer.mapper.TPaymentRequestMapper;
import com.ruoyi.customer.service.ITCreditLogService;
import com.ruoyi.customer.service.ITCustomerService;
import com.ruoyi.customer.service.ITPaymentRequestService;
import com.ruoyi.payment.IPaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 商户充值订单Service业务层处理
 * 
 * @author yf
 * @date 2022-04-17
 */
@Service
@Slf4j
public class TPaymentRequestServiceImpl extends ServiceImpl<TPaymentRequestMapper,TPaymentRequest> implements ITPaymentRequestService
{
    @Autowired
    private TPaymentRequestMapper tPaymentRequestMapper;

    @Autowired
    private SnowflakeKeyGenerator snowflakeKeyGenerator;

    @Autowired
    private ITCustomerService customerService;

    @Autowired
    private ITCreditLogService creditLogService;

    @Autowired
    private ITChannelService channelService;

    @Autowired
    private ITSettlementPlanService planService;

    @Autowired
    private ITChannelSetService channelSetService;


    /**
     * 查询商户充值订单
     * 
     * @param requestId 商户充值订单ID
     * @return 商户充值订单
     */
    @Override
    public TPaymentRequest selectTPaymentRequestById(String requestId)
    {
        return tPaymentRequestMapper.selectTPaymentRequestById(requestId);
    }

    /**
     * 查询商户充值订单列表
     * 
     * @param tPaymentRequest 商户充值订单
     * @return 商户充值订单
     */
    @Override
    public List<TPaymentRequest> selectTPaymentRequestList(TPaymentRequest tPaymentRequest)
    {
        return tPaymentRequestMapper.selectTPaymentRequestList(tPaymentRequest);
    }

    /**
     * 新增商户充值订单
     * 
     * @param dto 商户充值订单
     * @return 结果
     */
    @Override
    public boolean insertTPaymentRequest(ManualDepositDto dto) throws Exception {
        BigDecimal uIncome = dto.getUsdtIncome();
        BigDecimal channelURate = dto.getChannelURate();
        BigDecimal mchURate = dto.getMchURate();
        BigDecimal orderAmount = uIncome.multiply(mchURate);
        BigDecimal usdtProfit = (channelURate.subtract(mchURate).multiply(uIncome));
        DepositDto depositDto = new DepositDto();
        depositDto.setMchId(dto.getMchId());
        depositDto.setAmount(orderAmount);
        depositDto.setUIncome(uIncome);
        depositDto.setChannelURate(channelURate);
        depositDto.setMchURate(mchURate);
        depositDto.setUsdtProfit(usdtProfit);
        depositDto.setPayChannel("A888");
        depositDto.setAlias(dto.getAlias());
        return createUSDTDeposit(depositDto).getCode()==200;
    }

    /**
     * 修改商户充值订单
     * 
     * @param tPaymentRequest 商户充值订单
     * @return 结果
     */
    @Override
    public int updateTPaymentRequest(TPaymentRequest tPaymentRequest)
    {
        tPaymentRequest.setUpdateTime(DateUtils.getNowDate());
        return tPaymentRequestMapper.updateTPaymentRequest(tPaymentRequest);
    }

    /**
     * 批量删除商户充值订单
     * 
     * @param requestIds 需要删除的商户充值订单ID
     * @return 结果
     */
    @Override
    public int deleteTPaymentRequestByIds(String[] requestIds)
    {
        return tPaymentRequestMapper.deleteTPaymentRequestByIds(requestIds);
    }

    /**
     * 删除商户充值订单信息
     * 
     * @param requestId 商户充值订单ID
     * @return 结果
     */
    @Override
    public int deleteTPaymentRequestById(String requestId)
    {
        return tPaymentRequestMapper.deleteTPaymentRequestById(requestId);
    }

    /**
     * 手动充U
     * @param depositDto
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public DepositResult createUSDTDeposit(DepositDto depositDto) throws Exception {
        DepositResult depositResult = new DepositResult();
        Long mchId = depositDto.getMchId();
        TCustomer customer = customerService.getById(mchId);
        if (customer == null) {
            throw new CustomException("商户号不存在");
        }
        Integer status = customer.getStatus();
        if (status != 0) {
            throw new CustomException("商户号被禁用");
        }
        Long agentId = customer.getAgentId();
        TChannelDto channel = channelService.getEnableChanel(customer.getId(), Constants.RUJIN_CODE);
        //BigDecimal mchRate = channel.getMchRate();
        //BigDecimal mchCost = depositDto.getAmount().multiply(mchRate.divide(new BigDecimal(100)));
        BigDecimal mchCost = BigDecimal.ZERO;
        BigDecimal profit = mchCost;
        String requestId = "DP" + snowflakeKeyGenerator.generateKey();
        TPaymentRequest paymentRequest = new TPaymentRequest();
        paymentRequest.setRequestId(requestId);
        paymentRequest.setMchId(mchId);
        paymentRequest.setStatus(OrderEnum.SUCCESS.getCode());
        paymentRequest.setOrderAmount(depositDto.getAmount());
        paymentRequest.setRealAmount(depositDto.getAmount());
        paymentRequest.setCreateTime(DateUtils.getNowDate());
        paymentRequest.setUpdateTime(DateUtils.getNowDate());
        paymentRequest.setAgentId(agentId);
        paymentRequest.setProductId("A999");
        paymentRequest.setMchURate(depositDto.getMchURate());
        paymentRequest.setChannelURate(depositDto.getChannelURate());
        paymentRequest.setUsdtProfit(depositDto.getUsdtProfit());
        paymentRequest.setUIncome(depositDto.getUIncome());
        paymentRequest.setAlias(depositDto.getAlias());
        paymentRequest.setFee(mchCost);
        paymentRequest.setProfit(profit);
        paymentRequest.setMchRate(BigDecimal.ZERO);
        this.save(paymentRequest);
        //额度变更表
        Date now = new Date();
        BigDecimal opearteAmount = depositDto.getAmount().subtract(mchCost);
        BigDecimal preBalance = customer.getBalance();
        BigDecimal postBalance = preBalance.add(opearteAmount);
        TCreditLog creditLog = new TCreditLog();
        creditLog.setId("LG"+snowflakeKeyGenerator.generateKey());
        creditLog.setMchId(mchId);
        creditLog.setRefId(requestId);
        creditLog.setOpearteType(BillOperateTypeEnum.DEPOSIT.getCode());
        creditLog.setOpearteAmount(depositDto.getAmount());
        creditLog.setPreBalance(preBalance);
        creditLog.setPostBalance(postBalance);
        creditLog.setCreateTime(now);
        creditLog.setCreateBy(SecurityUtils.getUsername());
        creditLog.setUpdateTime(now);
        creditLog.setUpdateBy(SecurityUtils.getUsername());
        creditLog.setFee(paymentRequest.getFee().negate());
        creditLogService.save(creditLog);
        //更新用户额度
        customerService.addAmount(mchId,opearteAmount,1);
        depositResult.setCode(200);
        return depositResult;
    }

    @Override
    @Transactional
    public DepositResult create(DepositDto depositDto,boolean isManual) throws Exception{
        Long mchId = depositDto.getMchId();
        TCustomer customer = customerService.getById(mchId);
        if (customer == null) {
            throw new CustomException("商户号不存在");
        }
        Integer status = customer.getStatus();
        if (status != 0) {
            throw new CustomException("商户号被禁用");
        }
        Integer planId = customer.getPlanId();
        if (planId == null) {
            throw new CustomException("商户没有配置通道");
        }
        //查看用户是否分配了通道
        String productId = depositDto.getPayChannel().trim();
        TChannelDto channel = channelService.getEnableChanel(customer.getId(), productId);
        if (channel == null || channel.getStatus() != StatusEnum.OK.getCode()) {
            log.info("代收通道维护中：{}",depositDto);
            throw new CustomException("当前没有配置通道或通道维护中");
        }
        //查看代收金额是否匹配这个通道
        BigDecimal amount = depositDto.getAmount();
        BigDecimal minAmount = channel.getMinAmount();
        BigDecimal maxAmount = channel.getMaxAmount();
        String fixAmount = channel.getFixAmount();
        if (StringUtils.isEmpty(fixAmount)) {
            //没有固定金额,判断最小最大
            if(amount.compareTo(minAmount)<0 || amount.compareTo(maxAmount)>0){
                throw new CustomException("该通道限额:"+minAmount+"-"+maxAmount);
            }
        } else {
            String[] fixAmountArray = fixAmount.split(",");
            boolean amountCheck = false;
            for (String s : fixAmountArray) {
                if(amount.compareTo(new BigDecimal(s)) == 0){
                    amountCheck = true;
                    break;
                }
            }
            if (!amountCheck) {
                throw new CustomException("该通道固定限额:"+fixAmount);
            }
        }

        BigDecimal channelRate = channel.getChannelRate();
        BigDecimal mchRate = channel.getMchRate();
        if (channelRate.compareTo(mchRate)>0) {
            mchRate = channelRate;
        }
        BigDecimal agentRate = BigDecimal.ZERO;
        // 查看是否有上级代理
        Long agentId = customer.getAgentId();
        if(agentId != null){
            //计算代理费率
            TCustomer agent = customerService.getById(agentId);
            if (agent != null) {
                Integer agentPlanId = agent.getPlanId();
                if (agentPlanId != null) {
                    TChannelDto agentChannel = channelService.getEnableChanel(customer.getId(), productId);
                    if (agentChannel != null) {
                        agentRate = agentChannel.getMchRate();
                    }
                }
            }
        }
        if(agentRate.compareTo(BigDecimal.ZERO)>0 && (agentRate.compareTo(channelRate) < 0 || agentRate.compareTo(mchRate)>0)){
            log.error("代理通道费率配置错误:{}",channel);
            agentRate = BigDecimal.ZERO;
        }

        String code = channel.getCode();//注入是哪个Bean
        //查看下游订单是否重复
        String outNo = depositDto.getOrderId();
        if(StringUtils.isNotEmpty(outNo) && this.checkOutNoExist(mchId,outNo)){
            throw new CustomException(ExceptionEnum.ORDER_REPEART_ERROR);
        }
        //发起订单
        // 计算利润
        BigDecimal mchCost = amount.multiply(mchRate.divide(new BigDecimal(100)));
        BigDecimal channelCost = amount.multiply(channelRate.divide(new BigDecimal(100)));
        BigDecimal agentCost = agentRate.compareTo(BigDecimal.ZERO)==0?BigDecimal.ZERO:amount.multiply(mchRate.subtract(agentRate).divide(new BigDecimal(100)));
        BigDecimal profit = mchCost.subtract(channelCost).subtract(agentCost);
        TPaymentRequest paymentRequest = new TPaymentRequest();
        IPaymentService paymentService = SpringUtils.getBean(code);
        String requestId = "DP" + snowflakeKeyGenerator.generateKey();
        DepositResult depositResult = new DepositResult();
        if(!isManual){
            depositResult = paymentService.deposit(depositDto,requestId);
        }
        if (isManual || depositResult.getCode() == 200) {
            depositResult.setCode(200);
            paymentRequest.setRequestId(requestId);
            paymentRequest.setMchId(mchId);
            paymentRequest.setOrderAmount(depositDto.getAmount());
            paymentRequest.setRealAmount(depositDto.getAmount());
            paymentRequest.setCreateTime(DateUtils.getNowDate());
            paymentRequest.setUpdateTime(DateUtils.getNowDate());
            paymentRequest.setAgentId(agentId);
            paymentRequest.setChannelId(channel.getChannelId());
            paymentRequest.setOutOrderNo(depositDto.getOrderId());
            paymentRequest.setNotifyUrl(depositDto.getNotifyUrl());
            paymentRequest.setAttach(depositDto.getAttach());
            paymentRequest.setChannelRate(channelRate);
            paymentRequest.setMchRate(mchRate);
            paymentRequest.setAgentRate(agentRate);
            paymentRequest.setChannelCost(channelCost);
            paymentRequest.setAgentCost(agentCost);
            paymentRequest.setFee(mchCost);
            paymentRequest.setProfit(profit);
            paymentRequest.setProductId(productId);
            paymentRequest.setMchURate(depositDto.getMchURate());
            paymentRequest.setChannelURate(depositDto.getChannelURate());
            paymentRequest.setUsdtProfit(depositDto.getUsdtProfit());
            paymentRequest.setUIncome(depositDto.getUIncome());
            paymentRequest.setAlias(depositDto.getAlias());
            if (isManual) {
                paymentRequest.setStatus(OrderEnum.SUCCESS.getCode());
            } else {
                paymentRequest.setStatus(OrderEnum.PENDDING.getCode());
            }
            this.save(paymentRequest);
        }
        return depositResult;
    }

    private boolean checkOutNoExist(Long mchId, String outNo) {
        QueryWrapper<TPaymentRequest> wrapper = new QueryWrapper<>();
        wrapper.eq("mch_id",mchId);
        wrapper.eq("out_order_no",outNo);
        return tPaymentRequestMapper.selectCount(wrapper)>0;

    }

    @Override
    @Transactional
    public boolean doSuccess(String billNo, BigDecimal amount) {
        //设置订单成已支付
        Date now = DateUtils.getNowDate();
        TPaymentRequest paymentRequest = this.getById(billNo);
        if (paymentRequest == null || !OrderEnum.PENDDING.getCode().equals(paymentRequest.getStatus())) {
            return false;
        }
        BigDecimal orderAmount = paymentRequest.getOrderAmount();
        BigDecimal realAmount = amount == null ? orderAmount:amount;
        //如果 真实金额与订单金额不一致,则需要重新计算手续费,费率等
        BigDecimal agentProfit = paymentRequest.getAgentCost();
        BigDecimal mchRate = paymentRequest.getMchRate();
        BigDecimal agentRate = paymentRequest.getAgentRate();
        BigDecimal channelRate = paymentRequest.getChannelRate();
        if (orderAmount.compareTo(realAmount) != 0) {
            // 计算利润
            //BigDecimal mchCost = realAmount.multiply(mchRate.divide(new BigDecimal(100)));
            BigDecimal mchCost = BigDecimal.ZERO;
            BigDecimal channelCost = realAmount.multiply(channelRate.divide(new BigDecimal(100)));
            BigDecimal agentCost = agentRate.compareTo(BigDecimal.ZERO)==0?BigDecimal.ZERO:realAmount.multiply(mchRate.subtract(agentRate).divide(new BigDecimal(100)));
            BigDecimal profit = mchCost.subtract(channelCost).subtract(agentCost);
            paymentRequest.setFee(mchCost);
            paymentRequest.setProfit(profit);
            paymentRequest.setAgentCost(agentProfit);
            paymentRequest.setChannelCost(channelCost);
        }
        if (agentProfit.compareTo(BigDecimal.ZERO)>0) {
            handleAgentCommission(agentProfit,paymentRequest.getAgentId(),paymentRequest.getRequestId());
        }
        BigDecimal opearteAmount = realAmount.subtract(paymentRequest.getFee());
        Long userId = paymentRequest.getMchId();
        TCustomer customer = customerService.selectTCustomerById(userId);
        String username = customer.getUsername();
        paymentRequest.setSuccessTime(now);
        paymentRequest.setUpdateTime(now);
        paymentRequest.setUpdateBy(customer.getUsername());
        paymentRequest.setRealAmount(realAmount);
        paymentRequest.setStatus(OrderEnum.SUCCESS.getCode());
        this.updateById(paymentRequest);
        //额度变更表
        BigDecimal preBalance = customer.getBalance();
        BigDecimal postBalance = preBalance.add(opearteAmount);
        TCreditLog creditLog = new TCreditLog();
        creditLog.setId("LG"+snowflakeKeyGenerator.generateKey());
        creditLog.setMchId(userId);
        creditLog.setRefId(billNo);
        creditLog.setOpearteType(BillOperateTypeEnum.DEPOSIT.getCode());
        creditLog.setOpearteAmount(realAmount);
        creditLog.setPreBalance(preBalance);
        creditLog.setPostBalance(postBalance);
        creditLog.setCreateTime(now);
        creditLog.setCreateBy(username);
        creditLog.setUpdateTime(now);
        creditLog.setUpdateBy(username);
        creditLog.setFee(paymentRequest.getFee().negate());
        creditLogService.save(creditLog);
        //更新用户额度
        return customerService.addAmount(userId,opearteAmount,1);
    }

    /**
     * 处理代理佣金
     */
    public boolean handleAgentCommission(BigDecimal commission,Long agentId,String orderNo){
        log.info("{}订单号,给代理[{}]添加佣金:{}",orderNo,agentId,commission);
        if (commission != null && commission.compareTo(BigDecimal.ZERO)>0) {
            TCustomer agent = customerService.getById(agentId);
            //额度变更表
            Date now = DateUtils.getNowDate();
            BigDecimal preBalance = agent.getBalance();
            BigDecimal postBalance = preBalance.add(commission);
            TCreditLog creditLog = new TCreditLog();
            creditLog.setId("LG"+snowflakeKeyGenerator.generateKey());
            creditLog.setMchId(agentId);
            creditLog.setRefId(orderNo);
            creditLog.setOpearteType(BillOperateTypeEnum.COMMISSION.getCode());
            creditLog.setOpearteAmount(commission);
            creditLog.setPreBalance(preBalance);
            creditLog.setPostBalance(postBalance);
            creditLog.setCreateTime(now);
            creditLog.setCreateBy(agent.getUsername());
            creditLog.setUpdateTime(now);
            creditLog.setUpdateBy(agent.getUsername());
            creditLog.setFee(BigDecimal.ZERO);
            creditLogService.save(creditLog);
            //更新用户额度
            return customerService.addAmount(agentId,commission,1);
        }
        return false;
    }

    @Override
    public List<PaymentRequestVO> listOrder(DepositSearchDto tPaymentRequest) {
        return this.tPaymentRequestMapper.listOrder(tPaymentRequest);
    }

    @Override
    public String callbackCustomer(TPaymentRequest paymentRequest) {
        //拼凑回调参数
        CallbackVO vo = new CallbackVO();
        vo.setMchId(paymentRequest.getMchId());
        vo.setOrderNo(paymentRequest.getOutOrderNo());
        vo.setBillNo(paymentRequest.getRequestId());
        vo.setAmount(paymentRequest.getOrderAmount());
        vo.setAttach(paymentRequest.getAttach());
        vo.setStatus(2);
        Map<String,Object> map = MapDataUtil.object2Map(vo);
        TCustomer customer = customerService.getById(paymentRequest.getMchId());
        String notifyUrl = paymentRequest.getNotifyUrl();
        if (StringUtils.isEmpty(notifyUrl)) {
            log.error("{}订单号无法回调，下游没有传回调地址",paymentRequest.getOutOrderNo());
            return null;
        }
        String sign = PaymentUtil.sortSign(map, customer.getPriKey());
        map.put("sign",sign);
        Map<String,String> newMap = map.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue()==null?"":String.valueOf(e.getValue())));
        String result = OkHttpUtils.getInstance().doPostForm(notifyUrl,newMap);
        if ("ok".equals(result)) {
            //回调成功 ,更新订单表回调状态字段
            UpdateWrapper<TPaymentRequest> wrapper = new UpdateWrapper<>();
            wrapper.eq("request_id",paymentRequest.getRequestId());
            wrapper.set("callback_status",1);
            this.update(wrapper);
        }
        return result;
    }

    @Override
    public TPaymentRequest queryOrder(Long mchId, String orderNo) {
        QueryWrapper<TPaymentRequest> wrapper = new QueryWrapper<>();
        wrapper.eq("mch_id",mchId).eq("out_order_no",orderNo);
        return this.getOne(wrapper);
    }

    @Override
    public Map<String, Object> sum(ListDto dto, Long mchId) {
        TCustomer customer = customerService.getById(mchId);
        return tPaymentRequestMapper.sum(dto,mchId,customer.getUserType()==1);
    }

    @Override
    public Map<String, Object> successSum(ListDto dto, Long mchId,Integer usetType) {
        return tPaymentRequestMapper.successSum(dto,mchId,usetType==1);
    }

    @Override
    public Map<String, Object> sum(DepositSearchDto dto) {
        return tPaymentRequestMapper.listOrderSum(dto);
    }

    @Override
    public Map<String, Object> depositInfo(Integer status) {
        //获取交易总数
        QueryWrapper<TPaymentRequest> wrapper = new QueryWrapper<>();
        Date now = DateUtils.getNowDate();
        Date begin = null;
        Date end = null;
        try {
            begin = DateUtils.getDateBegin(now);
            end = DateUtils.getDateEnd(now);
        } catch (ParseException e) {
            log.info("获取统计异常：{}",e.getMessage(),e);
        }
        wrapper.le("create_time",end);
        wrapper.ge("create_time",begin);
        if (status != null) {
            wrapper.select(" count(1) as successCount,IFNULL(sum(order_amount),0) as successAmount ");
            wrapper.eq("status",status);
        } else {
            wrapper.select(" count(1) as totalCount,IFNULL(sum(order_amount),0) as totalAmount ");
        }


        return  getMap(wrapper);
    }

    @Override
    public List<Map<String, Object>> depositChatData() {
        QueryWrapper<TPaymentRequest> wrapper = new QueryWrapper<>();
        Date begin = null;
        Date end = null;
        Date now = DateUtils.getNowDate();
        try {
            begin = DateUtils.getDateBegin(DateUtils.getDateBegin(DateUtils.addDays(now,-7)));
            end = DateUtils.getDateEnd(now);
        } catch (ParseException e) {
            log.info("获取统计异常：{}",e.getMessage(),e);
        }
        wrapper.le("create_time",end);
        wrapper.ge("create_time",begin);
        wrapper.eq("status",OrderEnum.SUCCESS.getCode());
        wrapper.select("IFNULL(sum(order_amount),0) as successAmount,date_format(create_time, '%Y-%m-%d') as dataStr");
        wrapper.groupBy("date_format(create_time, '%Y-%m-%d')");
        return listMaps(wrapper);
    }

    @Override
    public List<TPaymentRequestVO> listCustomer(ListDto dto, Long merId, boolean isAgent) {
        return tPaymentRequestMapper.listCustomer(dto,merId,isAgent);
    }

    @Override
    public void dayReport() throws ParseException {
        Date now = new Date();
        Date yesterDay = DateUtils.addDays(now,-1);
        Date begin = DateUtils.getDateBegin(yesterDay);
        Date end = DateUtils.getDateEnd(yesterDay);
        tPaymentRequestMapper.insertDayReport(begin,end);
    }

    @Override
    public void channelDayReport() throws ParseException{
        Date now = new Date();
        Date yesterDay = DateUtils.addDays(now,-1);
        Date begin = DateUtils.getDateBegin(yesterDay);
        Date end = DateUtils.getDateEnd(yesterDay);
        tPaymentRequestMapper.insertChannelDayReport(begin,end);
    }

}
