package com.ruoyi.channel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.channel.domain.TSettlementPlan;

import java.util.List;

/**
 * 结算方案Service接口
 * 
 * @author ruoyi
 * @date 2022-04-23
 */
public interface ITSettlementPlanService extends IService<TSettlementPlan>
{
    /**
     * 查询结算方案
     * 
     * @param planId 结算方案ID
     * @return 结算方案
     */
    public TSettlementPlan selectTSettlementPlanById(Long planId);

    /**
     * 查询结算方案列表
     * 
     * @param tSettlementPlan 结算方案
     * @return 结算方案集合
     */
    public List<TSettlementPlan> selectTSettlementPlanList(TSettlementPlan tSettlementPlan);

    /**
     * 新增结算方案
     * 
     * @param tSettlementPlan 结算方案
     * @return 结果
     */
    public int insertTSettlementPlan(TSettlementPlan tSettlementPlan);

    /**
     * 修改结算方案
     * 
     * @param tSettlementPlan 结算方案
     * @return 结果
     */
    public int updateTSettlementPlan(TSettlementPlan tSettlementPlan);

    /**
     * 批量删除结算方案
     * 
     * @param planIds 需要删除的结算方案ID
     * @return 结果
     */
    public int deleteTSettlementPlanByIds(Long[] planIds);

    /**
     * 删除结算方案信息
     * 
     * @param planId 结算方案ID
     * @return 结果
     */
    public int deleteTSettlementPlanById(Long planId);
}
