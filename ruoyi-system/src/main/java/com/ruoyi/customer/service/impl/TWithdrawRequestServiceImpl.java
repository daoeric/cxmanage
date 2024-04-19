package com.ruoyi.customer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.channel.domain.TChannelSet;
import com.ruoyi.channel.service.ITChannelService;
import com.ruoyi.channel.service.ITChannelSetService;
import com.ruoyi.channel.service.ITSettlementPlanService;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.dto.ListDto;
import com.ruoyi.common.dto.TChannelDto;
import com.ruoyi.common.dto.WithdrawalAddDto;
import com.ruoyi.common.dto.WithdrawalDto;
import com.ruoyi.common.dto.admin.MualWithAddDto;
import com.ruoyi.common.dto.admin.WithdrawalSearchDto;
import com.ruoyi.common.enums.BillOperateTypeEnum;
import com.ruoyi.common.enums.ExceptionEnum;
import com.ruoyi.common.enums.OrderEnum;
import com.ruoyi.common.enums.StatusEnum;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.*;
import com.ruoyi.common.utils.google.GoogleAuthenticator;
import com.ruoyi.common.utils.http.OkHttpUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.common.utils.uuid.SnowflakeKeyGenerator;
import com.ruoyi.common.vo.WithdrawalRequestVO;
import com.ruoyi.common.vo.front.CallbackVO;
import com.ruoyi.common.vo.payment.WithdrawalResult;
import com.ruoyi.customer.domain.TCreditLog;
import com.ruoyi.customer.domain.TCustomer;
import com.ruoyi.customer.domain.TWithdrawRequest;
import com.ruoyi.customer.mapper.TCustomerMapper;
import com.ruoyi.customer.mapper.TWithdrawRequestMapper;
import com.ruoyi.customer.service.ITCreditLogService;
import com.ruoyi.customer.service.ITCustomerService;
import com.ruoyi.customer.service.ITWithdrawRequestService;
import com.ruoyi.payment.FanChaService;
import com.ruoyi.payment.IPaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 代付提现Service业务层处理
 * 
 * @author yf
 * @date 2022-04-17
 */
@Service
@Slf4j
public class TWithdrawRequestServiceImpl extends ServiceImpl<TWithdrawRequestMapper,TWithdrawRequest> implements ITWithdrawRequestService
{
    @Autowired
    private TWithdrawRequestMapper tWithdrawRequestMapper;

    @Autowired
    private ITCustomerService customerService;

    @Autowired
    private SnowflakeKeyGenerator snowflakeKeyGenerator;

    @Autowired
    private ITCreditLogService creditLogService;

    @Autowired
    private ITSettlementPlanService planService;

    @Autowired
    private ITChannelService channelService;

    @Autowired
    private ITWithdrawRequestService withdrawRequestService;

    @Autowired
    private RedisLock redisLock;

    @Autowired
    private TCustomerMapper customerMapper;

    @Autowired
    private FanChaService fanChaService;

    @Autowired
    private ITChannelSetService channelSetService;

    /**
     * 查询代付提现
     * 
     * @param withdrawId 代付提现ID
     * @return 代付提现
     */
    @Override
    public TWithdrawRequest selectTWithdrawRequestById(String withdrawId)
    {
        return tWithdrawRequestMapper.selectTWithdrawRequestById(withdrawId);
    }

    /**
     * 查询代付提现列表
     * 
     * @param tWithdrawRequest 代付提现
     * @return 代付提现
     */
    @Override
    public List<TWithdrawRequest> selectTWithdrawRequestList(TWithdrawRequest tWithdrawRequest)
    {
        return tWithdrawRequestMapper.selectTWithdrawRequestList(tWithdrawRequest);
    }

    /**
     * 新增代付提现
     * 
     * @param tWithdrawRequest 代付提现
     * @return 结果
     */
    @Override
    public int insertTWithdrawRequest(TWithdrawRequest tWithdrawRequest)
    {
        tWithdrawRequest.setCreateTime(DateUtils.getNowDate());
        return tWithdrawRequestMapper.insertTWithdrawRequest(tWithdrawRequest);
    }

    /**
     * 修改代付提现
     * 
     * @param tWithdrawRequest 代付提现
     * @return 结果
     */
    @Override
    public int updateTWithdrawRequest(TWithdrawRequest tWithdrawRequest)
    {
        tWithdrawRequest.setUpdateTime(DateUtils.getNowDate());
        return tWithdrawRequestMapper.updateTWithdrawRequest(tWithdrawRequest);
    }

    /**
     * 批量删除代付提现
     * 
     * @param withdrawIds 需要删除的代付提现ID
     * @return 结果
     */
    @Override
    public int deleteTWithdrawRequestByIds(String[] withdrawIds)
    {
        return tWithdrawRequestMapper.deleteTWithdrawRequestByIds(withdrawIds);
    }

    /**
     * 删除代付提现信息
     * 
     * @param withdrawId 代付提现ID
     * @return 结果
     */
    @Override
    public int deleteTWithdrawRequestById(String withdrawId)
    {
        return tWithdrawRequestMapper.deleteTWithdrawRequestById(withdrawId);
    }

    /**
     * 发起代付请求
     *
     * @param withdrawalDto
     * @return
     */
    @Override
    @Transactional
    public TWithdrawRequest create(WithdrawalDto withdrawalDto,boolean isAuto) throws Exception {
        // 加上分布式锁
        String key = "withdrawal:create:"+withdrawalDto.getMchId();
        try{
            if (redisLock.tryLock(key, 10, 30, TimeUnit.SECONDS)){
                TCustomer customer = customerMapper.findByIdForUpdate(withdrawalDto.getMchId());
                String orderId = withdrawalDto.getOrderId();
                BigDecimal preBalance = customer.getBalance();
                //查看商户是否已经配置过结算方式
//                Integer planId = customer.getPlanId();
//                if (planId == null) {
//                    log.info("{}代付失败，没有配置结算方式",orderId);
//                    throw new CustomException(ExceptionEnum.NO_PLAN_CONFIG);
//                }
                //查看订单号是否重复
                if (this.checkOrderExist(withdrawalDto.getMchId(),withdrawalDto.getOrderId())) {
                    log.info("{}订单号重复",orderId);
                    throw new CustomException(ExceptionEnum.ORDER_REPEART_ERROR);
                }
                //获取商户费率配置
                TChannelSet channelSet = channelSetService.getOne(customer.getId(),"A888");

                //获取手续费
                BigDecimal fee = channelSet.getDaifuFee();
                BigDecimal rate = channelSet.getRate();
                BigDecimal amount = withdrawalDto.getAmount();
                BigDecimal mchCost = amount.multiply(rate.divide(new BigDecimal(100))).setScale(2,RoundingMode.HALF_UP);
                BigDecimal totalCost = fee.add(mchCost);
                TChannelDto channel = channelService.getEnableChanel(customer.getId(), "A888");
                if (channel == null || channel.getStatus() != StatusEnum.OK.getCode()) {
                    throw new CustomException("当前通道维护中");
                }
                BigDecimal channelFee = channel.getChannelFee();
                //检查代付金额是否限额
                BigDecimal max = channelSet.getDaifuMaxAmount();
                if (max != null && amount.compareTo(max)> 0) {
                    log.info("{}订单号金额超出最大限额",orderId);
                    throw new CustomException("订单号金额超出最大限额");
                }
                BigDecimal min = channelSet.getDaifuMinAmount();
                if (min != null && amount.compareTo(min) < 0) {
                    log.info("{}订单金额低于最小限额",orderId);
                    throw new CustomException("订单金额低于最小限额");
                }
                // 获取商户余额
                BigDecimal currentBalance = customer.getBalance();
                BigDecimal opearteAmount = amount.add(totalCost);
                if (currentBalance.compareTo(opearteAmount)<0){
                    log.info("{}代付失败，余额不足",orderId);
                    throw new CustomException(ExceptionEnum.AVAILABLE_BALANCE_NOT_ENOUGH);
                }

                BigDecimal postBalance = currentBalance.subtract(opearteAmount);
                //修改用户额度
                log.info("{}代付开始扣去余额",withdrawalDto);
                String withdrawalId = isAuto?"WT"+snowflakeKeyGenerator.generateKey():"MAUL"+snowflakeKeyGenerator.generateKey();
                TWithdrawRequest withdrawRequest = new TWithdrawRequest();
                if(customerService.withdrawal(withdrawalDto.getMchId(),amount,totalCost,1,withdrawalId)){
                    //新增代付订单
                    withdrawRequest.setWithdrawId(withdrawalId);
                    withdrawRequest.setWithdrawAmount(withdrawalDto.getAmount());
                    withdrawRequest.setMchId(withdrawalDto.getMchId());
                    withdrawRequest.setStatus(OrderEnum.PENDDING.getCode());
                    withdrawRequest.setFee(fee);
                    withdrawRequest.setRateFee(mchCost);
                    withdrawRequest.setNotifyUrl(withdrawalDto.getNotifyUrl());
                    withdrawRequest.setChannelId(channel.getChannelId());
                    withdrawRequest.setAttach(withdrawalDto.getAttach());
                    withdrawRequest.setBankName(withdrawalDto.getBankName());
                    withdrawRequest.setBankNo(withdrawalDto.getBankNo());
                    withdrawRequest.setChannelFee(channelFee);
                    withdrawRequest.setBankRealname(withdrawalDto.getAccountName());
                    withdrawRequest.setOutOrderNo(withdrawalDto.getOrderId());
                    withdrawRequest.setOrderType(1);
                    //新增额度变更订单
                    TCreditLog creditLog = new TCreditLog();
                    creditLog.setId("LG"+snowflakeKeyGenerator.generateKey());
                    creditLog.setMchId(withdrawalDto.getMchId());
                    creditLog.setOpearteType(BillOperateTypeEnum.WITHDRAWAL.getCode());
                    creditLog.setRefId(withdrawalId);
                    creditLog.setPreBalance(preBalance);
                    creditLog.setPostBalance(postBalance);
                    creditLog.setOpearteAmount(amount.negate());
                    creditLog.setFee(totalCost.negate());
                    creditLog.setMchNo(withdrawalDto.getOrderId());
                    if(creditLogService.save(creditLog)){
                        //获取对应的上游接口
                        String code = channel.getCode();
                        log.info("{}代付数据修改完毕，开始请求上游:{}",withdrawalId,code);
                        if (StringUtils.isNotEmpty(code)) {
                            // 手动代付的话不需要请求上游
                            if(channel.getChannelId() == 1) {
                                if(this.save(withdrawRequest)){
                                    return withdrawRequest;
                                } else {
                                    log.warn("内部处理失败3:{}",withdrawalDto);
                                    throw new CustomException("内部处理失败,请稍后再试");
                                }
                            } else {
                                //根据code注入对应上游的service
                                IPaymentService paymentService = SpringUtils.getBean(code);
                                WithdrawalResult withdrawalResult =  paymentService.withdrawal(withdrawalDto,withdrawalId);
                                if(200 == withdrawalResult.getCode()){
                                    log.info("代付成功：{},结果：{}",withdrawalId,withdrawalResult);
                                    withdrawRequest.setUpOrderNo(withdrawalResult.getUpOrderNo());
                                    if(this.save(withdrawRequest)){
                                        return withdrawRequest;
                                    } else {
                                        log.warn("内部处理失败3:{}",withdrawalDto);
                                        throw new CustomException("内部处理失败,请稍后再试");
                                    }

                                } else {
                                    log.info("代付失败,事务回滚：{}",withdrawalId);
                                    throw new CustomException("代付失败:"+withdrawalResult.getMsg());
                                }
                            }
                        } else {
                            log.info("{}没有绑定通道，无法进行代付",withdrawalId);
                            throw new CustomException(ExceptionEnum.NO_CHANNEL_CONFIG_ERROR);
                        }
                    } else {
                        log.warn("内部处理失败2:{}",withdrawalDto);
                        throw new CustomException("内部处理失败,请稍后再试");
                    }
                } else {
                    log.warn("内部处理失败:{}",withdrawalDto);
                    throw new CustomException("内部处理失败,请重试");
                }
            }
            return null;
        } catch (Exception e){
            log.error("代付失败：{}",e.getMessage(),e);
            throw new CustomException(e.getMessage());
        } finally {
            redisLock.unlock(key);
        }
    }

    @Override
    @Transactional
    public boolean doSuccess(String billNo, String orderNo,String remark) {
        TWithdrawRequest withdrawRequest = tWithdrawRequestMapper.findByIdForUpdate(billNo);
        if (withdrawRequest == null || !OrderEnum.PENDDING.getCode().equals(withdrawRequest.getStatus())) {
            log.info("{}待处理得订单不存在",billNo);
            return false;
        }
        Long merId = withdrawRequest.getMchId();
        BigDecimal realAmount = withdrawRequest.getWithdrawAmount();
        TCustomer customer = customerService.getById(merId);
        String username = customer.getUsername();
        Date now = DateUtils.getNowDate();

        //修改状态
        withdrawRequest.setStatus(OrderEnum.SUCCESS.getCode());
        withdrawRequest.setRealAmount(realAmount);
        withdrawRequest.setOutOrderNo(orderNo);
        withdrawRequest.setUpdateBy(username);
        withdrawRequest.setUpdateTime(now);
        withdrawRequest.setSuccessTime(now);
        withdrawRequest.setRemark(remark);
        int count = this.updateTWithdrawRequest(withdrawRequest);
        BigDecimal totalFee = withdrawRequest.getFee().add(withdrawRequest.getRateFee());
        if (count > 0) {
            //修改用户额度
            return customerService.withdrawal(merId,realAmount,totalFee,2,billNo);
        }
        return false;
    }

    @Override
    public boolean checkOrderExist(Long mchId, String orderId) {
        QueryWrapper<TWithdrawRequest> wrapper = new QueryWrapper<>();
        wrapper.eq("mch_id",mchId).eq("out_order_no",orderId);
        return this.count(wrapper) > 0;
    }

    @Override
    public TWithdrawRequest queryOrder(Long mchId, String orderId) {
        QueryWrapper<TWithdrawRequest> wrapper = new QueryWrapper<>();
        wrapper.eq("mch_id",mchId).eq("out_order_no",orderId);
        return this.getOne(wrapper);
    }

    @Override
    public String callbackCustomer(TWithdrawRequest withdrawRequest) {
        //拼凑回调参数
        CallbackVO vo = new CallbackVO();
        Integer status = withdrawRequest.getStatus();
        vo.setStatus(status);
        vo.setMchId(withdrawRequest.getMchId());
        vo.setOrderNo(withdrawRequest.getOutOrderNo());
        vo.setBillNo(withdrawRequest.getWithdrawId());
        vo.setAmount(withdrawRequest.getWithdrawAmount());
        vo.setAttach(withdrawRequest.getAttach());
        vo.setRemark(withdrawRequest.getRemark());
        Map<String,Object> map = MapDataUtil.object2Map(vo);
        TCustomer customer = customerService.getById(withdrawRequest.getMchId());
        String notifyUrl = withdrawRequest.getNotifyUrl();
        if (StringUtils.isEmpty(notifyUrl)) {
            return null;
        }
        String sign = PaymentUtil.sortSign(map, customer.getPriKey());
        map.put("sign",sign);
        Map<String,String> newMap = map.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue()==null?"":String.valueOf(e.getValue())));
        //1041商户要编码
        Long mchId = customer.getId();
        if (mchId == 1041) {
            try {
                newMap.put("remark", URLEncoder.encode(newMap.get("remark"),"UTF-8"));
            } catch (UnsupportedEncodingException e) {
                log.error("{},下游回调，URL编码异常：{}",withdrawRequest,e.getMessage(),e);
            }
        }
        String result = OkHttpUtils.getInstance().doPostForm(notifyUrl,newMap);
        log.info("下游代付回调，订单号{}，回调URL：{},参数：{},回调结果{}",withdrawRequest.getWithdrawId(),notifyUrl,newMap,result);
        if ("success".equals(result)) {
            //回调成功 ,更新订单表回调状态字段
            UpdateWrapper<TWithdrawRequest> wrapper = new UpdateWrapper<>();
            wrapper.eq("withdraw_id",withdrawRequest.getWithdrawId());
            wrapper.set("callback_status",1);
            this.update(wrapper);
        }
        return result;
    }

    @Override
    public List<WithdrawalRequestVO> listOrder(WithdrawalSearchDto tWithdrawRequest) {
        return this.tWithdrawRequestMapper.listOrder(tWithdrawRequest);
    }

//    @Override
//    @Transactional
//    public boolean approve(TWithdrawRequest tWithdrawRequest) {
//        TWithdrawRequest withdrawRequest = this.getById(tWithdrawRequest.getWithdrawId());
//        if (!OrderEnum.PENDDING.getCode().equals(withdrawRequest.getStatus())) {
//            throw new CustomException(ExceptionEnum.AVAILABLE_BALANCE_NOT_ENOUGH);
//        }
//        Integer status = tWithdrawRequest.getStatus();
//        if (status == null) {
//            throw new CustomException(ExceptionEnum.PARAMS_ERROR);
//        } else if(status == 2){//通过 扣去冻结额度
//            withdrawRequestService.doSuccess(tWithdrawRequest.getWithdrawId(),null,tWithdrawRequest.getRemark());
//        } else if(status ==3){//驳回 扣去冻结额度 ，增加
//            withdrawRequestService.doFail(tWithdrawRequest.getWithdrawId(),null,tWithdrawRequest.getRemark());
//        }
//        UpdateWrapper<TWithdrawRequest> wrapper = new UpdateWrapper<>();
//        wrapper.eq("withdraw_id",tWithdrawRequest.getWithdrawId());
//        wrapper.set("status",tWithdrawRequest.getStatus());
//        wrapper.set("remark",tWithdrawRequest.getRemark());
//        return this.update(wrapper);
//    }

    @Override
    @Transactional
    public boolean doFail(String billNo, String orderNo, String result) {
        TWithdrawRequest withdrawRequest = tWithdrawRequestMapper.findByIdForUpdate(billNo);
        if (!OrderEnum.PENDDING.getCode().equals(withdrawRequest.getStatus())) {
            return false;
        }
        TCustomer customer = customerService.getById(withdrawRequest.getMchId());
        BigDecimal amount = withdrawRequest.getWithdrawAmount();
        BigDecimal fee = withdrawRequest.getFee();
        BigDecimal mchCost = withdrawRequest.getRateFee();
        BigDecimal totalFee = fee.add(mchCost);
        BigDecimal opeaAmount = amount.add(totalFee);
        Long mchId = withdrawRequest.getMchId();
        BigDecimal preBalance = customer.getBalance();
        BigDecimal postBalance = preBalance.add(opeaAmount);
        TCreditLog creditLog = new TCreditLog();
        creditLog.setId("RJ"+ snowflakeKeyGenerator.generateKey());
        creditLog.setMchId(mchId);
        creditLog.setPreBalance(preBalance);
        creditLog.setPostBalance(postBalance);
        creditLog.setOpearteAmount(opeaAmount);
        creditLog.setFee(BigDecimal.ZERO);
        creditLog.setRefId(withdrawRequest.getWithdrawId());
        creditLog.setOpearteType(BillOperateTypeEnum.REJECT.getCode());
        Date now = DateUtils.getNowDate();
        creditLog.setCreateTime(now);
        creditLogService.save(creditLog);
        UpdateWrapper<TWithdrawRequest> wrapper = new UpdateWrapper<>();
        wrapper.eq("withdraw_id",billNo);
        wrapper.eq("status",1);
        wrapper.set("status",OrderEnum.FAIL.getCode());
        wrapper.set("remark",result);
        wrapper.set("up_order_no",orderNo);
        wrapper.set("success_time",now);
        boolean rs = this.update(wrapper);
        if(rs){
            return customerService.withdrawal(mchId,amount,totalFee,3,withdrawRequest.getWithdrawId());
        }
        return false;
    }

    @Override
    public Map<String, Object> sum(ListDto dto, TCustomer customer) {
        Long mchId = customer.getId();
        QueryWrapper<TWithdrawRequest> queryWrapper = new QueryWrapper<>();
        if (dto.getBegin() != null) {
            queryWrapper.ge("create_time",dto.getBegin());
        }
        if (dto.getEnd() != null) {
            queryWrapper.le("create_time",dto.getEnd());
        }
        if (StringUtils.isNotEmpty(dto.getOrderNo())) {
            queryWrapper.eq("withdraw_id",dto.getOrderNo());
        }
        if (dto.getStatus() != null) {
            queryWrapper.eq("status",dto.getStatus());
        }
        if (StringUtils.isNotEmpty(dto.getOutOrderNo())) {
            queryWrapper.eq("out_order_no",dto.getOutOrderNo());
        }
        if (customer.getUserType() == 1) {
            List<Long> customerIdList = customerService.getChildrenIdList(customer);
            queryWrapper.in("mch_id",customerIdList);
        } else {
            queryWrapper.eq("mch_id",mchId);
        }
        queryWrapper.select("sum(withdraw_amount) as totalAmount,sum(fee) as totalFee,sum(rate_fee) as totalRateFee");
        return getMap(queryWrapper);
    }

    @Override
    public Map<String, Object> sum(WithdrawalSearchDto dto) {
        return tWithdrawRequestMapper.listOrderSum(dto);
    }

    @Override
    public Map<String, Object> successRate(WithdrawalSearchDto dto) {
        return tWithdrawRequestMapper.successRate(dto);
    }

    @Override
    public Map<String, Object> failRate(WithdrawalSearchDto dto) {
        return tWithdrawRequestMapper.failRate(dto);
    }

    @Override
    public Map<String, Object> failRate(ListDto dto, TCustomer customer) {
        Long mchId = customer.getId();
        QueryWrapper<TWithdrawRequest> queryWrapper = new QueryWrapper<>();
        if (dto.getBegin() != null) {
            queryWrapper.ge("create_time",dto.getBegin());
        }
        if (dto.getEnd() != null) {
            queryWrapper.le("create_time",dto.getEnd());
        }
        if (StringUtils.isNotEmpty(dto.getOrderNo())) {
            queryWrapper.eq("withdraw_id",dto.getOrderNo());
        }
        queryWrapper.eq("status",3);
        if (StringUtils.isNotEmpty(dto.getOutOrderNo())) {
            queryWrapper.eq("out_order_no",dto.getOutOrderNo());
        }
        if (customer.getUserType() == 3) {
            List<Long> customerIdList = customerService.getChildrenIdList(customer);
            queryWrapper.in("mch_id",customerIdList);
        } else {
            queryWrapper.eq("mch_id",mchId);
        }
        queryWrapper.select("count(1) as failCount ,sum(withdraw_amount) as failTotalAmount,sum(fee) as failTotalFee");
        return getMap(queryWrapper);
    }

    @Override
    public Map<String, Object> successRate(ListDto dto, TCustomer customer) {
        Long mchId = customer.getId();
        QueryWrapper<TWithdrawRequest> queryWrapper = new QueryWrapper<>();
        if (dto.getBegin() != null) {
            queryWrapper.ge("create_time",dto.getBegin());
        }
        if (dto.getEnd() != null) {
            queryWrapper.le("create_time",dto.getEnd());
        }
        if (StringUtils.isNotEmpty(dto.getOrderNo())) {
            queryWrapper.eq("withdraw_id",dto.getOrderNo());
        }
        queryWrapper.eq("status",2);
        if (StringUtils.isNotEmpty(dto.getOutOrderNo())) {
            queryWrapper.eq("out_order_no",dto.getOutOrderNo());
        }
        if (customer.getUserType() == 1) {
            List<Long> customerIdList = customerService.getChildrenIdList(customer);
            queryWrapper.in("mch_id",customerIdList);
        } else {
            queryWrapper.eq("mch_id",mchId);
        }
        queryWrapper.select("count(1) as successCount ,sum(withdraw_amount) as successTotalAmount,sum(fee) as successTotalFee");
        return getMap(queryWrapper);
    }

    @Override
    @Transactional
    public TWithdrawRequest createMual(Long mchId, WithdrawalAddDto dto, String ip) throws Exception {
        TCustomer customer = customerService.getById(mchId);
        boolean result = false;
        //检测谷歌验证码是否正确
        String code = dto.getCode();
        String checkCode = GoogleAuthenticator.getTOTPCode(customer.getGoogleCode());
        if(!StringUtils.equals(code,checkCode)){
            throw new CustomException(ExceptionEnum.GOOGLE_CODE_ERROR);
        }
        //检测提现密码是否正确
        String password = dto.getPassword();
        if (!SecurityUtils.matchesPassword(password, customer.getWithdrawPassword()))
        {
            throw new CustomException("提现密码错误");
        }
        BigDecimal preBalance = customer.getBalance();
        //查看商户是否已经配置过上游
//        Integer channelId = customer.getChannelId();
//        if (channelId == null) {
//            log.info("{}代付失败，没有配置通道",mchId);
//            throw new CustomException(ExceptionEnum.NO_CHANNEL_CONFIG);
//        }
        //查看商户是否已经配置过结算方式
        TChannelSet channelSet = channelSetService.getOne(mchId, Constants.DAIFU_CODE);
        if (channelSet == null) {
            log.info("{}代付失败，没有配置结算方式",mchId);
            throw new CustomException(ExceptionEnum.NO_PLAN_CONFIG);
        }
        //获取手续费
        BigDecimal fee = channelSet.getDaifuFee();
        BigDecimal rate = channelSet.getRate();
        BigDecimal amount = dto.getAmount();
        BigDecimal mchCost = amount.multiply(rate.divide(new BigDecimal(100))).setScale(2,RoundingMode.HALF_UP);
        BigDecimal totalCost = mchCost.add(fee);
        //检查代付金额是否限额
        BigDecimal max = channelSet.getDaifuMaxAmount();
        if (max != null && amount.compareTo(max)> 0) {
            log.info("{}订单号金额超出最大限额",mchId);
            throw new CustomException("订单号金额超出最大限额");
        }
        BigDecimal min = channelSet.getDaifuMinAmount();
        if (min != null && amount.compareTo(min) < 0) {
            log.info("{}订单金额低于最小限额",mchId);
            throw new CustomException("订单金额低于最小限额");
        }

        // 获取商户余额
        BigDecimal currentBalance = customer.getBalance();
        BigDecimal opearteAmount = amount.add(fee);
        if (currentBalance.compareTo(opearteAmount)<0){
            log.info("{}代付失败，余额不足,余额:{},提现金额:{}",mchId,currentBalance,opearteAmount);
            throw new CustomException(ExceptionEnum.AVAILABLE_BALANCE_NOT_ENOUGH);
        }
        BigDecimal postBalance = currentBalance.subtract(opearteAmount);
        Integer channelId = null;
        //新增代付订单
        TWithdrawRequest withdrawRequest = new TWithdrawRequest();
        String withdrawalId = "MAUL"+snowflakeKeyGenerator.generateKey();
        withdrawRequest.setWithdrawId(withdrawalId);
        withdrawRequest.setWithdrawAmount(dto.getAmount());
        withdrawRequest.setMchId(mchId);
        withdrawRequest.setStatus(OrderEnum.PENDDING.getCode());
        withdrawRequest.setFee(fee);
        withdrawRequest.setRateFee(mchCost);
        withdrawRequest.setBankName(dto.getBankName());
        withdrawRequest.setBankNo(dto.getBankNo());
        withdrawRequest.setBankRealname(dto.getAccountName());
        withdrawRequest.setCurrencyType(dto.getCurrencyType());
        withdrawRequest.setOrderType(2);
//        withdrawRequest.setCallbackUrl(withdrawalDto.getReturnUrl());
        //新增额度变更订单
        TCreditLog creditLog = new TCreditLog();
        creditLog.setId("LG"+snowflakeKeyGenerator.generateKey());
        creditLog.setMchId(mchId);
        creditLog.setOpearteType(BillOperateTypeEnum.WITHDRAWAL.getCode());
        creditLog.setRefId(withdrawalId);
        creditLog.setPreBalance(preBalance);
        creditLog.setPostBalance(postBalance);
        creditLog.setOpearteAmount(amount.negate());
        creditLog.setFee(totalCost.negate());
        TChannelDto channel = channelService.getEnableChanel(customer.getId(), "A888");
        channelId = channel.getChannelId();
        if(1 == dto.getCurrencyType()){//RMB
            BigDecimal channelFee = channel.getChannelFee();
            withdrawRequest.setChannelFee(channelFee);
            if (channel != null && channel.getStatus() == StatusEnum.OK.getCode()) {
                String serviceCode = channel.getCode();
                if (StringUtils.isNotEmpty(serviceCode)) {
                    WithdrawalDto withdrawalDto = new WithdrawalDto();
                    withdrawalDto.setAccountName(dto.getAccountName());
                    withdrawalDto.setAmount(dto.getAmount());
                    withdrawalDto.setBankNo(dto.getBankNo());
                    withdrawalDto.setBankName(dto.getBankName());
                    withdrawalDto.setMchId(mchId);
                    withdrawalDto.setNotifyUrl(channel.getNotifyUrl());
                    withdrawalDto.setOrderId("MAUL"+snowflakeKeyGenerator.generateKey());
                    withdrawalDto.setIp(ip);
                    if (channel.getChannelId() != 1) {
                        //根据code注入对应上游的service
                        IPaymentService paymentService = SpringUtils.getBean(serviceCode);
                        WithdrawalResult withdrawalResult =  paymentService.withdrawal(withdrawalDto,withdrawalId);
                        if(200 == withdrawalResult.getCode()){
                            log.info("手动代付成功：{},结果：{}",dto,withdrawalResult);
                            withdrawRequest.setUpOrderNo(withdrawalResult.getUpOrderNo());
                        } else {
                            log.info("手动代付失败,事务回滚：{},返回:{}",dto,withdrawalResult);
                            throw new CustomException("手动代付失败:"+withdrawalResult.getMsg());
                        }
                    }
                }
            }
        }
        if(creditLogService.save(creditLog)){
            withdrawRequest.setChannelId(channelId);
            if(this.save(withdrawRequest)){
                //修改用户额度
                result = customerService.withdrawal(mchId,amount,totalCost,1,withdrawalId);
            }
        }
        return result?withdrawRequest:null;
    }

    @Override
    public TWithdrawRequest adminCreateMual(MualWithAddDto dto) throws Exception{
        Long mchId = dto.getCustomerId();
        TCustomer customer = customerService.getById(mchId);
        boolean result = false;
        BigDecimal preBalance = customer.getBalance();
        //查看商户是否已经配置过结算方式
        TChannelSet channelSet = channelSetService.getOne(mchId,Constants.DAIFU_CODE);
        if (channelSet == null) {
            log.info("{}代付失败，没有配置结算方式",mchId);
            throw new CustomException(ExceptionEnum.NO_PLAN_CONFIG);
        }
        //获取手续费
        BigDecimal fee = channelSet.getDaifuFee();
        BigDecimal amount = dto.getAmount();
        BigDecimal rate = channelSet.getRate();
        BigDecimal mchCost = amount.multiply(rate.divide(new BigDecimal(100))).setScale(2,RoundingMode.HALF_UP);
        BigDecimal totalCost = mchCost.add(fee);
        //检查代付金额是否限额
        BigDecimal max = channelSet.getDaifuMaxAmount();
        if (max != null && amount.compareTo(max)> 0) {
            log.info("{}订单号金额超出最大限额",mchId);
            throw new CustomException("订单号金额超出最大限额");
        }
        BigDecimal min = channelSet.getDaifuMinAmount();
        if (min != null && amount.compareTo(min) < 0) {
            log.info("{}订单金额低于最小限额",mchId);
            throw new CustomException("订单金额低于最小限额");
        }

        // 获取商户余额
        BigDecimal currentBalance = customer.getBalance();
        BigDecimal opearteAmount = amount.add(totalCost);
        if (currentBalance.compareTo(opearteAmount)<0){
            log.info("{}代付失败，余额不足,余额:{},提现金额:{}",mchId,currentBalance,opearteAmount);
            throw new CustomException(ExceptionEnum.AVAILABLE_BALANCE_NOT_ENOUGH);
        }
        BigDecimal postBalance = currentBalance.subtract(opearteAmount);
        Integer channelId = null;
        //新增代付订单
        TWithdrawRequest withdrawRequest = new TWithdrawRequest();
        String withdrawalId = "MAUL"+snowflakeKeyGenerator.generateKey();
        withdrawRequest.setWithdrawId(withdrawalId);
        withdrawRequest.setWithdrawAmount(dto.getAmount());
        withdrawRequest.setMchId(Long.valueOf(mchId));
        withdrawRequest.setStatus(OrderEnum.PENDDING.getCode());
        withdrawRequest.setFee(fee);
        withdrawRequest.setRateFee(mchCost);
        withdrawRequest.setBankName(dto.getBankName());
        withdrawRequest.setBankNo(dto.getBankNo());
        withdrawRequest.setBankRealname(dto.getAccountName());
        withdrawRequest.setCurrencyType(dto.getCurrencyType());
        withdrawRequest.setOrderType(2);
//        withdrawRequest.setCallbackUrl(withdrawalDto.getReturnUrl());
        //新增额度变更订单
        TCreditLog creditLog = new TCreditLog();
        creditLog.setId("LG"+snowflakeKeyGenerator.generateKey());
        creditLog.setMchId(mchId);
        creditLog.setOpearteType(BillOperateTypeEnum.WITHDRAWAL.getCode());
        creditLog.setRefId(withdrawalId);
        creditLog.setPreBalance(preBalance);
        creditLog.setPostBalance(postBalance);
        creditLog.setOpearteAmount(amount.negate());
        creditLog.setFee(totalCost);
        TChannelDto channel = channelService.getEnableChanel(customer.getId(), "A888");
        BigDecimal channelFee = channel.getChannelFee();
        channelId = channel.getChannelId();
        if(1 == dto.getCurrencyType()){//RMB
            withdrawRequest.setChannelFee(channelFee);
            if (channel != null && channel.getStatus() == StatusEnum.OK.getCode()) {
                String serviceCode = channel.getCode();
                if (StringUtils.isNotEmpty(serviceCode)) {
                    WithdrawalDto withdrawalDto = new WithdrawalDto();
                    withdrawalDto.setAccountName(dto.getAccountName());
                    withdrawalDto.setAmount(dto.getAmount());
                    withdrawalDto.setBankNo(dto.getBankNo());
                    withdrawalDto.setBankName(dto.getBankName());
                    withdrawalDto.setMchId(mchId);
                    withdrawalDto.setNotifyUrl(channel.getNotifyUrl());
                    withdrawalDto.setOrderId("MAUL"+snowflakeKeyGenerator.generateKey());
//                    withdrawalDto.setIp(ip);
                    if (channelId != 1) {
                        //根据code注入对应上游的service
                        IPaymentService paymentService = SpringUtils.getBean(serviceCode);
                        WithdrawalResult withdrawalResult =  paymentService.withdrawal(withdrawalDto,withdrawalId);
                        if(200 == withdrawalResult.getCode()){
                            log.info("管理员手动代付成功：{},结果：{}",dto,withdrawalResult);
                            withdrawRequest.setUpOrderNo(withdrawalResult.getUpOrderNo());
                        } else {
                            log.info("管理员手动代付失败,事务回滚：{},返回:{}",dto,withdrawalResult);
                            throw new CustomException("手动代付失败:"+withdrawalResult.getMsg());
                        }
                    }
                }
            }
        }
        if(creditLogService.save(creditLog)){
            withdrawRequest.setChannelId(channelId);
            if(this.save(withdrawRequest)){
                //修改用户额度
                result = customerService.withdrawal(mchId,amount,totalCost,1,withdrawalId);
            }
        }
        if (result ) {
            return withdrawRequest;
        } else {
            return null;
        }
    }

    @Override
    public Integer countNotice() {
        QueryWrapper<TWithdrawRequest> wrapper = new QueryWrapper<>();
        wrapper.eq("status",1);
        wrapper.eq("channel_id",1);
        return tWithdrawRequestMapper.selectCount(wrapper);
    }



}
