package com.ruoyi.channel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.channel.domain.TSettlementPlan;
import com.ruoyi.channel.mapper.TSettlementPlanMapper;
import com.ruoyi.channel.service.ITSettlementPlanService;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 结算方案Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-04-23
 */
@Service
public class TSettlementPlanServiceImpl extends ServiceImpl<TSettlementPlanMapper,TSettlementPlan> implements ITSettlementPlanService
{
    @Autowired
    private TSettlementPlanMapper tSettlementPlanMapper;

    /**
     * 查询结算方案
     * 
     * @param planId 结算方案ID
     * @return 结算方案
     */
    @Override
    public TSettlementPlan selectTSettlementPlanById(Long planId)
    {
        return tSettlementPlanMapper.selectTSettlementPlanById(planId);
    }

    /**
     * 查询结算方案列表
     * 
     * @param tSettlementPlan 结算方案
     * @return 结算方案
     */
    @Override
    public List<TSettlementPlan> selectTSettlementPlanList(TSettlementPlan tSettlementPlan)
    {
        return tSettlementPlanMapper.selectTSettlementPlanList(tSettlementPlan);
    }

    /**
     * 新增结算方案
     * 
     * @param tSettlementPlan 结算方案
     * @return 结果
     */
    @Override
    public int insertTSettlementPlan(TSettlementPlan tSettlementPlan)
    {
        //方案名称重复查询
        int count = tSettlementPlanMapper.countByName(tSettlementPlan.getPlanName());
        if (count > 0) {
            throw new CustomException("方案名称已存在");
        }
        tSettlementPlan.setCreateTime(DateUtils.getNowDate());
        return tSettlementPlanMapper.insertTSettlementPlan(tSettlementPlan);
    }

    /**
     * 修改结算方案
     * 
     * @param tSettlementPlan 结算方案
     * @return 结果
     */
    @Override
    public int updateTSettlementPlan(TSettlementPlan tSettlementPlan)
    {
        tSettlementPlan.setUpdateTime(DateUtils.getNowDate());
        return tSettlementPlanMapper.updateTSettlementPlan(tSettlementPlan);
    }

    /**
     * 批量删除结算方案
     * 
     * @param planIds 需要删除的结算方案ID
     * @return 结果
     */
    @Override
    public int deleteTSettlementPlanByIds(Long[] planIds)
    {
        return tSettlementPlanMapper.deleteTSettlementPlanByIds(planIds);
    }

    /**
     * 删除结算方案信息
     * 
     * @param planId 结算方案ID
     * @return 结果
     */
    @Override
    public int deleteTSettlementPlanById(Long planId)
    {
        return tSettlementPlanMapper.deleteTSettlementPlanById(planId);
    }
}
