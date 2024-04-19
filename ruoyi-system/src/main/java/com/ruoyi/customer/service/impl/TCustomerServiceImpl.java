package com.ruoyi.customer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.channel.domain.TChannelSet;
import com.ruoyi.channel.domain.TChannelTopSet;
import com.ruoyi.channel.service.ITChannelSetService;
import com.ruoyi.channel.service.ITChannelTopSetService;
import com.ruoyi.common.MyLambdaUpdateWrapper;
import com.ruoyi.common.dto.UpdateAgentDto;
import com.ruoyi.common.dto.admin.CustomerSearchDto;
import com.ruoyi.common.enums.BillOperateTypeEnum;
import com.ruoyi.common.enums.ExceptionEnum;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.google.GoogleAuthenticator;
import com.ruoyi.common.utils.uuid.SnowflakeKeyGenerator;
import com.ruoyi.common.vo.CustomerAdminVO;
import com.ruoyi.customer.domain.TCreditLog;
import com.ruoyi.customer.domain.TCustomer;
import com.ruoyi.customer.mapper.TCustomerMapper;
import com.ruoyi.customer.service.ITCreditLogService;
import com.ruoyi.customer.service.ITCustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 商户信息Service业务层处理
 * 
 * @author yf
 * @date 2022-04-17
 */
@Service
@Slf4j
public class TCustomerServiceImpl extends ServiceImpl<TCustomerMapper,TCustomer> implements ITCustomerService
{
    @Autowired
    private TCustomerMapper tCustomerMapper;

    @Autowired
    private ITCreditLogService creditLogService;

    @Autowired
    private SnowflakeKeyGenerator snowflakeKeyGenerator;

    @Autowired
    private ITChannelSetService channelSetService;

    @Autowired
    private ITChannelTopSetService channelTopSetService;


    /**
     * 查询商户信息
     * 
     * @param id 商户信息ID
     * @return 商户信息
     */
    @Override
    public TCustomer selectTCustomerById(Long id)
    {
        return tCustomerMapper.selectTCustomerById(id);
    }

    /**
     * 查询商户信息列表
     * 
     * @param tCustomer 商户信息
     * @return 商户信息
     */
    @Override
    public List<TCustomer> selectTCustomerList(TCustomer tCustomer)
    {
        QueryWrapper<TCustomer> wrapper = new QueryWrapper<>();
        if (tCustomer.getId() != null) {
            wrapper.eq("id",tCustomer.getId());
        }
        if (StringUtils.isNotEmpty(tCustomer.getUsername())) {
            wrapper.eq("username",tCustomer.getUsername());
        }
//        this.tCustomerMapper.selectTCustomerList(tCustomer);
        return this.list(wrapper);
    }

    /**
     * 新增商户信息
     * 
     * @param tCustomer 商户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertTCustomer(TCustomer tCustomer)
    {
        int result = this.save(tCustomer)?1:0;
        if (result > 0) {
            //通用配置同步过去
            TChannelTopSet channelTopSet = new TChannelTopSet();
            List<TChannelTopSet> channelTopSetList = channelTopSetService.list(channelTopSet);
            TChannelSet channelSet = null;
            for (TChannelTopSet channelSetVO : channelTopSetList) {
                channelSet = new TChannelSet();
                BeanUtils.copyProperties(channelSetVO,channelSet);
                channelSet.setCustomerId(tCustomer.getId());
                channelSetService.save(channelSet);
            }
        }
        return result;
    }

    /**
     * 修改商户信息
     * 
     * @param tCustomer 商户信息
     * @return 结果
     */
    @Override
    public int updateTCustomer(TCustomer tCustomer)
    {
        tCustomer.setUpdateTime(DateUtils.getNowDate());
        return tCustomerMapper.updateTCustomer(tCustomer);
    }

    /**
     * 批量删除商户信息
     * 
     * @param ids 需要删除的商户信息ID
     * @return 结果
     */
    @Override
    public int deleteTCustomerByIds(Long[] ids)
    {
        return tCustomerMapper.deleteTCustomerByIds(ids);
    }

    /**
     * 删除商户信息信息
     * 
     * @param id 商户信息ID
     * @return 结果
     */
    @Override
    public int deleteTCustomerById(Long id)
    {
        return tCustomerMapper.deleteTCustomerById(id);
    }

    /**
     *  添加额度
     * @param mchId
     * @param amount
     * @param type  null 或 0 表示 只追加额度 ,1-表示存款增加额度 2-表示提款扣去额度
     * @return
     */
    @Override
    public boolean addAmount(Long mchId, BigDecimal amount,Integer type) {
//        UpdateWrapper<TCustomer> wrapper1 = new UpdateWrapper<>();
        MyLambdaUpdateWrapper<TCustomer> wrapper = new MyLambdaUpdateWrapper<>(TCustomer.class);
        if(type == null){
            wrapper.incrField(TCustomer::getBalance,amount);
        }
        else if (1 == type) {
            wrapper.incrField(TCustomer::getBalance,amount);
            //wrapper.incrField(TCustomer::getTotalUsed,totalAmount);
        } else if (2 == type) {
            wrapper.incrField(TCustomer::getLockBalance,amount).incrField(TCustomer::getTotalUsed,amount).descField(TCustomer::getBalance,amount);
        }
        wrapper.eq(TCustomer::getId,mchId);
        return this.update(wrapper);
    }


    /**
     *  代付 通过 / 失败 金额变更
     * @param mchId  商户号
     * @param amount 订单金额
     * @param fee 手续费
     * @param flag  1-表示发起代付 2-表示代付成功 3-代付失败
     * @param orderNo  代付订单号 ，作为日志记录用
     * @return
     */
    @Override
    @Transactional
    public boolean withdrawal(Long mchId,BigDecimal amount,BigDecimal fee,int flag,String orderNo){
        log.info("代付金额变更开始，订单号：{},商户号：{}，订单金额：{}，手续费：{}，类型：{}",orderNo,mchId,amount,fee,flag);
        MyLambdaUpdateWrapper<TCustomer> wrapper = new MyLambdaUpdateWrapper<>(TCustomer.class);
        int count = 0;
        if (flag == 1) {
//            wrapper.incrField(TCustomer::getLockBalance,amount.add(fee))
//                    .descField(TCustomer::getBalance,amount.add(fee))
//                    .incrField(TCustomer::getTotalUsed,amount);
            count = tCustomerMapper.withdrawalCreate(mchId,amount.add(fee));
        }
        else if (flag == 2) {
            //代付成功 减去冻结金额
//            wrapper.descField(TCustomer::getLockBalance,amount.add(fee));
            count = tCustomerMapper.withdrawalSuccess(mchId,amount.add(fee));
        }else {
            //代付失败 ，减去冻结金额 ，增加可用金额，通道额度减少
//            wrapper.descField(TCustomer::getLockBalance,amount.add(fee)).incrField(TCustomer::getBalance,amount.add(fee)).descField(TCustomer::getTotalUsed,amount);
            count = tCustomerMapper.withdrawalFailed(mchId,amount.add(fee));
        }
//        wrapper.eq(TCustomer::getId,mchId);
        boolean rs = count > 0;
        if(!rs){
            throw new CustomException("处理金额异常，请稍后再试");
        }
        log.info("代付金额变更结束，订单号：{},商户号：{}，订单金额：{}，手续费：{}，类型：{}，结果：{}",orderNo,mchId,amount,fee,flag,rs);
        return true;
    }

    @Override
    public TCustomer selectTCustomerByUsername(String username) {
        QueryWrapper<TCustomer> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        return this.getOne(wrapper);
    }

    @Override
    public boolean switchSafeMode(Long userId) {
        TCustomer customer = this.getById(userId);
        int safeMode = customer.getSafeMode();
        customer.setSafeMode(safeMode == 0 ? 1:0);
        return this.updateById(customer);
    }

    @Override
    public boolean updatePassword(Long id, String encryptPassword) {
        UpdateWrapper<TCustomer> wrapper = new UpdateWrapper();
        wrapper.set("password",encryptPassword);
        wrapper.eq("id",id);
        return update(wrapper);
    }

    @Override
    public boolean updateChanel(TCustomer tCustomer) {
        //获取旧的渠道id ,如果两者没有变则说明不需要判断额度是否清空
        TCustomer user = this.getById(tCustomer.getId());
        Integer oldChannelId = user.getChannelId();
        Integer newChannelId = tCustomer.getChannelId();
        UpdateWrapper<TCustomer> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",tCustomer.getId());
        wrapper.set("update_by", SecurityUtils.getUsername());
        wrapper.set("update_time",DateUtils.getNowDate());
        if(newChannelId.equals(oldChannelId)){
            //只需要update 通道额度就行
            wrapper.set("channel_limit",tCustomer.getChannelLimit());
        } else {
            //判断额度是否清0
            if (user.getBalance().add(user.getLockBalance()).compareTo(BigDecimal.ZERO)> 0) {
                throw new CustomException(ExceptionEnum.BALANCE_ERROR);
            }
            wrapper.set("channel_id",tCustomer.getChannelId());
            wrapper.set("channel_limit",tCustomer.getChannelLimit());
            wrapper.set("total_used",0);
        }
        return this.update(wrapper);
    }

    @Override
    public List<CustomerAdminVO> selectAdminCustomerList(CustomerSearchDto tCustomer) {
        return this.tCustomerMapper.selectAdminCustomerList(tCustomer);
    }

    @Override
    public boolean updatePlan(Long id, Integer planId) {
        UpdateWrapper<TCustomer> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",id);
        wrapper.set("plan_id",planId);
        return this.update(wrapper);
    }

    @Override
    public boolean updatePwd(Integer type, Long userId, String password) {
        UpdateWrapper<TCustomer> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",userId);
        if(type == 0){//重置密码
            wrapper.set("password",SecurityUtils.encryptPassword(password));
        }else if (type == 1) {
            wrapper.set("withdraw_password",SecurityUtils.encryptPassword(password));
        }
        wrapper.set("update_by",SecurityUtils.getUsername());
        wrapper.set("update_time",DateUtils.getNowDate());
        return this.update(wrapper);
    }

    @Override
    public boolean resetGoogle(Long userId) {
        String newGoogleKey = GoogleAuthenticator.getRandomSecretKey();
        UpdateWrapper<TCustomer> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",userId);
        wrapper.set("update_by",SecurityUtils.getUsername());
        wrapper.set("update_time",DateUtils.getNowDate());
        wrapper.set("google_code",newGoogleKey);
        wrapper.set("last_login_time",null);
        wrapper.set("safe_mode",0);
        return this.update(wrapper);
    }

    @Override
    @Transactional
    public boolean addBalance(Long id, BigDecimal amount, String remark) {
        TCustomer customer = this.getById(id);
        if (customer == null) {
            throw new CustomException(ExceptionEnum.MERCHANT_NOT_EXIST);
        }
        BigDecimal preBalance = customer.getBalance();
        BigDecimal postBalance = preBalance.add(amount);
        TCreditLog creditLog = new TCreditLog();
        String creditLogId = "CD" + snowflakeKeyGenerator.generateKey();
        creditLog.setId(creditLogId);
        creditLog.setFee(BigDecimal.ZERO);
        creditLog.setOpearteAmount(amount);
        creditLog.setPreBalance(preBalance);
        creditLog.setPostBalance(postBalance);
        creditLog.setRemark(remark);
        creditLog.setMchId(id);
        if (amount.compareTo(BigDecimal.ZERO)>0) {
            creditLog.setOpearteType(BillOperateTypeEnum.MANUAL_IN.getCode());
        } else {
            creditLog.setOpearteType(BillOperateTypeEnum.MANUAL_OUT.getCode());
        }
        Date now = DateUtils.getNowDate();
        creditLog.setCreateTime(now);
        creditLog.setCreateBy(SecurityUtils.getUsername());
        creditLog.setUpdateBy(SecurityUtils.getUsername());
        creditLog.setUpdateTime(now);
        creditLogService.save(creditLog);
        //修改额度变更
        return this.addAmount(id,amount,null);
    }

    @Override
    public void addTotalUsed(Long mchId,BigDecimal amount) {
        MyLambdaUpdateWrapper<TCustomer> wrapper = new MyLambdaUpdateWrapper<>(TCustomer.class);
        wrapper.incrField(TCustomer::getTotalUsed,amount);
        wrapper.eq(TCustomer::getId,mchId);
        this.update(wrapper);
    }

    @Override
    public boolean updateStatus(Long mchId, Integer status) {
        UpdateWrapper<TCustomer> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",mchId);
        wrapper.set("status",status);
        return this.update(wrapper);
    }

    @Override
    public List<TCustomer> listAgent() {
        QueryWrapper<TCustomer> wrapper = new QueryWrapper<>();
        wrapper.eq("user_type",1);
        wrapper.eq("status",0);
        wrapper.orderByAsc("username");
        return list(wrapper);
    }

    @Override
    @Transactional
    public boolean updateAgent(UpdateAgentDto updateAgentDto) {
        Long customerId = updateAgentDto.getId();
        TCustomer customer = this.getById(customerId);
        if (customer!=null && customer.getUserType()==1) {
            throw new CustomException("代理不能配置代理");
        }
        Long agentId = updateAgentDto.getAgentId();
        TCustomer agent = this.getById(agentId);
        if (agent!=null && agent.getUserType()==0) {
            throw new CustomException("不能把商户配置给商户");
        }
        UpdateWrapper<TCustomer> wrapper = new UpdateWrapper<>();
        wrapper.set("agent_id",updateAgentDto.getAgentId());
        wrapper.eq("id",customerId);
        return this.update(wrapper);
    }

    @Override
    public boolean updatePassword(Long id, String encryptPassword, Integer passwordMode) {
        UpdateWrapper<TCustomer> wrapper = new UpdateWrapper();
        if (passwordMode == 1) {
            wrapper.set("password",encryptPassword);
        } else {
            wrapper.set("withdraw_password",encryptPassword);
        }
        wrapper.eq("id",id);
        return update(wrapper);
    }

    @Override
    public List<Long> getChildrenIdList(TCustomer customer) {
        QueryWrapper<TCustomer> wrapper = new QueryWrapper<>();
        wrapper.eq("agent_id",customer.getId());
        wrapper.select("id");
        List<Long> result = new ArrayList<>();
        List<Object> objectList = listObjs(wrapper);
        for (Object o : objectList) {
            result.add((Long) o);
        }
        return result;
    }

    @Override
    public Map<String, Object> sumBalance(CustomerSearchDto tCustomer) {
        return tCustomerMapper.sumBalance(tCustomer);
    }
}
